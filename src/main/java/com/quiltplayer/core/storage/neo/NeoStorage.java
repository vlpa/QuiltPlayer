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
import java.util.Stack;

import org.apache.commons.lang.Validate;
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

import com.quiltplayer.core.storage.Storage;
import com.quiltplayer.external.covers.model.LocalImage;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.StringId;
import com.quiltplayer.model.neo.NeoAlbum;
import com.quiltplayer.model.neo.NeoArtist;
import com.quiltplayer.model.neo.NeoLocalImage;
import com.quiltplayer.model.neo.NeoSong;

/**
 * Implementation of Storage against Neo4j.
 * 
 * @author Vlado Palczynski
 */
@Repository
public class NeoStorage implements Storage {
    /**
     * The logger.
     */
    private static final Logger log = Logger.getLogger(NeoStorage.class);

    /**
     * Album property for album id.
     */
    private static final String ALBUM_ID_INDEX = "albumId";

    /**
     * Index property for album id.
     */
    private static final String ARTIST_ID_INDEX = "artistId";

    /**
     * Song property for song id.
     */
    private static final String SONG_ID_INDEX = "songId";

    /**
     * Index property for file name.
     */
    private static final String FILE_NAME_INDEX = "fileName";

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
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.storage.Storage#getAlbums(java.util.Collection)
     */
    @Override
    @Transactional
    public List<Album> getAlbums(final Collection<Artist> artists) {
        final List<Album> albums = new ArrayList<Album>();

        for (final Artist artist : artists) {

            for (Album album : artist.getAlbums()) {
                albums.add(album);
            }
        }

        return albums;
    }

    @Override
    @Transactional
    public Stack<Album> getAlbumsAsStack(final Collection<Artist> artists) {
        final Stack<Album> albums = new Stack<Album>();

        for (final Artist artist : artists) {
            for (Album album : artist.getAlbums()) {
                albums.push(album);
            }
        }

        return albums;
    }

    /*
     * @see com.quiltplayer.core.storage.Storage#getAlbum(java.lang.String)
     */
    @Override
    @Transactional
    public Album getAlbum(final StringId albumId) {

        Node albumNode = searchSingle(albumId.getId(), ALBUM_ID_INDEX,
                QuiltPlayerRelationshipTypes.ALBUM_ID);

        Album album = null;

        if (albumNode != null) {
            album = new NeoAlbum(albumNode);
        }

        return album;
    }

    @Override
    @Transactional
    public Song getSong(final String albumTitle, final StringId songId) {
        Song song = null;

        Node songNode = searchSingle(songId.getId(), SONG_ID_INDEX,
                QuiltPlayerRelationshipTypes.SONG_ID);

        if (songNode != null) {
            song = new NeoSong(songNode);
        }

        return song;
    }

