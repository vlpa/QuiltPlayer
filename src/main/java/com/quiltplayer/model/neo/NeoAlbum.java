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

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import com.quiltplayer.core.QList;
import com.quiltplayer.core.comparators.ImageCounterComparator;
import com.quiltplayer.core.storage.neo.QuiltPlayerRelationshipTypes;
import com.quiltplayer.external.covers.model.LocalImage;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.SongCollection;
import com.quiltplayer.model.StringId;
import com.quiltplayer.properties.Config;

/**
 * Neo implementation for Album.
 * 
 * @author Vlado Palczynski
 */
public class NeoAlbum implements Album {

    /**
     * 
     */
    private static final long serialVersionUID = -2994237324490453333L;

    /**
     * Property album id.
     */
    public static final String PROPERTY_ID = "albumId";

    /**
     * Property spotify album id.
     */
    public static final String PROPERTY_SPOTIFY_ID = "spotifyId";

    /**
     * Property title.
     */
    private static final String PROPERTY_TITLE = "title";

    /**
     * Property type.
     */
    private static final String PROPERTY_TYPE = "type";

    /**
     * Property label.
     */
    private static final String PROPERTY_LABEL = "label";

    /**
     * Property title to publish.
     */
    private static final String PROPERTY_TITLE_TO_PUBLISH = "titleToPublish";

    /**
     * Property year.
     */
    private static final String PROPERTY_YEAR = "year";

    /**
     * Property release id.
     */
    private static final String PROPERTY_RELEASE_ID = "releaseId";

    /**
     * Song collection.
     */
    private transient SongCollection songCollection;

    /**
     * Image comparator.
     */
    private ImageCounterComparator imageCounterComparator = new ImageCounterComparator();

    /**
     * The node.
     */
    private transient Node node;

