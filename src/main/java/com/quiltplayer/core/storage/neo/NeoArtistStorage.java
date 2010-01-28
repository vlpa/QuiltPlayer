/**
 * QuiltPlayer v1.0 Copyright (C) 2008-2009 Vlado Palczynski
 * vlado.palczynski@quiltplayer.com http://www.quiltplayer.com This program is
 * free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or (at your option) any later version. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package com.quiltplayer.core.storage.neo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.neo4j.api.core.Direction;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Relationship;
import org.neo4j.api.core.ReturnableEvaluator;
import org.neo4j.api.core.StopEvaluator;
import org.neo4j.api.core.TraversalPosition;
import org.neo4j.api.core.Traverser;
import org.neo4j.util.index.IndexService;
import org.neo4j.util.index.LuceneIndexService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.model.Artist;
import com.quiltplayer.model.StringId;
import com.quiltplayer.model.neo.NeoArtist;

/**
 * Implementation of ArtistStorage against Neo4j.
 * 
 * @author Vlado Palczynski
 */
@Repository
public class NeoArtistStorage implements ArtistStorage {
    /**
     * The logger.
     */
    private static final Logger log = Logger.getLogger(NeoArtistStorage.class);

    /**
     * Index property for album id.
     */
    private static final String ARTIST_ID_INDEX = "artistId";

    /**
     * Property for word.
     */
    private static final String WORD_PROPERTY = "word";

    /**
     * Property for count.
     */
    private static final String COUNT_PROPERTY = "count_uses";

    /**
     * Property for part.
     */
    private static final String PART_POSTFIX = ".part";

    /**
     * Neo storage.
     */
    private NeoService neoService = NeoSingelton.getInstance().getNeoService();

    /**
     * Index service.
     */
    private IndexService indexService = new LuceneIndexService(NeoSingelton.getInstance()
            .getNeoService());

    /**
     * Artist root.
     */
    private Node artistRoot;

    @Transactional
    public Artist getArtist(final StringId artistId) {
        Artist artist = null;

        Node artistNode = searchSingle(artistId.getId(), ARTIST_ID_INDEX,
                QuiltPlayerRelationshipTypes.ARTIST_ID);

        if (artistNode != null) {
            artist = new NeoArtist(artistNode);
        }

        return artist;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.storage.Storage#retrieveArtists()
     */
    @Override
    @Transactional
    public Collection<Artist> getArtists() {

        Traverser nodes = neoService.getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
                StopEvaluator.DEPTH_ONE, ReturnableEvaluator.ALL_BUT_START_NODE,
                QuiltPlayerRelationshipTypes.ARTISTS, Direction.OUTGOING);

        List<Artist> l = new ArrayList<Artist>();

        for (Node node : nodes) {
            l.add(new NeoArtist(node));
        }

        return l;
    }

    @Transactional
    public void getArtistByFirstChar(final String character) {
        Traverser morpheusfriends = artistRoot.traverse(Traverser.Order.BREADTH_FIRST,
                StopEvaluator.DEPTH_ONE, new ReturnableEvaluator() {
                    @Override
                    public boolean isReturnableNode(TraversalPosition pos) {
                        String artistName = (String) pos.currentNode().getProperty("name");

                        if (artistName.substring(0, 1).equalsIgnoreCase(character)) {
                            return true;
                        }

                        return false;
                    }
                }, QuiltPlayerRelationshipTypes.HAS_ALBUM, Direction.OUTGOING);

        for (Node friend : morpheusfriends) {
            System.out.println(friend);
        }
    }

    /*
     * @see com.quiltplayer.core.storage.Storage#createArtist(java.lang.String)
     */
    @Override
    @Transactional
    public Artist createArtist(final StringId id) {
        Artist artist = null;

        final Node artistNode = neoService.createNode();
        artist = new NeoArtist(artistNode);

        artist.setId(id);

        neoService.getReferenceNode().createRelationshipTo(artistNode,
                QuiltPlayerRelationshipTypes.ARTISTS);

        index(artist.getStringId().getId(), artistNode, ARTIST_ID_INDEX,
                QuiltPlayerRelationshipTypes.ARTIST_ID);

        log.debug("Artist with id '" + id.getId() + "' created.");

        return artist;
    }

