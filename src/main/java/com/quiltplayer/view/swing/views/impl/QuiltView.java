package com.quiltplayer.view.swing.views.impl;

import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.jxlayer.JXLayer;
import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.buttons.AlbumCoverButton;
import com.quiltplayer.view.swing.interfaces.FrameResizeAwarable;
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
public class QuiltView implements ListView<Album>, FrameResizeAwarable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ChangeAlbumListener changeAlbumListener;

    private Album selectedAlbum = null;

    private JPanel panel;

    private List<Album> albums = Collections.EMPTY_LIST;

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
    public JComponent getUI() {
        panel = new JPanel(new MigLayout("ins 0 0.3cm 0 0.3cm, wrap "
                + Configuration.getInstance().getGridProperties().getQuiltGrid()
                + ", fillx, align center"));
        panel.setOpaque(true);

        int i = 1;

        for (Album album : albums) {
            if (album.getFrontImage() != null) {
                AlbumCoverButton p = new AlbumCoverButton(album, changeAlbumListener);
                panel.add(p, "grow, shrink 0");

                if (i == Configuration.getInstance().getGridProperties().getQuiltGrid())
                    i = 1;
                else
                    i++;
            }
        }

        /* Fill with empty squares to remain size */
        while (i < Configuration.getInstance().getGridProperties().getQuiltGrid()) {
            AlbumCoverButton p = new AlbumCoverButton(null, null);
            p.setVisible(true);
            panel.add(p, "grow, shrink 0");

            i++;
        }

        final QScrollPane pane = new QScrollPane(panel);

        return new JXLayer<JScrollPane>(pane, new JScrollPaneLayerUI());
    }

    public Album getSelectedAlbum() {
        return selectedAlbum;
    }
}
