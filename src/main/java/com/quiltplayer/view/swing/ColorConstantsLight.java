package com.quiltplayer.view.swing;

import java.awt.Color;

/**
 * Faster to test other color combination.
 * 
 * @author Vlado Palczynski
 */
public class ColorConstantsLight implements ColorConstants
{
	/**
     * 
     */
	private static final long serialVersionUID = 5700970503463696933L;

	/*
	 * @see com.quiltplayer.view.swing.ColorConstants#getBackground()
	 */
	@Override
	public Color getBackground()
	{
		return new Color(248, 248, 248);
	}

	/*
	 * @see
	 * com.quiltplayer.view.swing.ColorConstants#getControlPanelBackground()
	 */
	@Override
	public Color getControlPanelBackground()
	{
		return getPlaylistSongBackgroundInactive();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.quiltplayer.view.swing.ColorConstants#getPlaylistPanelBackground()
	 */
	@Override
	public Color getPlaylistPanelBackground()
	{
		return Color.white;
	}

	/*
	 * @see
	 * com.quiltplayer.view.swing.ColorConstants#getControlPanelGradientColors()
	 */
	@Override
	public Color[] getControlPanelGradientColors()
	{
		Color[] colors = { new Color(80, 80, 80), new Color(60, 60, 60),
				new Color(30, 30, 30), new Color(10, 10, 10) };

		return colors;
	}

	/*
	 * @see
	 * com.quiltplayer.view.swing.ColorConstants#getPlaylistPanelBackground()
	 */
	@Override
	public Color getPlaylistSongBackgroundInactive()
	{
		return Color.BLACK;
	}

	/*
	 * @see com.quiltplayer.view.swing.ColorConstants#getPlaylistCurrentSong()
	 */
	@Override
	public Color getPlaylistSongBackgroundCurrent()
	{
		// TODO Auto-generated method stub
		return new Color(35, 35, 35);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.quiltplayer.view.swing.ColorConstants#getPlaylistTitle()
	 */
	@Override
	public Color getPlaylistTitle()
	{
		return new Color(100, 100, 100);
	}

	public static final Color PLAYLIST_PANEL = Color.WHITE;

	public static final Color ALBUM_VIEW = Color.WHITE;

	public static final Color ALBUM_PANEL = Color.WHITE;

	public static final Color ARTIST_VIEW = Color.WHITE;

	public static final Color PLAYLIST_PANEL_PLAYING_SONG = Color.LIGHT_GRAY;

	public static final Color PLAYLIST_PANEL_HOOVER_SONG = Color.BLACK;

	public static final Color PLAYLIST_PANEL_SONG = Color.GRAY;

	public static final Color PLAYLIST_LYRICS_COLOR = new Color(220, 220, 220);

	/*
	 * Alfabetic artist view
	 */
	public static final Color ARTIST_PANEL = Color.WHITE;

	public static final Color ARTISTS_PANEL_BACKGROUND = Color.BLACK;

	/*
	 * @see
	 * com.quiltplayer.view.swing.ColorConstants#getArtistViewCharBackground()
	 */
	@Override
	public Color getArtistViewCharBackground()
	{
		return Color.BLACK;
	}

	/*
	 * @see com.quiltplayer.view.swing.ColorConstants#getArtistViewCharColor()
	 */
	@Override
	public Color getArtistViewCharColor()
	{
		return Color.WHITE;
	}

	/*
	 * @see com.quiltplayer.view.swing.ColorConstants#getArtistViewTextColor()
	 */
	@Override
	public Color getArtistViewTextColor()
	{
		return new Color(80, 80, 80);
	}

	/*
	 * @see
	 * com.quiltplayer.view.swing.ColorConstants#getArtistViewTextHighlightColor
	 * ()
	 */
	@Override
	public Color getArtistViewTextHighlightColor()
	{
		return Color.BLACK;
	}

	/*
	 * @see com.quiltplayer.view.swing.ColorConstants#getAlbumViewTitleColor()
	 */
	@Override
	public Color getAlbumViewTitleColor()
	{
		return new Color(10, 10, 10);
	}

	/*
	 * @see com.quiltplayer.view.swing.ColorConstants#getArtistViewYearColor()
	 */
	@Override
	public Color getAlbumViewYearColor()
	{
		return new Color(120, 120, 120);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.quiltplayer.view.swing.ColorConstants#getQLabelForegroundColor()
	 */
	@Override
	public Color getQLabelForegroundColor()
	{
		return new Color(135, 135, 135);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.quiltplayer.view.swing.ColorConstants#getSearchBackground()
	 */
	@Override
	public Color getSearchBackground()
	{
		return new Color(230, 230, 230);
	}

}
