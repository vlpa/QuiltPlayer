package com.quiltplayer.view.swing.labels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.model.Song;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.panels.HighlightableQPanel;

/**
 * Represents a track in search list.
 * 
 * @author Vlado Palczynski
 */
public class TrackSearchLabel extends HighlightableQPanel {

    private static final long serialVersionUID = 1L;

    protected ChangeAlbumListener listener;

    public TrackSearchLabel(final Song song) {
        super();

        JLabel artistLabel = new JLabel(song.getAlbum().getArtist().getArtistName().getName());
        artistLabel.setFont(FontFactory.getFont(12f));
        artistLabel.setBackground(Configuration.getInstance().getColorConstants().getBackground());
        artistLabel.setForeground(Color.gray);
        artistLabel.setMaximumSize(new Dimension((int) getMaximumSize().getWidth(), 12));

        JLabel albumLabel = new JLabel(song.getAlbum().getTitle());
        albumLabel.setFont(FontFactory.getFont(12f));
        albumLabel.setBackground(Configuration.getInstance().getColorConstants().getBackground());
        albumLabel.setForeground(Color.gray);
        albumLabel.setMaximumSize(new Dimension((int) getMaximumSize().getWidth(), 12));

        JLabel songLabel = new JLabel(song.getTitle());
        songLabel.setFont(FontFactory.getFont(14f));
        songLabel.setBackground(Configuration.getInstance().getColorConstants().getBackground());
        songLabel.setForeground(Configuration.getInstance().getColorConstants()
                .getArtistViewTextColor());

        add(artistLabel);
        add(albumLabel);
        add(songLabel);

        addMouseListener(new MouseAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                Object[] objects = new Object[2];
                objects[0] = song.getAlbum();
                objects[1] = song;

                listener.actionPerformed(new ActionEvent(objects, 0,
                        ChangeAlbumController.EVENT_CHANGE_ALBUM_AND_PLAY_SONG));
            }
        });
    }

    public void addActionListener(ChangeAlbumListener listener) {
        this.listener = listener;
    }
}
