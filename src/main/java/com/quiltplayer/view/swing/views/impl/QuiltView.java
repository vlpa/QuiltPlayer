package com.quiltplayer.view.swing.views.impl;

import java.awt.Graphics;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.jxlayer.JXLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.buttons.ImageButton;
import com.quiltplayer.view.swing.interfaces.FrameResizeAwarable;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.panels.QScrollPane.ScrollDirection;
import com.quiltplayer.view.swing.util.MigProperties;
import com.quiltplayer.view.swing.util.ScreenUtils;
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

    private JPanel panel;

    private List<Album> albums = Collections.emptyList();

    private boolean initialized = false;

    private int width;

    @Autowired
    private ThreadPoolTaskExecutor imageTaskExecutor;

    @Autowired
    private ScreenUtils screenUtils;

    /*
     * @see com.quiltplayer.view.swing.components.ListView#setCollection(java.util .Collection)
     */
    @Override
    public void setList(List<Album> list) {
        albums = list;
        initialized = false;
    }

    /*
     * @see org.coverok.gui.components.AlbumView#getUI()
     */
    @Override
    public JComponent getUI() {
        panel = new JPanel(new MigLayout("ins 0 " + MigProperties.CONTROL_PANEL_WIDTH + "cm 0 "
                + MigProperties.CONTROL_PANEL_WIDTH + "cm , fillx, align center, wrap "
                + Configuration.getInstance().getGridProperties().getQuiltGrid())) {

            private static final long serialVersionUID = 1L;

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
                    createQuiltOfAlbums();
                }

                super.paintComponent(g);
            }

            private void createQuiltOfAlbums() {
                removeAll();

                int size = (getWidth() - 100) / Configuration.getInstance().getGridProperties().getQuiltGrid();

                int i = 1;

                for (Album album : albums) {
                    if (album.getFrontImage() != null) {
                        ImageButton p = new ImageButton(album, changeAlbumListener, imageTaskExecutor);
                        panel.add(p, "w " + size + "px, h " + size + "px");

                        /* Keep track where you are on a row for fill with empty albums below */
                        if (i == Configuration.getInstance().getGridProperties().getQuiltGrid())
                            i = 1;
                        else
                            i++;
                    }
                }

                initialized = true;
            }
        };

        panel.setOpaque(true);

        /* Fill with empty squares to remain size */
        // while (i < Configuration.getInstance().getGridProperties().getQuiltGrid()) {
        // AlbumCoverButton p = new AlbumCoverButton(null, null);
        // p.setVisible(true);
        // panel.add(p, "grow, shrink 0");
        //
        // i++;
        // }

        final QScrollPane pane = new QScrollPane(panel, ScrollDirection.VERTICAL);

        return new JXLayer<JScrollPane>(pane, new JScrollPaneLayerUI());
    }
}
