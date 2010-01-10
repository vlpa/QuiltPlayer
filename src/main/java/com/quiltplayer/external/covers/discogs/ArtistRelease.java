package com.quiltplayer.external.covers.discogs;

import java.io.Serializable;

/**
 * @author Vlado Palczynski
 */
public class ArtistRelease implements Serializable {

    private static final long serialVersionUID = -4598562729644282433L;

    private String id;

    private String title;

    private String trackinfo;

    private String format;

    private String label;

    private String year;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the trackinfo
     */
    public String getTrackinfo() {
        return trackinfo;
    }

    /**
     * @param trackinfo
     *            the trackinfo to set
     */
    public void setTrackinfo(String trackinfo) {
        this.trackinfo = trackinfo;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format
     *            the format to set
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year
     *            the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

}