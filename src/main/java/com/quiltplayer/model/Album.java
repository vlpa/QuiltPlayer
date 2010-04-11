package com.quiltplayer.model;

import java.util.List;

import com.quiltplayer.external.covers.model.LocalImage;

/**
 * Model class representing an album.
 * 
 * @author Vlado Palczynski
 */
public interface Album extends Comparable<Album> {

    String TYPE_FILE = "file";

    String TYPE_SPOTIFY = "spotify";

    /**
     * @return when last modified.
     */
    long getLastModified();

    /**
     * @param timestamp
     *            the timestamp to set.
     */
    void setLastModified(long timestamp);

    /**
     * @return the title in lower case
     */
    String getTitle();

    /**
     * @param title
     *            the title to set
     */
    void setTitle(String title);

    /**
     * @return the title to publish (cased).
     */
    String getTitleToPublish();

    /**
     * @param titleToPublish
     *            the titleToPublish to set
     */
    void setTitleToPublish(String titleToPublish);

    /**
     * @return the id
     */
    StringId getId();

    /**
     * @param albumId
     *            the albumId to set
     */
    void setId(StringId albumId);

    /**
     * @return the spotifyId
     */
    String getSpotifyId();

    /**
     * @param spotifyId
     *            the spotifyId to set
     */
    void setSpotifyId(String spotifyId);

    /**
     * @return the releaseId
     */
    String getReleaseId();

    /**
     * @param id
     *            the id to set.
     */
    void setReleaseId(String releaseId);

    /**
     * @return the Artist
     */
    Artist getArtist();

    /**
     * @param artist
     *            the artist to set
     */
    void setArtist(final Artist artist);

    /**
     * @return the songs
     */
    SongCollection getSongCollection();

    /**
     * @param songs
     *            the songs to add
     */
    void setSongCollection(SongCollection songCollection);

    /**
     * Add song to this album.
     * 
     * @param song
     *            the song to add.
     */
    void addSong(Song song);

    /**
     * Get all images for this album.
     * 
     * @return List of Image objects
     */
    List<LocalImage> getImages();

    /**
     * Delete all images for this album.
     */
    void deleteImages();

    /**
     * Get the front image.
     * 
     * @return Front image
     */
    LocalImage getFrontImage();

    /**
     * @param images
     *            the images to set.
     */
    void setImages(List<LocalImage> images);

    /**
     * Add a image to this album.
     * 
     * @param image
     *            the image to add.
     */
    void addLocalImage(LocalImage image);

    /**
     * @param year
     *            the year to add
     */
    void setYear(String year);

    /**
     * @return the year
     */
    String getYear();

    /**
     * @param toFrontImage
     *            the localImage to set as front.
     */
    void changeFrontImage(Album album, LocalImage toFrontImage);

    String getLabel();

    void setLabel(String label);

    String getType();

    void setType(String type);
}