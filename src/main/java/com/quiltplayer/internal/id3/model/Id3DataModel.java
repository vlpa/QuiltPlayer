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
package com.quiltplayer.internal.id3.model;

import java.io.File;

/**
 * Data extracted from the ID3-engine.
 * 
 * @author Vlado Palczynski
 */
public class Id3DataModel {
    /**
     * The artist name.
     */
    private String artistName;

    /**
     * The album title.
     */
    private String albumTitle;

    /**
     * The song title.
     */
    private String songTitle;

    /**
     * the track number.
     */
    private Number trackNumber;

    /**
     * The path to the file.
     */
    private File path;

    /**
     * The duration.
     */
    private int duration;

    /**
     * @return the artistName
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * @param artistName
     *            the artistName to set
     */
    public void setArtistName(final String artistName) {
        this.artistName = artistName;
    }

    /**
     * @return the albumTitle
     */
    public String getAlbumTitle() {
        return albumTitle;
    }

    /**
     * @param albumTitle
     *            the albumTitle to set
     */
    public void setAlbumTitle(final String albumTitle) {
        this.albumTitle = albumTitle;
    }

    /**
     * @return the songTitle
     */
    public String getSongTitle() {
        return songTitle;
    }

    /**
     * @param songTitle
     *            the songTitle to set
     */
    public void setSongTitle(final String songTitle) {
        this.songTitle = songTitle;
    }

    /**
     * @return the trackNumber
     */
    public Number getTrackNumber() {
        return trackNumber;
    }

    /**
     * @param trackNumber
     *            the trackNumber to set
     */
    public void setTrackNumber(final Number trackNumber) {
        this.trackNumber = trackNumber;
    }

    /**
     * @return the path
     */
    public File getPath() {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(File path) {
        this.path = path;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration
     *            the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
