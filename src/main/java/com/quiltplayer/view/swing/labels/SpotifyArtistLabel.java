package com.quiltplayer.view.swing.labels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;

import com.quiltplayer.model.Artist;

/**
 * Represents a artist in lists.
 * 
 * @author Vlado Palczynski
 */
public class SpotifyArtistLabel extends ArtistLabel
{
	private static final long serialVersionUID = 1L;

	public SpotifyArtistLabel(Artist artist)
	{
		super(artist);
	}

	public SpotifyArtistLabel(Artist artist, Image portrait)
	{
		super(artist);

		setIcon(new ImageIcon(portrait));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		super.paint(g);
	}
}
