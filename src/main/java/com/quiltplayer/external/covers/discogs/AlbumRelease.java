package com.quiltplayer.external.covers.discogs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlbumRelease implements Serializable {
    private static final long serialVersionUID = 1078896253152336962L;

    private String id;

    private List<Image> images = new ArrayList<Image>();

    private String name;

    private List<String> artists = new ArrayList<String>();

    private List<Artist> extraartists = new ArrayList<Artist>();

    private String title;

    private List<Label> labels = new ArrayList<Label>();

    private List<Format> formats = new ArrayList<Format>();

    private List<String> genres = new ArrayList<String>();

    private List<String> styles = new ArrayList<String>();

    private String country;

    private String released;

    private String notes;

    private List<Track> tracklist = new ArrayList<Track>();

    /**
     * @return the images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * @param images
     *            the images to set
     */
    public void setImages(final List<Image> images) {
        this.images = images;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the artists
     */
    public List<String> getArtists() {
        return artists;
    }

    /**
     * @param artists
     *            the artists to set
     */
    public void setArtists(final List<String> artists) {
        this.artists = artists;
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
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * @return the labels
     */
    public List<Label> getLabels() {
        return labels;
    }

    /**
     * @param labels
     *            the labels to set
     */
    public void setLabels(final List<Label> labels) {
        this.labels = labels;
    }

    /**
     * @return the formats
     */
    public List<Format> getFormats() {
        return formats;
    }

    /**
     * @param formats
     *            the formats to set
     */
    public void setFormats(final List<Format> formats) {
        this.formats = formats;
    }

    /**
     * @return the styles
     */
    public List<String> getStyles() {
        return styles;
    }

    /**
     * @param styles
     *            the styles to set
     */
    public void setStyles(final List<String> styles) {
        this.styles = styles;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * @return the released
     */
    public String getReleased() {
        return released;
    }

    /**
     * @param released
     *            the released to set
     */
    public void setReleased(final String released) {
        this.released = released;
    }

    /**
     * @return the tracklist
     */
    public List<Track> getTracklist() {
        return tracklist;
    }

    /**
     * @param tracklist
     *            the tracklist to set
     */
    public void setTracklist(final List<Track> tracklist) {
        this.tracklist = tracklist;
    }

    /**
     * @return the genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * @param genres
     *            the genres to set
     */
    public void setGenres(final List<String> genres) {
        this.genres = genres;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes
     *            the notes to set
     */
    public void setNotes(final String notes) {
        this.notes = notes;
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
    public void setExtraartists(final List<Artist> extraartists) {
        this.extraartists = extraartists;
    }

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
    public void setId(final String id) {
        this.id = id;
    }

}