    public NeoAlbum(final Node node) {
        this.node = node;

        songCollection = new NeoSongCollection(node);
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
     * @see com.quiltplayer.model.Album#addLocalImage(com.quiltplayer.model.LocalImage )
     */
    @Override
    public void addLocalImage(final LocalImage image) {
    }

    public void deleteImages() {
        Transaction tx = NeoTx.beginTx();

        for (LocalImage image : getImages()) {
            Node imageNode = ((NeoLocalImage) image).getNode();

            for (Relationship rel : imageNode.getRelationships()) {
                rel.delete();
            }

            imageNode.delete();
        }

        NeoTx.finishTx(tx);
    }

    /*
     * @see com.quiltplayer.model.Album#getArtist()
     */
    @Override
    public Artist getArtist() {
        Artist artist = null;

        Transaction tx = NeoTx.beginTx();

        for (Relationship rel : node.getRelationships(QuiltPlayerRelationshipTypes.HAS_ALBUM,
                Direction.INCOMING)) {
            artist = new NeoArtist(rel.getStartNode());
        }

        NeoTx.finishTx(tx);

        return artist;
    }

    /*
     * @see com.quiltplayer.model.Album#getId()
     */
    @Override
    public StringId getId() {
        return new StringId((String) Config.getNeoUtil().getProperty(node, PROPERTY_ID));
    }

    /*
     * @see com.quiltplayer.model.Album#setId(com.quiltplayer.model.AlbumId)
     */
    @Override
    public void setId(final StringId albumId) {
        Config.getNeoUtil().setProperty(node, PROPERTY_ID, albumId.getId());

    }

    /*
     * @see com.quiltplayer.model.Album#getImages()
     */
    @Override
    public QList<LocalImage> getImages() {
        Transaction tx = null;

        tx = Config.getNeoDb().beginTx();

        QList<LocalImage> frontImages = getFrontImages();

        QList<LocalImage> otherImages = getOtherImages();

        frontImages.addAll(otherImages);

        tx.success();
        tx.finish();

        return frontImages;
    }

    private QList<LocalImage> getFrontImages() {
        final QList<LocalImage> frontImages = new QList<LocalImage>();

        for (Relationship rel : node.getRelationships(QuiltPlayerRelationshipTypes.HAS_FRONT_IMAGE,
                Direction.OUTGOING)) {
            frontImages.add(new NeoLocalImage(rel.getEndNode()));
        }

        Collections.sort(frontImages, imageCounterComparator);

        return frontImages;
    }

    private QList<LocalImage> getOtherImages() {
        final QList<LocalImage> otherImages = new QList<LocalImage>();

        for (Relationship rel : node.getRelationships(QuiltPlayerRelationshipTypes.HAS_IMAGE,
                Direction.OUTGOING)) {
            otherImages.add(new NeoLocalImage(rel.getEndNode()));
        }

        Collections.sort(otherImages, imageCounterComparator);

        return otherImages;
    }

    /*
     * @see com.quiltplayer.model.Album#getFrontImage()
     */
    @Override
    public LocalImage getFrontImage() {
        LocalImage image = null;

        Transaction tx = NeoTx.beginTx();

        List<LocalImage> images = getFrontImages();

        if (images.size() > 0) {
            image = images.get(0);
        }
        else {
            images = getOtherImages();
            if (images.size() > 0) {
                image = images.get(0);
            }
        }

        NeoTx.finishTx(tx);

        return image;
    }

    /*
     * @see com.quiltplayer.model.Album#getLastModified()
     */
    @Override
    public long getLastModified() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * @see com.quiltplayer.model.Album#getReleaseId()
     */
    @Override
    public String getReleaseId() {
        return (String) node.getProperty(PROPERTY_RELEASE_ID);
    }

    /*
     * @see com.quiltplayer.model.Album#getSongCollection()
     */
    @Override
    public SongCollection getSongCollection() {
        return songCollection;
    }

    /*
     * @see com.quiltplayer.model.Album#setSongCollection(com.quiltplayer.model. SongCollection)
     */
    @Override
    public void setSongCollection(final SongCollection songCollection) {
        this.songCollection = songCollection;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#addSong(com.quiltplayer.model.Song)
     */
    @Override
    public void addSong(Song song) {
        Transaction tx = NeoTx.beginTx();

        node
                .createRelationshipTo(((NeoSong) song).getNode(),
                        QuiltPlayerRelationshipTypes.HAS_SONG);

        NeoTx.finishTx(tx);

    }

    /*
     * @see com.quiltplayer.model.Album#getTitle()
     */
    @Override
    public String getTitle() {
        return (String) Config.getNeoUtil().getProperty(node, PROPERTY_TITLE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#getLabel()
     */
    @Override
    public String getLabel() {
        if (node.hasProperty(PROPERTY_LABEL))
            return (String) Config.getNeoUtil().getProperty(node, PROPERTY_LABEL);

        return "";
    }

    /*
     * @see com.quiltplayer.model.Album#getTitleToPublish()
     */
    @Override
    public String getTitleToPublish() {
        return (String) Config.getNeoUtil().getProperty(node, PROPERTY_TITLE_TO_PUBLISH);
    }

    /*
     * @see com.quiltplayer.model.Album#getYear()
     */
    @Override
    public String getYear() {

        String year = "";

        Transaction tx = NeoTx.beginTx();

        if (node.hasProperty(PROPERTY_YEAR)) {
            year = (String) node.getProperty(PROPERTY_YEAR);
        }

        NeoTx.finishTx(tx);

        return year;
    }

    /*
     * @see com.quiltplayer.model.Album#setArtist(com.quiltplayer.model.Artist)
     */
    @Override
    public void setArtist(final Artist artist) {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setImages(java.util.List)
     */
    @Override
    public void setImages(final List<LocalImage> images) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setLastModified(long)
     */
    @Override
    public void setLastModified(final long timestamp) {
        // TODO Auto-generated method stub
    }

    /*
     * @see com.quiltplayer.model.Album#setReleaseId(java.lang.String)
     */
    @Override
    public void setReleaseId(final String releaseId) {
        Config.getNeoUtil().setProperty(node, PROPERTY_RELEASE_ID, releaseId);
    }

    /*
     * @see com.quiltplayer.model.Album#setTitle(java.lang.String)
     */
    @Override
    public void setTitle(final String title) {
        Config.getNeoUtil().setProperty(node, PROPERTY_TITLE, title);

        setTitleToPublish(title);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setLabel(java.lang.String)
     */
    @Override
    public void setLabel(final String label) {
        if (StringUtils.isNotBlank(label))
            Config.getNeoUtil().setProperty(node, PROPERTY_LABEL, label);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setTitleToPublish(java.lang.String)
     */
    @Override
    public void setTitleToPublish(final String titleToPublish) {
        Config.getNeoUtil().setProperty(node, PROPERTY_TITLE_TO_PUBLISH, titleToPublish);
    }

    /*
     * @see com.quiltplayer.model.Album#setYear(java.lang.String)
     */
    @Override
    public void setYear(final String year) {
        if (StringUtils.isNotBlank(year)) {
            Config.getNeoUtil().setProperty(node, PROPERTY_YEAR, year);
        }
    }

    /*
     * @see com.quiltplayer.model.Album#changeFrontImage(com.quiltplayer.model.LocalImage )
     */
    @Override
    public void changeFrontImage(final Album album, final LocalImage toFrontImage) {

        Transaction tx = NeoTx.beginTx();

        /*
         * Remove all primary pictures
         */
        for (LocalImage image : album.getImages()) {
            NeoLocalImage neoImage = (NeoLocalImage) image;
            for (Relationship rel : neoImage.getNode().getRelationships(
                    QuiltPlayerRelationshipTypes.HAS_FRONT_IMAGE, Direction.INCOMING)) {
                rel.delete();
            }

            node.createRelationshipTo(neoImage.getNode(), QuiltPlayerRelationshipTypes.HAS_IMAGE);

            neoImage.setType(LocalImage.TYPE_SECONDARY);
        }

        if (toFrontImage != null) {
            for (Relationship rel : ((NeoLocalImage) toFrontImage).getNode().getRelationships(
                    QuiltPlayerRelationshipTypes.HAS_IMAGE, Direction.INCOMING)) {
                rel.delete();
            }
            node.createRelationshipTo(((NeoLocalImage) toFrontImage).getNode(),
                    QuiltPlayerRelationshipTypes.HAS_FRONT_IMAGE);

            toFrontImage.setType(LocalImage.TYPE_PRIMARY);
        }

        NeoTx.finishTx(tx);
    }

    /*
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Album o) {
        return getTitle().compareTo(o.getTitle());
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof NeoAlbum) {
            return this.node.equals(((NeoAlbum) o).getNode());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.node.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Album: " + getTitle() + " by " + getArtist().getArtistName().getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#getType()
     */
    @Override
    public String getType() {
        String type = null;

        Transaction tx = NeoTx.beginTx();

        if (node.hasProperty(PROPERTY_TYPE)) {
            type = (String) node.getProperty(PROPERTY_TYPE);
        }

        NeoTx.finishTx(tx);

        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
        Config.getNeoUtil().setProperty(node, PROPERTY_TYPE, type);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#getSpotifyId()
     */
    @Override
    public String getSpotifyId() {
        String spotifyId = null;

        Transaction tx = NeoTx.beginTx();

        if (node.hasProperty(PROPERTY_SPOTIFY_ID)) {
            spotifyId = (String) node.getProperty(PROPERTY_SPOTIFY_ID);
        }

        NeoTx.finishTx(tx);

        return spotifyId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setSpotifyId(java.lang.String)
     */
    @Override
    public void setSpotifyId(String spotifyId) {
        if (spotifyId != null) {
            Config.getNeoUtil().setProperty(node, PROPERTY_SPOTIFY_ID, spotifyId);
        }
    }
}
