package com.quiltplayer.external.covers.discogs;

import java.io.Serializable;

/**
 * @author vlado
 */
public class Resp implements Serializable {

    private static final long serialVersionUID = 3973123181727703837L;

    private Artist artist;

    private AlbumRelease release;

    private String requests;

    /**
     * @return the artist
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * @param artist
     *            the artist to set
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * @return the albumRelease
     */
    public AlbumRelease getAlbumRelease() {
        return release;
    }

    /**
     * @param albumRelease
     *            the albumRelease to set
     */
    public void setAlbumRelease(AlbumRelease albumRelease) {
        this.release = albumRelease;
    }

    /**
     * @return the requests
     */
    public String getRequests() {
        return requests;
    }

    /**
     * @param requests
     *            the requests to set
     */
    public void setRequests(String requests) {
        this.requests = requests;
    }
}
