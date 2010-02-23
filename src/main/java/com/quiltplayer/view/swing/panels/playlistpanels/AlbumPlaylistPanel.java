package com.quiltplayer.view.swing.panels.playlistpanels;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.impl.NullAlbum;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.buttons.QSongButton;
import com.quiltplayer.view.swing.effects.CrossFader;
import com.quiltplayer.view.swing.labels.ImageControlLabel;
import com.quiltplayer.view.swing.listeners.ArtistListener;
import com.quiltplayer.view.swing.listeners.ImageListener;
import com.quiltplayer.view.swing.panels.AlbumPresentationPanel;
import com.quiltplayer.view.swing.panels.components.SongsComponent;

/**
 * Represents the playlist panel. One Panel will give you information about the album, tracks and so
 * forth. The other will give you information regarding the artist.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component
public class AlbumPlaylistPanel extends JPanel {

    private Logger log = Logger.getLogger(AlbumPlaylistPanel.class);

    private static final long serialVersionUID = 1L;

    private QSongButton currentSongLabel;

    private JButton albumsButton;

    private SongsComponent songsComponent;

    private JPanel imageControlPanel = new JPanel();

    private JPanel imageButtons = new JPanel();

    @Autowired
    private AlbumPresentationPanel albumPresentationPanel;

    @Autowired
    private ArtistListener artistListener;

    @Autowired
    private PlayerListener playerListener;

    @Autowired
    private ImageListener imageListener;

    private ImageControlLabel imageCounterLabel;

    private Album album;

    @Autowired
    private CrossFader crossFader;

    public AlbumPlaylistPanel() {
        super(new MigLayout("insets 0, wrap 1, fill, h 100%"));
        setOpaque(true);

        setBackground(ColorConstantsDark.PLAYLIST_BACKGROUND);
    }

    @PostConstruct
    public void init() {
        this.album = new NullAlbum();

        // playlistButtonPanel = new JPanel(new MigLayout("insets 0, wrap 1, fill"));
        // setupImageControlPanel(false);

        add(albumPresentationPanel, "top");
        add(crossFader, "h " + ImageSizes.LARGE.getSize() + "px!, w 100%, top, gapy 0.3cm");
        // add(imageControlPanel, "alignx center, aligny top, w 100%, gapx 0.5cm 0.5cm");
    }

    private void setupSongsPanel() {
        if (songsComponent != null)
            remove(songsComponent);

        songsComponent = new SongsComponent(album, playerListener);

        add(songsComponent, "w 100%, h 100%, top, gapy 0.2cm");

        songsComponent.repaint();
        repaint();
    }

    /**
     * Setup the album cover image.
     */
    private void setupImageControlPanel(boolean update) {
        imageControlPanel.setLayout(new MigLayout("insets 0, wrap 4, fillx"));
        imageControlPanel.setOpaque(false);

        setAlbumButtons();
    }

    /**
     * Add +/- buttons if more than one cover exists
     * 
     * @param album
     */
    private void setAlbumButtons() {
        imageControlPanel.remove(imageButtons);

        if (album.getImages() != null && album.getImages().size() > 1) {
            imageButtons = new JPanel();
            imageButtons.setLayout(new MigLayout("insets 0, wrap 4, fillx"));
            imageButtons.setOpaque(false);

            // ImageControlLabel increase = new ImageControlLabel("[ + ]");
            // increase.setToolTipText("Next album picture");
            // increase.addMouseListener(increaseListener);
            // imageButtons.add(increase, "dock west, gapafter 5lp"); // 15

            // ImageControlLabel decrease = new ImageControlLabel("[ - ]");
            // decrease.setToolTipText("Previous album picture");
            // decrease.addMouseListener(decreaseListener);
            // imageButtons.add(decrease, "dock west, gapafter 5lp");

            ImageControlLabel cover = new ImageControlLabel("[ #1 ]");
            cover.setToolTipText("Set as front cover");

            MouseListener changeCoverListener = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Implement against CrossFader...
                    // imageListener.actionPerformed(new ActionEvent(album, iconLabel.getCounter(),
                    // ImageController.EVENT_CHANGE_COVER));
                }
            };

            cover.addMouseListener(changeCoverListener);

            imageButtons.add(cover, "dock east, gapbefore 10lp");

            imageCounterLabel = new ImageControlLabel("");
            imageCounterLabel.addMouseListener(null);
            imageCounterLabel.setOpaque(false);
            // imageCounterLabel.setText(imageCounter + 1 + "/"
            // + album.getImages().size());
            imageButtons.add(imageCounterLabel, "dock east");

            imageControlPanel.add(imageButtons, "w 100%");
        }
    }

    public void changeAlbum(final Album album) {
        log.debug("Changing album...");
        this.album = album;

        crossFader.setImages(album.getImages());

        albumPresentationPanel.update(album);

        setupSongsPanel();

        setLocation(0, 0);

        repaint();

        log.debug("Album is changed...");
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
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paint(g);
    }
}
