package com.quiltplayer.view.swing.labels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;

import com.quiltplayer.model.Artist;
import com.quiltplayer.view.swing.listeners.ArtistListener;

/**
 * Represents a artist in lists.
 * 
 * @author Vlado Palczynski
 */
public class SpotifyArtistLabel extends ArtistNameButton {
    private static final long serialVersionUID = 1L;

    public SpotifyArtistLabel(final Artist artist, final ArtistListener artistListener) {
        super(artist, artistListener);
    }

    public SpotifyArtistLabel(Artist artist, final ArtistListener artistListener, Image portrait) {
        super(artist, artistListener);

        setIcon(new ImageIcon(portrait));
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paint(g);
    }
}
