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

import java.util.List;

/**
 * Artist model interface.
 * 
 * @author Vlado Palczynski
 */
public interface Artist {
    /**
     * @return the id.
     */
    StringId getStringId();

    /**
     * @param id
     *            the id to set.
     */
    void setId(StringId id);

    /**
     * @return the spotifyId.
     */
    String getSpotifyId();

    /**
     * @param spotifyId
     *            the spotifyId to set.
     */
    void setSpotifyId(String spotifyId);

    /**
     * @return the artistName.
     */
    ArtistName getArtistName();

    /**
     * @param artistName
     *            the artistName to set.
     */
    void setArtistName(ArtistName artistName);

    /**
     * @return the albums.
     */
    List<Album> getAlbums();

    /**
     * @param album
     *            the album to add.
     */
    void addAlbum(Album album);

    /**
     * Check if album already exists.
     * 
     * @param album
     * @return if the album exists on artist.
     */
    boolean hasAlbum(Album album);

    /**
     * @param album
     *            the album to remove from this artist.
     */
    void removeAlbum(Album album);

    /**
     * @param the
     *            the the to set
     */
    void setThe(boolean the);

    /**
     * @return the the
     */
    boolean isThe();

    /**
     * Check to see if the artist has some album relations.
     * 
     * @return true if artist contains albums, otherwise false
     */
    boolean hasAlbums();
}