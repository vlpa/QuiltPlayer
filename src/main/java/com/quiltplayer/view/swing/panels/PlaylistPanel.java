package com.quiltplayer.view.swing.panels;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.buttons.QTab;
import com.quiltplayer.view.swing.panels.components.SongLabel;
import com.quiltplayer.view.swing.panels.playlistpanels.AlbumPlaylistPanel;
import com.quiltplayer.view.swing.panels.playlistpanels.LyricsPlaylistPanel;

/**
 * Represents the playlist panel. One Panel will give you information about the album, tracks and so
 * forth. The other will give you information regarding the artist.
 * 
 * This class will outline the panel and delegate the UI for the different implementation.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component
public class PlaylistPanel extends JPanel {

    protected enum Mode {
        SONG, LYRICS, EDIT, WIKI
    }

    protected static final long serialVersionUID = 1L;

    protected static final String EVENT_CHANGE_SONG = "change.song";

    public static final String EVENT_UPDATE_ALBUM_ID3 = "update.album.id3";

    private SongLabel currentSongLabel;

    protected JPanel mainPanel;

    protected Album album;

    @Autowired
    @Qualifier("albumPlaylistPanel")
    protected AlbumPlaylistPanel albumPlaylistPanel;

    @Autowired
    @Qualifier("lyricsPlaylistPanel")
    protected LyricsPlaylistPanel lyricsPlaylistPanel;

    @Autowired
    private AlbumControlPanel albumPanel;

    private JPanel playlistButtonPanel;

    private QTab songsButton;

    private QTab lyricsButton;

    public PlaylistPanel() {
        super(new MigLayout("insets 0, wrap 1, alignx center, w 100%, h 100%, fill"));
    }

    @PostConstruct
    public void init() {
        mainPanel = new JPanel(new MigLayout(
                "insets 30, w 100%, h 100%, fillx, filly, alignx center"));

        mainPanel.setOpaque(true);
        mainPanel.setBackground(ColorConstantsDark.ARTISTS_PANEL_BACKGROUND);

        add(mainPanel, "w 100%, h 100%, shrinkprio 0, w " + ImageSizes.LARGE.getSize() + "px!");

        mainPanel.add(albumPanel, "dock south");

        setupSongsPanel();
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

    public Component[] getSongLabels() {
        return albumPlaylistPanel.getSongLabels();
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

        super.paintComponent(g);
    }

    private void setupSongsPanel() {

        addAlbumPlaylistPanelToMainPanel();

        SwingUtilities.updateComponentTreeUI(this);
    }

    public void changeAlbum(final Album album) {
        albumPlaylistPanel.changeAlbum(album);
    }

    public void setLyrics(final String lyrics) {
        lyricsPlaylistPanel.setLyrics(lyrics);
    }

    private void addPlaylistPanelButtons() {
        playlistButtonPanel = new JPanel(new MigLayout("insets 0, w 100%, wrap 2, alignx right"));

        playlistButtonPanel.setOpaque(false);
        playlistButtonPanel.setBackground(Configuration.getInstance().getColorConstants()
                .getPlaylistSongBackgroundInactive());

        setupSongsButton();

        playlistButtonPanel.add(songsButton, "dock east, w 2.5cm, h 0.8cm!");
        playlistButtonPanel.add(lyricsButton, "dock east, w 2.5cm, h 0.8cm!");

        mainPanel.add(playlistButtonPanel, "dock north");
    }

    private void setupSongsButton() {
        songsButton = new QTab("Songs");
        songsButton.setToolTipText("Show songs list.");

        songsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.remove(lyricsPlaylistPanel);
                addAlbumPlaylistPanelToMainPanel();

                repaint();
            }
        });
    }

    private void addAlbumPlaylistPanelToMainPanel() {
        mainPanel.add(albumPlaylistPanel, "w 100%, h 100%, alignx center, aligny center");
    }

    public void viewAlbumPanel() {

        mainPanel.remove(lyricsPlaylistPanel);
        mainPanel.add(albumPlaylistPanel, "w 100%, h 100%");

        SwingUtilities.updateComponentTreeUI(lyricsPlaylistPanel);

        repaint();
    }

    public void viewLyricsPanel() {

        mainPanel.remove(albumPlaylistPanel);
        mainPanel.add(lyricsPlaylistPanel, "w 100%, h 100%");

        SwingUtilities.updateComponentTreeUI(lyricsPlaylistPanel);

        repaint();
    }

    public Album getPlayingAlbum() {
        return album;
    }
}
