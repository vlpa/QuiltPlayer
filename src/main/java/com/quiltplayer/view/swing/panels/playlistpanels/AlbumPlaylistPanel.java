package com.quiltplayer.view.swing.panels.playlistpanels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.impl.NullAlbum;
import com.quiltplayer.model.jotify.JotifyAlbum;
import com.quiltplayer.model.neo.NeoAlbum;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.effects.CrossFader;
import com.quiltplayer.view.swing.labels.ArtistLabel;
import com.quiltplayer.view.swing.labels.ImageControlLabel;
import com.quiltplayer.view.swing.listeners.ArtistListener;
import com.quiltplayer.view.swing.listeners.ImageListener;
import com.quiltplayer.view.swing.panels.AlbumPresentationPanel;
import com.quiltplayer.view.swing.panels.components.SongLabel;
import com.quiltplayer.view.swing.panels.components.SongsComponent;

/**
 * Represents the playlist panel. One Panel will give you information about the album, tracks and so
 * forth. The other will give you information regarding the artist.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component
public class AlbumPlaylistPanel extends AbstractPlaylistPanel {

    private static final long serialVersionUID = 1L;

    private SongLabel currentSongLabel;

    private JButton editButton;

    private JButton albumsButton;

    private JPanel songs = new JPanel(new MigLayout("insets 0, wrap 1"));

    private SongsComponent songsComponent;

    private JPanel imageControlPanel = new JPanel();

    private JPanel imageButtons = new JPanel();

    private JPanel playlistButtonPanel;

    @Autowired
    private AlbumPresentationPanel albumPresentationPanel;

    JSeparator songSeparator = new JSeparator(JSeparator.HORIZONTAL);

    @Autowired
    private ArtistListener artistListener;

    @Autowired
    private PlayerListener playerListener;

    @Autowired
    private ImageListener imageListener;

    private ImageControlLabel imageCounterLabel;

    private Album album;

    private CrossFader crossFader;

    public AlbumPlaylistPanel() {
        super();
    }

    @PostConstruct
    public void init() {
        this.album = new NullAlbum();

        songSeparator = new JSeparator(JSeparator.HORIZONTAL);
        songSeparator.setBackground(Color.WHITE);
        songSeparator.setForeground(Color.GRAY);

        playlistButtonPanel = new JPanel(new MigLayout("insets 0, wrap 1, center, aligny center"));

        // iconLabel = new PlaylistImageLabel(album, jotifyRepository);

        crossFader = new CrossFader();

        setupImageControlPanel(false);
        setupEditAlbumButton();

        add(albumPresentationPanel, "top, w " + ImageSizes.LARGE.getSize() + "px!");
        add(crossFader, "alignx center,  aligny top, h " + ImageSizes.LARGE.getSize() + "px!, w "
                + ImageSizes.LARGE.getSize() + "px!, gapy 20");
        add(imageControlPanel, "alignx center, aligny top, h 30lp!, w "
                + ImageSizes.LARGE.getSize() + "px!");

        // addPlaylistButtons();
        addEditAlbumButton();

        // playlistButtonPanel.setVisible(false);

        songsComponent = new SongsComponent();
        songsComponent.setPlayerListener(playerListener);
    }

    private void setupSongsPanel() {
        songs = songsComponent.create(album);

        add(songs, "h 40%, w " + ImageSizes.LARGE.getSize() + "px!, top, gapy 5");

        // playlistButtonPanel.setVisible(true);

        if (album instanceof NeoAlbum) {
            addEditAlbumButton();
        }
        else if (album instanceof JotifyAlbum) {
            addAddToCollectionButton();
        }

        // playlistButtonPanel.repaint();

        SwingUtilities.updateComponentTreeUI(this);
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
            imageButtons.setBackground(Configuration.getInstance().getColorConstants()
                    .getPlaylistSongBackgroundInactive());

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

    private void addEditAlbumButton() {
        playlistButtonPanel.add(editButton, "cell 0 0, wmin 2.5cm");
    }

    private void addAddToCollectionButton() {
        playlistButtonPanel.remove(editButton);
    }

    private void setupAlbumsByArtistButton() {
        /*
         * Other albums by artist
         */
        albumsButton = new QButton("More albums");

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                artistListener.actionPerformed(new ActionEvent(album.getArtist(), 0,
                        ArtistLabel.ACTION_GET_ARTIST_ALBUMS));
            }
        };

        albumsButton.addActionListener(actionListener);
    }

    public void changeAlbum(final Album album) {
        this.album = album;

        crossFader.setImages(album.getImages());
        crossFader.startAnimation();

        albumPresentationPanel.update(album);

        setupSongsPanel();

        updateAlbumUI();
    }

    /*
     * @see javax.swing.JPanel#updateUI()
     */
    public void updateAlbumUI() {
        setupImageControlPanel(true);
    }

    public Component[] getSongLabels() {
        return songsComponent.getSongsPanel().getComponents();
    }

    public void progress(long time) {
        currentSongLabel.setProgress(time);
    }

    /**
     * @param currentSongLabel
     *            the currentSongLabel to set
     */
    public void setCurrentSongLabel(final SongLabel currentSongLabel) {
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
    public SongLabel getCurrentSongLabel() {
        return currentSongLabel;
    }

    /**
     * 
     */
    private void setupEditAlbumButton() {
        editButton = new QButton("Edit album");
        editButton.setToolTipText("Edit the ID3 information of this album.");

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                // editAlbumListener.actionPerformed(new ActionEvent(album, 0,
                // PlaylistPanel.EVENT_UPDATE_ALBUM_ID3));
            }
        };

        editButton.addActionListener(actionListener);
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
