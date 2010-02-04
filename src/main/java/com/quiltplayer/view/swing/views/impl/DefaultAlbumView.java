package com.quiltplayer.view.swing.views.impl;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import net.miginfocom.swing.MigLayout;

import org.cmc.shared.swing.FlowWrapLayout;
import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.controller.ArtistController;
import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.core.repo.spotify.JotifyRepository;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.model.jotify.JotifyAlbum;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.listeners.ArtistListener;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.panels.AlbumView;
import com.quiltplayer.view.swing.panels.SpotifySquaredAlbumPanel;
import com.quiltplayer.view.swing.panels.SquaredAlbumPanel;
import com.quiltplayer.view.swing.views.AbstractView;
import com.quiltplayer.view.swing.views.ListView;

/**
 * My implementation of the album view.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component
public class DefaultAlbumView extends AbstractView implements Serializable, ListView<Album> {

    private static final long serialVersionUID = 1L;

    private static final int VERTICAL_UNIT_INCRENET = 110;

    @Autowired
    private JotifyRepository jotifyRepository;

    @Autowired
    private transient ChangeAlbumListener changeAlbumListener;

    @Autowired
    private transient ArtistListener artistListener;

    private Album selectedAlbum = null;

    private List<Album> albums;

    private JPanel panel;

    private Artist artist;

    /*
     * @see org.coverok.gui.components.AlbumView#close()
     */
    @Override
    public void close() {
    }

    /*
     * @see org.coverok.gui.components.AlbumView#getUI()
     */
    @Override
    public Component getUI() {

        panel = new JPanel(new FlowWrapLayout(30, 30, 30, 30));
        panel.setOpaque(true);

        MouseListener l = new MouseInputAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (panel.contains(e.getX(), e.getY())) {
                    AlbumView albumPane = (AlbumView) e.getSource();

                    selectedAlbum = albumPane.getAlbum();

                    changeAlbumListener.actionPerformed(new ActionEvent(selectedAlbum, 0,
                            ChangeAlbumController.EVENT_CHANGE_ALBUM));
                }
            }
        };

        if (albums != null && !albums.isEmpty()) {
            for (final Album album : albums) {
                SquaredAlbumPanel p = null;

                if (album instanceof JotifyAlbum) {
                    if (!((JotifyAlbum) album).isPlayable())
                        continue;

                    p = new SpotifySquaredAlbumPanel(album, jotifyRepository);
                }
                else
                    p = new SquaredAlbumPanel(album);

                p.addMouseListener(l);

                panel.add(p, "aligny top, gapy 25");
            }
        }
        else {
            final JButton deleteArtistButton = new QButton("Delete artist");
            deleteArtistButton.addMouseListener(new MouseAdapter() {
                /*
                 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event. MouseEvent)
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    artistListener.actionPerformed(new ActionEvent(artist, 0,
                            ArtistController.EVENT_DELETE_ARTIST));
                }

            });

            panel.add(deleteArtistButton);

        }

        return getScrollPane(panel, VERTICAL_UNIT_INCRENET);
    }

    /*
     * @see com.quiltplayer.view.swing.components.ListView#setCollection(java.util .Collection)
     */
    @Override
    public void setList(final List<Album> list) {
        albums = list;
    }

    public Album getSelectedAlbum() {
        return selectedAlbum;
    }

    /**
     * @param artist
     *            the artist to set
     */
    public final void setArtist(Artist artist) {
        this.artist = artist;
    }
}
