package com.quiltplayer.view.swing.views.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.cmc.shared.swing.FlowWrapLayout;
import org.jdesktop.jxlayer.JXLayer;
import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.controller.ArtistController;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.model.jotify.JotifyAlbum;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.buttons.SpotifySquaredAlbumButton;
import com.quiltplayer.view.swing.buttons.SquaredAlbumButton;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.listeners.ArtistListener;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.views.ListView;

/**
 * My implementation of the album view.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component
public class DefaultAlbumView implements Serializable, ListView<Album> {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ChangeAlbumListener changeAlbumListener;

    @Autowired
    private ArtistListener artistListener;

    private Album selectedAlbum = null;

    private List<Album> albums;

    private JPanel panel;

    private Artist artist;

    /*
     * @see org.coverok.gui.components.AlbumView#getUI()
     */
    @Override
    public JComponent getUI() {

        panel = new JPanel(new FlowWrapLayout(0, 0, 0, 0));
        panel.setOpaque(true);

        if (albums != null && !albums.isEmpty()) {
            for (final Album album : albums) {
                SquaredAlbumButton p = null;

                if (album instanceof JotifyAlbum) {
                    if (!((JotifyAlbum) album).isPlayable())
                        continue;

                    p = new SpotifySquaredAlbumButton(album, changeAlbumListener);
                }
                else
                    p = new SquaredAlbumButton(album, changeAlbumListener);

                panel.add(p, "aligny top, gapy 0.3cm, gapx 0.3cm");
            }
        }
        else {
            final JButton deleteArtistButton = new QButton("Delete artist");
            deleteArtistButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    artistListener.actionPerformed(new ActionEvent(artist, 0,
                            ArtistController.EVENT_DELETE_ARTIST));
                }
            });

            panel.add(deleteArtistButton);

        }

        return new JXLayer<JScrollPane>(new QScrollPane(panel), new JScrollPaneLayerUI());
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
