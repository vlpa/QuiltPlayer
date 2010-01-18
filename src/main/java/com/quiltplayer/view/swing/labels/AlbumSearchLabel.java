package com.quiltplayer.view.swing.labels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.panels.HighlightableQPanel;

/**
 * Represents a artist in the search list.
 * 
 * @author Vlado Palczynski
 */
public class AlbumSearchLabel extends HighlightableQPanel {

    private static final long serialVersionUID = 1L;

    protected ChangeAlbumListener listener;

    public AlbumSearchLabel(final Album album) {

        super();

        JLabel artistLabel = new JLabel(album.getArtist().getArtistName().getName());
        artistLabel.setFont(FontFactory.getFont(12f));
        artistLabel.setBackground(Configuration.getInstance().getColorConstants().getBackground());
        artistLabel.setForeground(Color.gray);
        artistLabel.setMaximumSize(new Dimension((int) getMaximumSize().getWidth(), 12));

        JLabel albumLabel = new JLabel(album.getTitle());
        albumLabel.setFont(FontFactory.getFont(14f));
        albumLabel.setBackground(Configuration.getInstance().getColorConstants().getBackground());
        albumLabel.setForeground(Configuration.getInstance().getColorConstants()
                .getArtistViewTextColor());

        add(artistLabel);
        add(albumLabel);

        addMouseListener(new MouseAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                listener.actionPerformed(new ActionEvent(album, 0,
                        ChangeAlbumController.EVENT_CHANGE_ALBUM));
            }
        });
    }

    public void addActionListener(ChangeAlbumListener listener) {
        this.listener = listener;
    }
}