    /*
     * @see com.quiltplayer.core.storage.Storage#deleteArtist(com.quiltplayer.model .Artist)
     */
    @Override
    @Transactional
    public void delete(final Artist artist) {
        Node artistNode = ((NeoArtist) artist).getNode();

        for (Relationship rel : artistNode.getRelationships()) {
            rel.delete();
        }

        artistNode.delete();
    }

    private void index(final String value, final Node node, final String indexName,
            final QuiltPlayerRelationshipTypes relType) {

        indexService.index(node, indexName, value);
        final String partIndexName = indexName + PART_POSTFIX;

        for (String part : splitSearchString(value)) {
            Node wordNode = indexService.getSingleNode(partIndexName, part);
            if (wordNode == null) {
                wordNode = neoService.createNode();
                indexService.index(wordNode, partIndexName, part);
                wordNode.setProperty(WORD_PROPERTY, part);
            }
            wordNode.createRelationshipTo(node, relType);
            wordNode.setProperty(COUNT_PROPERTY,
                    ((Integer) wordNode.getProperty(COUNT_PROPERTY, 0)) + 1);
        }
    }

    private String[] splitSearchString(final String value) {
        return value.toLowerCase(Locale.ENGLISH).split("[^\\w]+");
    }

    private List<Node> findSearchWords(final String userInput, final String indexName) {
        final String partIndexName = indexName + PART_POSTFIX;
        final List<Node> wordList = new ArrayList<Node>();

        for (String part : splitSearchString(userInput)) {
            Node wordNode = indexService.getSingleNode(partIndexName, part);
            if (wordNode == null || !wordNode.hasRelationship() || wordList.contains(wordNode)) {
                continue;
            }
            wordList.add(wordNode);
        }
        if (wordList.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.sort(wordList, new Comparator<Node>() {
            public int compare(final Node left, final Node right) {
                int leftCount = (Integer) left.getProperty(COUNT_PROPERTY, 0);
                int rightCount = (Integer) right.getProperty(COUNT_PROPERTY, 0);
                return leftCount - rightCount;
            }
        });
        return wordList;
    }

    private Node searchSingle(final String value, final String indexName,
            final QuiltPlayerRelationshipTypes wordRelType) {
        Node match = indexService.getSingleNode(indexName, value);

        if (match != null) {
            return match;
        }

        final List<Node> wordList = findSearchWords(value, indexName);
        if (wordList.isEmpty()) {
            return null;
        }

        final Node startNode = wordList.remove(0);
        match = startNode.getRelationships(wordRelType).iterator().next().getEndNode();
        if (wordList.isEmpty()) {
            return match;
        }

        int bestCount = 0;
        final int listSize = wordList.size();
        for (Relationship targetRel : startNode.getRelationships(wordRelType)) {
            Node targetNode = targetRel.getEndNode();
            int hitCount = 0;
            for (Relationship wordRel : targetNode.getRelationships(wordRelType)) {

                if (wordList.contains(wordRel.getStartNode())) {
                    if (++hitCount == listSize) {
                        return targetNode;
                    }
                }
            }
            if (hitCount > bestCount) {
                match = targetNode;
                bestCount = hitCount;
            }
        }
        return match;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.storage.ArtistStorage#deleteIfNoAlbumRelations()
     */
    @Override
    public void deleteIfNoAlbumRelations(Artist artist) {
        Traverser nodes = ((NeoArtist) artist).getNode().traverse(Traverser.Order.DEPTH_FIRST,
                StopEvaluator.DEPTH_ONE, ReturnableEvaluator.ALL_BUT_START_NODE,
                QuiltPlayerRelationshipTypes.HAS_ALBUM, Direction.OUTGOING);

        if (nodes.getAllNodes().size() == 0)
            delete(artist);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.storage.ArtistStorage#countArtists()
     */
    @Override
    public int countArtists() {
        Traverser nodes = neoService.getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
                StopEvaluator.DEPTH_ONE, ReturnableEvaluator.ALL_BUT_START_NODE,
                QuiltPlayerRelationshipTypes.ARTISTS, Direction.OUTGOING);

        return nodes.getAllNodes().size();
    }

}