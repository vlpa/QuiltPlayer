package com.quiltplayer.view.swing;

import java.awt.Color;

/**
 * Dark implementation of ColorConstants.
 * 
 * @author Vlado Palczynski
 */
public class ColorConstantsDark implements ColorConstants {
    private static final long serialVersionUID = 8562550268099323144L;

    public static final Color BACKGROUND = new Color(30, 30, 30);

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.ColorConstants#getBackground()
     */
    @Override
    public Color getBackground() {
        return new Color(30, 30, 30);
    }

    /*
     * @see com.quiltplayer.view.swing.ColorConstants#getControlPanelBackground()
     */
    @Override
    public Color getControlPanelBackground() {
        return new Color(15, 15, 15);
    }

    /*
     * @see com.quiltplayer.view.swing.ColorConstants#getControlPanelGradientColors()
     */
    @Override
    public Color[] getControlPanelGradientColors() {
        Color[] colors = { new Color(50, 50, 50), new Color(40, 40, 40), new Color(10, 10, 10),
                new Color(30, 30, 30) };

        return colors;
    }

    /*
     * @see com.quiltplayer.view.swing.ColorConstants#getPlaylistPanelBackground()
     */
    @Override
    public Color getPlaylistSongBackgroundInactive() {
        return getControlPanelBackground();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.ColorConstants#getPlaylistPanelBackground()
     */
    @Override
    public Color getPlaylistPanelBackground() {
        return new Color(15, 15, 15);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.ColorConstants#getPlaylistTitle()
     */
    @Override
    public Color getPlaylistTitle() {
        return new Color(180, 180, 180);
    }

    /*
     * @see com.quiltplayer.view.swing.ColorConstants#getPlaylistCurrentSong()
     */
    @Override
    public Color getPlaylistSongBackgroundCurrent() {
        return new Color(10, 10, 10);
    }

    public static final Color ALBUM_VIEW = Color.BLACK;

    public static final Color ALBUM_PANEL = Color.BLACK;

    public static final Color ARTIST_VIEW = Color.BLACK;

    /*
     * Playlist
     */

    public static final Color PLAYLIST_PANEL_HOOVER_SONG = Color.WHITE;

    public static final Color PLAYLIST_PANEL_SONG = Color.GRAY;

    public static final Color PLAYLIST_LYRICS_COLOR = new Color(190, 190, 190);

    /*
     * Alfabetic artist view
     */
    public static final Color ARTIST_PANEL = Color.BLACK;

    public static final Color ARTISTS_PANEL_BACKGROUND = new Color(20, 20, 20);

    /*
     * @see com.quiltplayer.view.swing.ColorConstants#getArtistViewCharBackground()
     */
    @Override
    public Color getArtistViewCharBackground() {
        return new Color(50, 50, 50);
    }

    /*
     * @see com.quiltplayer.view.swing.ColorConstants#getArtistViewCharColor()
     */
    @Override
    public Color getArtistViewCharColor() {
        return new Color(220, 220, 220);
    }

    /*
     * @see com.quiltplayer.view.swing.ColorConstants#getArtistViewTextHighlightColor ()
     */
    @Override
    public Color getArtistViewTextHighlightColor() {
        return Color.WHITE;
    }

    /*
     * @see com.quiltplayer.view.swing.ColorConstants#getArtistViewTextColor()
     */
    @Override
    public Color getArtistViewTextColor() {
        return new Color(220, 220, 220);
    }

    /*
     * @see com.quiltplayer.view.swing.ColorConstants#getAlbumViewTitleColor()
     */
    @Override
    public Color getAlbumViewTitleColor() {
        return new Color(170, 170, 170);
    }

    /*
     * @see com.quiltplayer.view.swing.ColorConstants#getArtistViewYearColor()
     */
    @Override
    public Color getAlbumViewYearColor() {
        return new Color(150, 150, 150);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.ColorConstants#getQLabelForegroundColor()
     */
    @Override
    public Color getQLabelForegroundColor() {
        return new Color(135, 135, 135);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.ColorConstants#getSearchBackground()
     */
    @Override
    public Color getSearchBackground() {
        return new Color(50, 50, 50);
    }

}
