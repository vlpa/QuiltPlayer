package com.quiltplayer.view.swing.labels.search;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.buttons.ScrollableAndHighlightableButton;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;

/**
 * Represents a artist in the search list.
 * 
 * @author Vlado Palczynski
 */
public class AlbumSearchLabel extends ScrollableAndHighlightableButton {

    private static final long serialVersionUID = 1L;

    protected ChangeAlbumListener listener;

    private Album album;

    public AlbumSearchLabel(final Album album) {
        super();

        setLayout(new MigLayout(""));

        this.album = album;

        JLabel artistLabel = new JLabel(album.getArtist().getArtistName().getName());
        artistLabel.setFont(FontFactory.getFont(12f));
        artistLabel.setForeground(Color.gray);
        artistLabel.setOpaque(false);

        JLabel albumLabel = new JLabel(album.getTitle());
        albumLabel.setFont(FontFactory.getFont(14f));
        albumLabel.setForeground(Configuration.getInstance().getColorConstants()
                .getArtistViewTextColor());
        albumLabel.setOpaque(false);

        add(artistLabel, "north, gapx 0.05cm 0.05cm");
        add(albumLabel, "north, gapx 0.05cm 0.05cm");
    }

    public void triggerAction() {
        listener
                .actionPerformed(new ActionEvent(album, 0, ChangeAlbumController.EVENT_CHANGE_ALBUM));

    }

    public void addActionListener(ChangeAlbumListener listener) {
        this.listener = listener;
    }
}