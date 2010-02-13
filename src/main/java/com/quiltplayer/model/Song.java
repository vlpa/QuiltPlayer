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
package com.quiltplayer.model;

/**
 * @author Vlado Palczynski
 */
public interface Song {
    String TYPE_FILE = "file";

    String TYPE_SPOTIFY = "spotify";

    /**
     * @param songId
     *            the songId to set.
     */
    void setId(StringId songId);

    /**
     * @return the id.
     */
    StringId getId();

    /**
     * @param spotifyId
     *            the spotifyId to set.
     */
    void setSpotifyId(String spotifyId);

    /**
     * @return the spotifyId.
     */
    String getSpotifyId();

    /**
     * @return the album.
     */
    Album getAlbum();

    /**
     * @param album
     *            the album to set.
     */
    void setAlbum(Album album);

    /**
     * @return the title
     */
    String getTitle();

    /**
     * @param title
     *            the title to set
     */
    void setTitle(String title);

    /**
     * @return the path
     */
    String getPath();

    /**
     * @param path
     *            the path to set
     */
    void setPath(String path);

    /**
     * @return the trackNumber
     */
    Number getTrackNumber();

    /**
     * @param trackNumber
     *            the trackNumber to set
     */
    void setTrackNumber(Number trackNumber);

    /**
     * @return the fileName
     */
    String getFileName();

    /**
     * @param fileName
     *            the fileName to set
     */
    void setFileName(String fileName);

    void setType(String type);

    String getType();

}