    /*
     * @see com.quiltplayer.core.storage.Storage#getLocalImage(java.lang.String)
     */
    @Override
    @Transactional
    public LocalImage getLocalImage(final String path) {
        LocalImage image = null;

        Node imageNode = searchSingle(path, FILE_NAME_INDEX,
                QuiltPlayerRelationshipTypes.PART_OF_FILE_NAME);

        if (imageNode != null) {
            image = new NeoLocalImage(imageNode);
        }

        return image;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.storage.Storage#createAlbum(com.quiltplayer.model .StringId)
     */
    @Override
    @Transactional
    public Album createAlbum(final StringId albumId) {
        Album album = null;

        final Node albumNode = neoService.createNode();
        album = new NeoAlbum(albumNode);

        album.setId(albumId);

        index(albumId.getId(), albumNode, ALBUM_ID_INDEX, QuiltPlayerRelationshipTypes.ALBUM_ID);

        log.debug("Album with title '" + albumId.getId() + "' created.");

        return album;
    }

    /*
     * @see com.quiltplayer.core.storage.Storage#createLocalImage(com.quiltplayer .model.Album,
     * java.lang.String)
     */
    @Override
    @Transactional
    public LocalImage createLocalImage(final Album album, final String fileName,
            final LocalImage image) {
        Validate.notNull(album);

        final Node imageNode = neoService.createNode();

        final NeoLocalImage neoImage = new NeoLocalImage(imageNode);

        final Node albumNode = ((NeoAlbum) album).getNode();

        if (image.getType().equals(LocalImage.TYPE_PRIMARY)) {
            log.debug("Adding primary image to album: " + album.getTitle());
            albumNode.createRelationshipTo(imageNode, QuiltPlayerRelationshipTypes.HAS_FRONT_IMAGE);

            Traverser nodes = ((NeoAlbum) album).getNode().traverse(Traverser.Order.DEPTH_FIRST,
                    StopEvaluator.DEPTH_ONE, ReturnableEvaluator.ALL_BUT_START_NODE,
                    QuiltPlayerRelationshipTypes.HAS_FRONT_IMAGE, Direction.OUTGOING);

            int counter = nodes.getAllNodes().size();
            neoImage.setCounter(counter);
        }
        else {
            log.debug("Adding secondary image to album: " + album.getTitle());
            albumNode.createRelationshipTo(imageNode, QuiltPlayerRelationshipTypes.HAS_IMAGE);

            Traverser nodes = ((NeoAlbum) album).getNode().traverse(Traverser.Order.DEPTH_FIRST,
                    StopEvaluator.DEPTH_ONE, ReturnableEvaluator.ALL_BUT_START_NODE,
                    QuiltPlayerRelationshipTypes.HAS_IMAGE, Direction.OUTGOING);

            int counter = nodes.getAllNodes().size();
            neoImage.setCounter(counter);
        }

        index(fileName, imageNode, FILE_NAME_INDEX, QuiltPlayerRelationshipTypes.PART_OF_FILE_NAME);

        neoImage.setType(image.getType());
        neoImage.setSmallImage(image.getSmallImage());
        neoImage.setMediumImage(image.getMediumImage());
        neoImage.setLargeImage(image.getLargeImage());

        return image;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.storage.Storage#createSong(com.quiltplayer.model .StringId)
     */
    @Override
    @Transactional
    public Song createSong(final StringId songId) {
        Song song = null;

        final Node songNode = neoService.createNode();
        song = new NeoSong(songNode);

        song.setId(songId);

        index(songId.getId(), songNode, SONG_ID_INDEX, QuiltPlayerRelationshipTypes.SONG_ID);

        return song;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.storage.Storage#deleteArtist(com.quiltplayer.model .Artist)
     */
    @Override
    @Transactional
    public void delete(final Album album) {
        Node albumNode = ((NeoAlbum) album).getNode();

        indexService.removeIndex(albumNode, album.getId().getId(), "");

        for (Relationship rel : albumNode.getRelationships()) {
            if (rel.isType(QuiltPlayerRelationshipTypes.HAS_SONG)) {
                Node songNode = rel.getEndNode();
                deleteSong(songNode);
            }
            else if (rel.isType(QuiltPlayerRelationshipTypes.HAS_FRONT_IMAGE)
                    || rel.isType(QuiltPlayerRelationshipTypes.HAS_IMAGE)) {
                Node imageNode = rel.getEndNode();
                deleteImage(imageNode);
            }
            else
                rel.delete();
        }

        System.out.println("------------------------------------------");
        albumNode.delete();
    }

    @Transactional
    public void deleteSong(final Node songNode) {
        indexService.removeIndex(songNode, (String) songNode.getProperty(SONG_ID_INDEX), "");

        for (Relationship rel : songNode.getRelationships()) {
            rel.delete();
        }

        songNode.delete();
    }

    @Transactional
    public void deleteImage(final Node imageNode) {
        for (Relationship rel : imageNode.getRelationships()) {
            rel.delete();
        }

        imageNode.delete();
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

}