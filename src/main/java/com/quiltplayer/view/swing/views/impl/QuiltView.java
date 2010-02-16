package com.quiltplayer.view.swing.views.impl;

import java.awt.Component;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.cmc.shared.swing.FlowWrapLayout;
import org.jdesktop.jxlayer.JXLayer;
import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.core.storage.Storage;
import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.buttons.AlbumCoverButton;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.views.ListView;

/**
 * Implementation of the quilt album view.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component
public class QuiltView implements ListView<Album> {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ChangeAlbumListener changeAlbumListener;

    @Autowired
    private ArtistStorage artistStorage;

    @Autowired
    private Storage storage;

    private Album selectedAlbum = null;

    private JPanel panel;

    private List<Album> albums;

    @PostConstruct
    public void init() {
        albums = storage.getAlbums(artistStorage.getArtists());
    }

    /*
     * @see com.quiltplayer.view.swing.components.ListView#setCollection(java.util .Collection)
     */
    @Override
    public void setList(List<Album> list) {
        albums = list;
    }

    /*
     * @see org.coverok.gui.components.AlbumView#getUI()
     */
    @Override
    public Component getUI() {
        panel = new JPanel(new FlowWrapLayout(10, 10, 10, 10));
        panel.setOpaque(true);

        Collections.sort(albums);

        for (Album album : albums) {
            if (album.getFrontImage() != null) {
                AlbumCoverButton p = new AlbumCoverButton(album, changeAlbumListener);
                panel.add(p, "h " + ImageSizes.SMALL.getSize() + "px!");
            }
        }

        final QScrollPane pane = new QScrollPane(panel);

        final JXLayer<JScrollPane> jx = new JXLayer<JScrollPane>(pane, new JScrollPaneLayerUI());

        return jx;
    }

    public Album getSelectedAlbum() {
        return selectedAlbum;
    }
}
