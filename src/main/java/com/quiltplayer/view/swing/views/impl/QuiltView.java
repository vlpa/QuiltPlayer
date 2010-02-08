package com.quiltplayer.view.swing.views.impl;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JPanel;

import org.cmc.shared.swing.FlowWrapLayout;
import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.core.storage.Storage;
import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.panels.AlbumLabel;
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

    private JPanel glassPane;

    /*
     * @see org.coverok.gui.components.AlbumView#close()
     */
    @Override
    public void close() {
        panel = null;

        glassPane = null;
    }

    /*
     * @see com.quiltplayer.view.swing.components.ListView#setCollection(java.util .Collection)
     */
    @Override
    public void setList(List<Album> list) {
    }

    /*
     * @see org.coverok.gui.components.AlbumView#getUI()
     */
    @Override
    public Component getUI() {
        panel = new JPanel(new FlowWrapLayout());

        final QScrollPane pane = new QScrollPane(panel);

        MouseListener l = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (panel.contains(e.getX(), e.getY())) {
                    AlbumLabel albumPane = (AlbumLabel) e.getSource();

                    selectedAlbum = albumPane.getAlbum();

                    changeAlbumListener.actionPerformed(new ActionEvent(selectedAlbum, 0,
                            ChangeAlbumController.EVENT_CHANGE_ALBUM));
                }
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
             */
            @Override
            public void mousePressed(MouseEvent e) {
                pane.mousePressed(e);
            }
        };

        MouseMotionListener ml = new MouseMotionAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseMotionAdapter#mouseDragged(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                pane.mouseDragged(e);
            }
        };

        for (Album album : storage.getAlbums(artistStorage.getArtists())) {
            if (album.getFrontImage() != null) {
                AlbumLabel p = new AlbumLabel(album);
                p.addMouseListener(l);
                p.addMouseMotionListener(ml);
                p.setAutoscrolls(true);

                panel.add(p, "h " + ImageSizes.SMALL.getSize() + "px!");
            }
        }

        return pane;
    }

    public Album getSelectedAlbum() {
        return selectedAlbum;
    }

    public void setGlassPane(final JPanel glassPane) {
        this.glassPane = glassPane;
    }
}
