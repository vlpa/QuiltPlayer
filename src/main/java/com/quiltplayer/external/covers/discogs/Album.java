package com.quiltplayer.external.covers.discogs;

import java.io.Serializable;
import java.util.List;

import com.quiltplayer.external.covers.model.LocalImage;

/**
 * 
 * 
 * @author Vlado Palczynski
 */
public class Album implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3746506126665828470L;

    private Artist artist;

    private String title;

    private String year;

    private List<LocalImage> images;

    private String releaseId;

    private List<Label> labels;

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

    /**
     * @return the images
     */
    public List<LocalImage> getImages() {
        return images;
    }

    /**
     * @param images
     *            the images to set
     */
    public void setImages(List<LocalImage> images) {
        this.images = images;
    }

    /**
     * @return the releaseId
     */
    public String getReleaseId() {
        return releaseId;
    }

    /**
     * @param releaseId
     *            the releaseId to set
     */
    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    /**
     * @return the labels
     */
    public final List<Label> getLabels() {
        return labels;
    }

    /**
     * @param labels
     *            the labels to set
     */
    public final void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}
