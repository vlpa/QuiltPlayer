package com.quiltplayer.view.swing.labels;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.controller.ArtistController;
import com.quiltplayer.model.Artist;
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

        setLayout(new MigLayout("fill, alignx left"));

        setBorder(BorderFactory.createEmptyBorder());

        final JLabel nameLabel = new JLabel(artist.getArtistName().getName());

        nameLabel.setForeground(new Color(220, 220, 200));

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
        artistListener.actionPerformed(new ActionEvent(artist, 0,
                ArtistController.ACTION_GET_ARTIST_ALBUMS));
    }
}
