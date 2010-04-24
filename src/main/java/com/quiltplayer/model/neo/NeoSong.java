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

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import com.quiltplayer.core.storage.neo.QuiltPlayerRelationshipTypes;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.StringId;
import com.quiltplayer.properties.Config;

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

    private static final String PROPERTY_DURATION = "duration";

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#getId()
     */
    @Override
    public StringId getId() {
        return new StringId((String) Config.getNeoUtil().getProperty(node, PROPERTY_ID));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#setId(com.quiltplayer.model.StringId)
     */
    @Override
    public void setId(final StringId songId) {
        Config.getNeoUtil().setProperty(node, PROPERTY_ID, songId.getId());
    }

    /*
     * @see com.quiltplayer.model.Song#getAlbum()
     */
    @Override
    public Album getAlbum() {
        Transaction tx = null;
        try {
            tx = Config.getNeoDb().beginTx();

            Album album = null;

            for (Relationship rel : node.getRelationships(QuiltPlayerRelationshipTypes.HAS_SONG, Direction.INCOMING)) {
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
        return (String) Config.getNeoUtil().getProperty(node, PROPERTY_FILE_NAME);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.SongInterface#getPath()
     */
    @Override
    public String getPath() {
        return (String) Config.getNeoUtil().getProperty(node, PROPERTY_PATH);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.SongInterface#getTitle()
     */
    @Override
    public String getTitle() {
        return (String) Config.getNeoUtil().getProperty(node, PROPERTY_TITLE);
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
        Config.getNeoUtil().setProperty(node, PROPERTY_FILE_NAME, fileName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.SongInterface#setPath(java.lang.String)
     */
    @Override
    public void setPath(final String path) {
        Config.getNeoUtil().setProperty(node, PROPERTY_PATH, path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.SongInterface#setTitle(java.lang.String)
     */
    @Override
    public void setTitle(final String title) {
        Config.getNeoUtil().setProperty(node, PROPERTY_TITLE, title);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.SongInterface#setTrackNumber(java.lang.Number)
     */
    @Override
    public void setTrackNumber(final Number trackNumber) {
        if (trackNumber != null) {
            Config.getNeoUtil().setProperty(node, PROPERTY_TRACK_NUMBER, trackNumber);
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
        return Config.getNeoDb().beginTx();
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
            Config.getNeoUtil().setProperty(node, PROPERTY_SPOTIFY_ID, spotifyId);
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
            Config.getNeoUtil().setProperty(node, PROPERTY_TYPE, type);
        }
    }

    @Override
    public int getDuration() {
        return ((Integer) Config.getNeoUtil().getProperty(node, PROPERTY_DURATION));
    }

    @Override
    public void setDuration(int length) {
        if (length > 0) {
            Config.getNeoUtil().setProperty(node, PROPERTY_DURATION, length);
        }
    }
}
