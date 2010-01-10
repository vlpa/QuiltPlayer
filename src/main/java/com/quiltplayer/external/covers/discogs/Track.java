package com.quiltplayer.external.covers.discogs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vlado
 */
public class Track implements Serializable {

    private static final long serialVersionUID = 6572558046209319062L;

    private String position;

    private String title;

    private String duration;

    private String artists;

    private List<Artist> extraartists = new ArrayList<Artist>();

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position
     *            the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration
     *            the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return the extraartists
     */
    public List<Artist> getExtraartists() {
        return extraartists;
    }

    /**
     * @param extraartists
     *            the extraartists to set
     */
    public void setExtraartists(List<Artist> extraartists) {
        this.extraartists = extraartists;
    }

    /**
     * @return the artists
     */
    public final String getArtists() {
        return artists;
    }

    /**
     * @param artists
     *            the artists to set
     */
    public final void setArtists(String artists) {
        this.artists = artists;
    }
}
