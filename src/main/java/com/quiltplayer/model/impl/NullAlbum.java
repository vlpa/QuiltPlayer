package com.quiltplayer.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.quiltplayer.external.covers.model.LocalImage;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.SongCollection;
import com.quiltplayer.model.StringId;

/**
 * Model class representing an album without data, will point to default picture and text.
 * 
 * @author Vlado Palczynski
 */
public class NullAlbum implements Album, Serializable {
    private static final long serialVersionUID = 5548348854173325894L;

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setTitleToPublish(java.lang.String)
     */
    @Override
    public void setTitleToPublish(final String titleToPublish) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#getLastModified()
     */
    @Override
    public long getLastModified() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setLastModified(long)
     */
    @Override
    public void setLastModified(final long timestamp) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.model.Album#getReleaseId()
     */
    @Override
    public String getReleaseId() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.model.Album#setReleaseId(java.lang.String)
     */
    @Override
    public void setReleaseId(final String releaseId) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.coverok.model.Album#addLocalImage(org.coverok.model.LocalImage)
     */
    @Override
    public void addLocalImage(final LocalImage image) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#getSongCollection()
     */
    @Override
    public SongCollection getSongCollection() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.quiltplayer.model.Album#setSongCollection(com.quiltplayer.model. SongCollection)
     */
    @Override
    public void setSongCollection(final SongCollection songCollection) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.model.Album#getTitleToPublish()
     */
    @Override
    public String getTitleToPublish() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.coverok.model.Album#getId()
     */
    @Override
    public StringId getId() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#getFrontImage()
     */
    @Override
    public LocalImage getFrontImage() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setId(com.quiltplayer.model.StringId)
     */
    @Override
    public void setId(final StringId albumId) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.coverok.model.Album#getImages()
     */
    @Override
    public List<LocalImage> getImages() {
        final List<LocalImage> images = new ArrayList<LocalImage>();

        // LocalImage image = new LocalImageImpl()

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.coverok.model.Album#getTitle()
     */
    @Override
    public String getTitle() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.coverok.model.Album#setImages(java.util.List)
     */
    @Override
    public void setImages(final List<LocalImage> images) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.coverok.model.Album#setTitle(java.lang.String)
     */
    @Override
    public void setTitle(final String title) {
    }

    /*
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Album o) {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.coverok.model.Album#getYear()
     */
    @Override
    public String getYear() {
        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.coverok.model.Album#setYear(java.lang.String)
     */
    @Override
    public void setYear(final String year) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#getArtist()
     */
    @Override
    public Artist getArtist() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setArtist(com.quiltplayer.model.Artist)
     */
    @Override
    public void setArtist(final Artist artist) {
    }

    /*
     * @see com.quiltplayer.model.Album#changeFrontImage(com.quiltplayer.model.LocalImage )
     */
    @Override
    public void changeFrontImage(Album album, LocalImage toFrontImage) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#addSong(com.quiltplayer.model.Song)
     */
    @Override
    public void addSong(Song song) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#getLabel()
     */
    @Override
    public String getLabel() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setLabel(java.lang.String)
     */
    @Override
    public void setLabel(String label) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#getType()
     */
    @Override
    public String getType() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#getSpotifyId()
     */
    @Override
    public String getSpotifyId() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setSpotifyId(java.lang.String)
     */
    @Override
    public void setSpotifyId(String spotifyId) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#deleteImages()
     */
    @Override
    public void deleteImages() {
        // TODO Auto-generated method stub

    }

}
