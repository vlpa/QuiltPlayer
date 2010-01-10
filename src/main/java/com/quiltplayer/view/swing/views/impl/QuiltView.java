package com.quiltplayer.view.swing.views.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.core.storage.Storage;
import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.panels.AlbumLabel;
import com.quiltplayer.view.swing.views.AbstractView;
import com.quiltplayer.view.swing.views.ListView;

/**
 * Implementation of the quilt album view.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component
public class QuiltView extends AbstractView implements ListView<Album> {

    private static final long serialVersionUID = 1L;

    private static final int VERTICAL_UNIT_INCRENET = ImageSizes.SMALL.getSize() + 4;

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
        panel = new JPanel(new MigLayout("insets 0, alignx center, aligny center,  wrap "
                + Configuration.getInstance().getQuiltColumns())) {

            private static final long serialVersionUID = -8314219595057537932L;

            /*
             * (non-Javadoc)
             * 
             * @see javax.swing.JComponent#paint(java.awt.Graphics)
             */
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                Color color1 = getBackground();
                Color color2 = Color.white;

                // Paint a gradient from top to bottom
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);

                super.paint(g);
            }
        };

        MouseListener l = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (panel.contains(e.getX(), e.getY())) {
                    AlbumLabel albumPane = (AlbumLabel) e.getSource();

                    selectedAlbum = albumPane.getAlbum();

                    changeAlbumListener.actionPerformed(new ActionEvent(selectedAlbum, 0,
                            ChangeAlbumController.EVENT_CHANGE_ALBUM));
                }
            }
        };

        for (Album album : storage.getAlbums(artistStorage.getArtists())) {
            if (album.getFrontImage() != null) {
                AlbumLabel p = new AlbumLabel(album, glassPane);
                p.addMouseListener(l);

                panel.add(p, "h " + ImageSizes.SMALL.getSize() + "px!");
            }
        }

        return getScrollPane(panel, VERTICAL_UNIT_INCRENET);
    }

    public Album getSelectedAlbum() {
        return selectedAlbum;
    }

    public void setGlassPane(final JPanel glassPane) {
        this.glassPane = glassPane;
    }
}
