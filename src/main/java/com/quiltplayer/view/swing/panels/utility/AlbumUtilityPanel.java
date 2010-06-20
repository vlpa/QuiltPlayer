package com.quiltplayer.view.swing.panels.utility;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;
import org.jdesktop.jxlayer.JXLayer;
import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.impl.NullAlbum;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.buttons.QSongButton;
import com.quiltplayer.view.swing.effects.CrossFader;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.panels.AlbumPresentationPanel;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.panels.QScrollPane.ScrollDirection;
import com.quiltplayer.view.swing.panels.components.SongsComponent;
import com.quiltplayer.view.swing.util.MigProperties;

/**
 * Represents the playlist panel. One Panel will give you information about the album, tracks and so
 * forth. The other will give you information regarding the artist.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component
public class AlbumUtilityPanel extends JPanel {

    private Logger log = Logger.getLogger(AlbumUtilityPanel.class);

    private static final long serialVersionUID = 1L;

    private QSongButton currentSongLabel;

    private SongsComponent songsComponent;

    private Component component;

    @Autowired
    private AlbumPresentationPanel albumPresentationPanel;

    @Autowired
    private PlayerListener playerListener;

    private transient Album album;

    @Autowired
    private CrossFader crossFader;

    public AlbumUtilityPanel() {
        super(new MigLayout("ins 0, fill, wrap 1, fillx, w " + MigProperties.PLAYLIST_PANEL_WIDTH + "cm!"));

        setBackground(ColorConstantsDark.PLAYLIST_BACKGROUND);

        setOpaque(true);
    }

    @PostConstruct
    public void init() {
        this.album = new NullAlbum();

        add(albumPresentationPanel, "north, gapy 0.5cm 0.3cm, gapx 0.2cm 0.2cm");
        add(crossFader, "north");

    }

    public void changeAlbum(final Album album) {
        log.debug("Changing album...");
        this.album = album;

        crossFader.setImages(album.getImages());

        albumPresentationPanel.update(album);

        setupSongsPanel();

        log.debug("Album is changed...");
    }

    private void setupSongsPanel() {
        if (component != null) {
            remove(component);
            remove(crossFader);
            remove(songsComponent);
        }

        songsComponent = new SongsComponent(album, playerListener);
        component = new JXLayer<JScrollPane>(new QScrollPane(songsComponent, ScrollDirection.VERTICAL),
                new JScrollPaneLayerUI());

        if (album.getImages().size() > 0)
            add(crossFader, "north, h " + MigProperties.PLAYLIST_PANEL_WIDTH + "cm!");

        add(component, "north, gapy 0.2cm");

        component.repaint();
    }

    /*
     * @see javax.swing.JPanel#updateUI()
     */
    public void updateAlbumUI() {
        // setupImageControlPanel(true);
    }

    public Component[] getSongLabels() {
        return songsComponent.getComponents();
    }

    /**
     * @param currentSongLabel
     *            the currentSongLabel to set
     */
    public void setCurrentSongLabel(final QSongButton currentSongLabel) {
        this.currentSongLabel = currentSongLabel;
    }

    /**
     * @param currentSongLabel
     *            the currentSongLabel to set
     */
    public void inactivateCurrentSongLabel() {
        if (currentSongLabel != null) {
            currentSongLabel.setInactive();
        }
    }

    /**
     * @return the currentSongLabel
     */
    public QSongButton getCurrentSongLabel() {
        return currentSongLabel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

        super.paintComponent(g);
    }
}
