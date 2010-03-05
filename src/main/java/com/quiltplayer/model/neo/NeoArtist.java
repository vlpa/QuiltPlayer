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
package com.quiltplayer.model.neo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;

import com.quiltplayer.core.comparators.AlbumImageComparator;
import com.quiltplayer.core.comparators.YearComparator;
import com.quiltplayer.core.storage.neo.QuiltPlayerRelationshipTypes;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.model.ArtistName;
import com.quiltplayer.model.StringId;
import com.quiltplayer.properties.Config;

/**
 * Neo implementation for Artist.
 * 
 * @author Vlado Palczynski
 */
public class NeoArtist extends NeoTx implements Artist, Comparable<Artist> {
    private static final String PROPERTY_ID = "albumId";

    private static final String PROPERTY_NAME = "name";

    public static final String PROPERTY_SPOTIFY_ID = "spotifyId";

    private Node node;

    private YearComparator yearComparator = new YearComparator();

    private AlbumImageComparator albumImageComparator = new AlbumImageComparator();

    public NeoArtist(final Node node) {
        this.node = node;
    }

    /*
     * @see com.quiltplayer.model.Artist#setId(com.quiltplayer.model.ArtistId)
     */
    @Override
    public void setId(final StringId id) {
        Config.getNeoUtil().setProperty(node, PROPERTY_ID, id.getId());
    }

    /**
     * Get the node.
     * 
     * @return the node.
     */
    public Node getNode() {
        return node;
    }

    /*
     * @see com.quiltplayer.model.ArtistInterface#getAlbums()
     */
    @Override
    public List<Album> getAlbums() {
        List<Album> albums = null;

        Transaction tx = null;
        try {
            tx = Config.getNeoDb().beginTx();

            albums = new ArrayList<Album>();

            for (Relationship rel : node.getRelationships(QuiltPlayerRelationshipTypes.HAS_ALBUM,
                    Direction.OUTGOING)) {
                albums.add(new NeoAlbum(rel.getEndNode()));
            }

            tx.success();
        }
        finally {
            tx.finish();
        }

        Collections.sort(albums, albumImageComparator);
        Collections.sort(albums, yearComparator);

        return albums;
    }

    /*
     * @see com.quiltplayer.model.Artist#getId()
     */
    @Override
    public StringId getStringId() {
        return new StringId((String) Config.getNeoUtil().getProperty(node, PROPERTY_ID));
    }

    /*
     * @see com.quiltplayer.model.Artist#getArtistName()
     */
    @Override
    public ArtistName getArtistName() {
        return new ArtistName((String) Config.getNeoUtil().getProperty(node, PROPERTY_NAME));
    }

    /*
     * @see com.quiltplayer.model.ArtistInterface#addAlbum(com.quiltplayer.model. Album)
     */
    @Override
    public void addAlbum(final Album album) {
        Transaction tx = beginTx();

        node.createRelationshipTo(((NeoAlbum) album).getNode(),
                QuiltPlayerRelationshipTypes.HAS_ALBUM);

        finishTx(tx);
    }

    /*
     * @see com.quiltplayer.model.ArtistInterface#removeAlbum(com.quiltplayer.model. Album)
     */
    @Override
    public void removeAlbum(final Album album) {
        Transaction tx = beginTx();

        Iterable<Relationship> relationships = node.getRelationships();

        for (Relationship rel : relationships) {
            if (rel.getEndNode().getId() == ((NeoAlbum) album).getNode().getId())
                rel.delete();
        }

        finishTx(tx);
    }

    /*
     * @see com.quiltplayer.model.Artist#setArtistName(com.quiltplayer.model.ArtistName )
     */
    @Override
    public void setArtistName(final ArtistName artistName) {
        Config.getNeoUtil().setProperty(node, PROPERTY_NAME, artistName.getName());
    }

    /*
     * @see com.quiltplayer.model.Artist#isThe()
     */
    @Override
    public boolean isThe() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * @see com.quiltplayer.model.Artist#setThe(boolean)
     */
    @Override
    public void setThe(final boolean the) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Artist o) {
        if (o == null) {
            return 1;
        }

        return ((String) node.getProperty(PROPERTY_NAME)).compareTo(o.getArtistName().getName());
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof NeoArtist) {
            return this.node.equals(((NeoArtist) o).getNode());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.node.hashCode();
    }

    public boolean hasAlbums() {
        Transaction tx = beginTx();

        Traverser nodes = node.traverse(Traverser.Order.DEPTH_FIRST, StopEvaluator.DEPTH_ONE,
                ReturnableEvaluator.ALL_BUT_START_NODE, QuiltPlayerRelationshipTypes.HAS_ALBUM,
                Direction.OUTGOING);

        if (nodes.getAllNodes().size() == 0) {
            finishTx(tx);

            return false;
        }

        finishTx(tx);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Artist#hasAlbum(com.quiltplayer.model.Album)
     */
    @Override
    public boolean hasAlbum(Album album) {
        Transaction tx = beginTx();

        Traverser nodes = node.traverse(Traverser.Order.DEPTH_FIRST, StopEvaluator.DEPTH_ONE,
                ReturnableEvaluator.ALL_BUT_START_NODE, QuiltPlayerRelationshipTypes.HAS_ALBUM,
                Direction.OUTGOING);

        Collection<Node> c = nodes.getAllNodes();
        if (c.size() == 0) {
            finishTx(tx);

            return false;
        }
        else {
            for (Node node : c) {
                if (node.getProperty(NeoAlbum.PROPERTY_ID).equals(album.getId().getId())) {
                    finishTx(tx);
                    return true;
                }

            }
        }

        finishTx(tx);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Artist#getSpotifyId()
     */
    @Override
    public String getSpotifyId() {
        String spotifyId = null;
        Transaction tx = beginTx();

        if (node.hasProperty(PROPERTY_SPOTIFY_ID)) {
            spotifyId = (String) node.getProperty(PROPERTY_SPOTIFY_ID);
        }

        finishTx(tx);

        return spotifyId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Artist#setSpotifyId(java.lang.String)
     */
    @Override
    public void setSpotifyId(String spotifyId) {
        if (spotifyId != null) {
            Config.getNeoUtil().setProperty(node, PROPERTY_SPOTIFY_ID, spotifyId);
        }

    }

}
