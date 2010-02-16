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
package com.quiltplayer.view.swing;

import java.awt.Color;
import java.io.Serializable;

/**
 * Definition of the ColorConstant methods.
 * 
 * @author Vlado Palczynski
 */
public interface ColorConstants extends Serializable {
    /**
     * Returns the background color for the application.
     * 
     * @return
     */
    Color getBackground();

    Color getSearchBackground();

    Color getPlaylistPanelBackground();

    Color getPlaylistSongBackgroundInactive();

    Color getPlaylistSongBackgroundCurrent();

    /*
     * Playlist
     */

    Color getPlaylistTitle();

    public static final Color PLAYLIST_LYRICS_COLOR = new Color(220, 220, 220);

    /*
     * Alfabetic artist view
     */
    public static final Color ARTIST_PANEL = Color.BLACK;

    public static final Color ARTISTS_PANEL_BACKGROUND = Color.BLACK;

    Color getArtistViewCharBackground();

    Color getArtistViewCharColor();

    Color getArtistViewTextColor();

    Color getArtistViewTextHighlightColor();

    Color getAlbumViewTitleColor();

    Color getAlbumViewYearColor();

    Color getControlPanelBackground();

    Color[] getControlPanelGradientColors();

    Color getQLabelForegroundColor();
}
