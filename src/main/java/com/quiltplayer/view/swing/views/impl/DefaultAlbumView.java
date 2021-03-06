package com.quiltplayer.view.swing.views.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.jxlayer.JXLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.quiltplayer.controller.ArtistController;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.buttons.ImageButton;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.buttons.SquaredAlbumButton;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.listeners.ArtistListener;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.panels.QScrollPane.ScrollDirection;
import com.quiltplayer.view.swing.views.ListView;

/**
 * My implementation of the album view.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component
public class DefaultAlbumView implements ListView<Album> {

    @Autowired
    private ChangeAlbumListener changeAlbumListener;

    @Autowired
    private ArtistListener artistListener;

    private List<Album> albums;

    private Artist artist;

    private JLabel artistnameLabel;

    private boolean initialized = false;

    private int width;

    @Autowired
    private ThreadPoolTaskExecutor imageTaskExecutor;

    /*
     * @see org.coverok.gui.components.AlbumView#getUI()
     */
    @Override
    public JComponent getUI() {

        JPanel panel = new JPanel(new MigLayout("ins 1cm 2cm 0cm 2cm, fillx, align center, wrap "
                + Configuration.getInstance().getGridProperties().getAlbumsGrid())) {
            /*
             * (non-Javadoc)
             * 
             * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
             */
            @Override
            protected void paintComponent(Graphics g) {
                if (width != getWidth()) {
                    width = getWidth();

                    // createQuiltOfAlbums();
                }
                else if (!initialized && albums != null && albums.size() > 0) {
                    setup();

                    initialized = true;
                }

                super.paintComponent(g);
            }

            private void setup() {
                if (albums != null && !albums.isEmpty()) {
                    if (artistnameLabel == null)
                        setupArtistnameLabel();

                    artistnameLabel.setText(albums.get(0).getArtist().getArtistName().getName());
                    add(artistnameLabel, "span " + Configuration.getInstance().getGridProperties().getAlbumsGrid());

                    int i = 1;

                    for (final Album album : albums) {
                        SquaredAlbumButton p = null;

                        p = new SquaredAlbumButton(album, changeAlbumListener, imageTaskExecutor);

                        add(p, "grow, shrink 0, gap 0.3cm 0.3cm 0.3cm 0.3cm");

                        if (i == Configuration.getInstance().getGridProperties().getAlbumsGrid())
                            i = 1;
                        else
                            i++;
                    }

                    /* Fill with empty squares to remain size */
                    while (i < Configuration.getInstance().getGridProperties().getAlbumsGrid()) {
                        ImageButton p = new ImageButton(null, null, imageTaskExecutor);
                        p.setVisible(true);

                        add(p, "grow, shrink 0, gap 0.3cm 0.3cm 0.3cm 0.3cm");

                        i++;
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

                    add(deleteArtistButton);

                }
            }
        };

        /* Otherwise gray */
        panel.setOpaque(true);

        return new JXLayer<JScrollPane>(new QScrollPane(panel, ScrollDirection.VERTICAL), new JScrollPaneLayerUI());

    }

    private void setupArtistnameLabel() {
        artistnameLabel = new JLabel();
        artistnameLabel.setFont(FontFactory.getLargeTextFont(16f));
        artistnameLabel.setForeground(new Color(180, 179, 178));
    }

    /*
     * @see com.quiltplayer.view.swing.components.ListView#setCollection(java.util .Collection)
     */
    @Override
    public void setList(final List<Album> list) {
        albums = list;

        System.out.println(list);

        initialized = false;
    }

    @Deprecated
    public Album getSelectedAlbum() {
        return null;
    }

    /**
     * @param artist
     *            the artist to set
     */
    public final void setArtist(Artist artist) {
        this.artist = artist;
    }

}
