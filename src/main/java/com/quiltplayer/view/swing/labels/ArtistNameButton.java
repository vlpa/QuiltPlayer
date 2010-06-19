package com.quiltplayer.view.swing.labels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.controller.ArtistController;
import com.quiltplayer.model.Artist;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.buttons.ScrollableAndHighlightableButton;
import com.quiltplayer.view.swing.listeners.ArtistListener;
import com.quiltplayer.view.swing.listeners.HighlightableMouseListener;

/**
 * Represents a artist in lists.
 * 
 * @author Vlado Palczynski
 */
public class ArtistNameButton extends ScrollableAndHighlightableButton {

    private static final long serialVersionUID = 1L;

    protected Artist artist;

    private ArtistListener artistListener;

    public ArtistNameButton(final Artist artist, final ArtistListener artistListener) {
        super();

        this.artist = artist;
        this.artistListener = artistListener;

        setOpaque(false);

        setBackground(Color.PINK);

        setLayout(new MigLayout("fill, alignx left"));

        setBorder(BorderFactory.createEmptyBorder());

        final JLabel nameLabel = new JLabel(artist.getArtistName().getName());

        nameLabel.setForeground(new Color(220, 220, 200));

        nameLabel.setFont(FontFactory.getFont(14f));

        addMouseListener(new HighlightableMouseListener(background, this));

        add(nameLabel, "");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.buttons.ScrollableButton#triggerAction()
     */
    @Override
    protected void triggerAction() {
        artistListener.actionPerformed(new ActionEvent(artist, 0, ArtistController.ACTION_GET_ARTIST_ALBUMS));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.quiltplayer.view.swing.buttons.ScrollableAndHighlightableButton#paintComponent(java.awt
     * .Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {

        setMaximumSize(new Dimension(getWidth(), getHeight()));

        super.paintComponent(g);
    }

}
