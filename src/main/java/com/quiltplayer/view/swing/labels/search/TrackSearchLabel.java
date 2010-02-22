package com.quiltplayer.view.swing.labels.search;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.model.Song;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.borders.ScrollableAndHighlightableSearchResultButton;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.listeners.HighlightableMouseListener;

/**
 * Represents a track in search list.
 * 
 * @author Vlado Palczynski
 */
public class TrackSearchLabel extends ScrollableAndHighlightableSearchResultButton {

    private static final long serialVersionUID = 1L;

    protected ChangeAlbumListener changeAlbumListener;

    private Song song;

    public TrackSearchLabel(final Song song) {
        super();

        this.song = song;

        JLabel artistLabel = new JLabel(song.getAlbum().getArtist().getArtistName().getName());
        artistLabel.setFont(FontFactory.getFont(11f));
        artistLabel.setForeground(Color.gray);
        artistLabel.setOpaque(false);

        JLabel albumLabel = new JLabel(song.getAlbum().getTitle());
        albumLabel.setFont(FontFactory.getFont(11f));
        albumLabel.setForeground(Color.gray);
        albumLabel.setOpaque(false);

        JLabel songLabel = new JLabel(song.getTitle());
        songLabel.setFont(FontFactory.getFont(14f));
        songLabel.setForeground(Configuration.getInstance().getColorConstants()
                .getArtistViewTextColor());
        songLabel.setOpaque(false);

        add(artistLabel, "");
        add(albumLabel, "");
        add(songLabel, "");

        addMouseListener(new HighlightableMouseListener(background, this));
    }

    public void triggerAction() {
        Object[] objects = new Object[2];
        objects[0] = song.getAlbum();
        objects[1] = song;

        changeAlbumListener.actionPerformed(new ActionEvent(objects, 0,
                ChangeAlbumController.EVENT_CHANGE_ALBUM_AND_PLAY_SONG));

    }

    public void addActionListener(ChangeAlbumListener listener) {
        this.changeAlbumListener = listener;
    }
}
