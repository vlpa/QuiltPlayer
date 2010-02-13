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

import org.neo4j.api.core.Direction;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Relationship;
import org.neo4j.api.core.Transaction;

import com.quiltplayer.core.storage.neo.NeoSingelton;
import com.quiltplayer.core.storage.neo.QuiltPlayerRelationshipTypes;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.StringId;

/**
 * Neo implementation for Song.
 * 
 * @author Vlado Palczynski
 */
public class NeoSong implements Song {
    private static final String PROPERTY_ID = "songId";

    private static final String PROPERTY_SPOTIFY_ID = "spotifyId";

    private static final String PROPERTY_TYPE = "type";

    private static final String PROPERTY_FILE_NAME = "fileName";

    private static final String PROPERTY_PATH = "path";

    private static final String PROPERTY_TITLE = "title";

    private static final String PROPERTY_TRACK_NUMBER = "trackNumber";

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#getId()
     */
    @Override
    public StringId getId() {
        return new StringId((String) NeoSingelton.getInstance().getNeoUtil().getProperty(node,
                PROPERTY_ID));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#setId(com.quiltplayer.model.StringId)
     */
    @Override
    public void setId(final StringId songId) {
        NeoSingelton.getInstance().getNeoUtil().setProperty(node, PROPERTY_ID, songId.getId());
    }

    /*
     * @see com.quiltplayer.model.Song#getAlbum()
     */
    @Override
    public Album getAlbum() {
        Transaction tx = null;
        try {
            tx = NeoSingelton.getInstance().getNeoService().beginTx();

            Album album = null;

            for (Relationship rel : node.getRelationships(QuiltPlayerRelationshipTypes.HAS_SONG,
                    Direction.INCOMING)) {
                album = new NeoAlbum(rel.getStartNode());
            }
            tx.success();

            return album;
        }
        catch (Exception e) {
            tx.failure();
        }
        finally {
            tx.finish();
        }

        return null;
    }

    /*
     * @see com.quiltplayer.model.Song#setAlbum(com.quiltplayer.model.Album)
     */
    @Override
    public void setAlbum(final Album album) {
    }

    private Node node;

    public NeoSong(final Node node) {
        this.node = node;
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
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.SongInterface#getFileName()
     */
    @Override
    public String getFileName() {
        return (String) NeoSingelton.getInstance().getNeoUtil().getProperty(node,
                PROPERTY_FILE_NAME);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.SongInterface#getPath()
     */
    @Override
    public String getPath() {
        return (String) NeoSingelton.getInstance().getNeoUtil().getProperty(node, PROPERTY_PATH);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.SongInterface#getTitle()
     */
    @Override
    public String getTitle() {
        return (String) NeoSingelton.getInstance().getNeoUtil().getProperty(node, PROPERTY_TITLE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.SongInterface#getTrackNumber()
     */
    @Override
    public Number getTrackNumber() {
        Number trackNumber = null;
        Transaction tx = beginTransaction();

        if (node.hasProperty(PROPERTY_TRACK_NUMBER)) {
            trackNumber = (Number) node.getProperty(PROPERTY_TRACK_NUMBER);
        }

        finishTransaction(tx);

        return trackNumber;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.SongInterface#setFileName(java.lang.String)
     */
    @Override
    public void setFileName(final String fileName) {
        NeoSingelton.getInstance().getNeoUtil().setProperty(node, PROPERTY_FILE_NAME, fileName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.SongInterface#setPath(java.lang.String)
     */
    @Override
    public void setPath(final String path) {
        NeoSingelton.getInstance().getNeoUtil().setProperty(node, PROPERTY_PATH, path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.SongInterface#setTitle(java.lang.String)
     */
    @Override
    public void setTitle(final String title) {
        NeoSingelton.getInstance().getNeoUtil().setProperty(node, PROPERTY_TITLE, title);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.SongInterface#setTrackNumber(java.lang.Number)
     */
    @Override
    public void setTrackNumber(final Number trackNumber) {
        if (trackNumber != null) {
            NeoSingelton.getInstance().getNeoUtil().setProperty(node, PROPERTY_TRACK_NUMBER,
                    trackNumber);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof NeoSong) {
            return this.node.equals(((NeoSong) o).getNode());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.node.hashCode();
    }

    private Transaction beginTransaction() {
        return NeoSingelton.getInstance().getNeoService().beginTx();
    }

    private void finishTransaction(final Transaction tx) {
        tx.success();
        tx.finish();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getTitle();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#getSpotifyId()
     */
    @Override
    public String getSpotifyId() {
        String spotifyId = null;
        Transaction tx = beginTransaction();

        if (node.hasProperty(PROPERTY_SPOTIFY_ID)) {
            spotifyId = (String) node.getProperty(PROPERTY_SPOTIFY_ID);
        }

        finishTransaction(tx);

        return spotifyId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#setSpotifyId(java.lang.String)
     */
    @Override
    public void setSpotifyId(String spotifyId) {
        if (spotifyId != null) {
            NeoSingelton.getInstance().getNeoUtil().setProperty(node, PROPERTY_SPOTIFY_ID,
                    spotifyId);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#getType()
     */
    @Override
    public String getType() {
        String type = null;
        Transaction tx = beginTransaction();

        if (node.hasProperty(PROPERTY_TYPE)) {
            type = (String) node.getProperty(PROPERTY_TYPE);
        }

        finishTransaction(tx);

        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
        if (type != null) {
            NeoSingelton.getInstance().getNeoUtil().setProperty(node, PROPERTY_TYPE, type);
        }
    }
}
