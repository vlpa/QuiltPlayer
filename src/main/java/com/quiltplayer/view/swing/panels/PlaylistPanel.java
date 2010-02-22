package com.quiltplayer.view.swing.panels;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.buttons.QSongButton;
import com.quiltplayer.view.swing.panels.controlpanels.AlbumControlPanel;
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

    private QSongButton currentSongLabel;

    protected JPanel mainPanel;

    protected Album album;

    @Autowired
    @Qualifier("albumPlaylistPanel")
    protected AlbumPlaylistPanel albumPlaylistPanel;

    @Autowired
    @Qualifier("lyricsPlaylistPanel")
    protected LyricsPlaylistPanel lyricsPlaylistPanel;

    @Autowired
    private AlbumControlPanel albumControlPanel;

    public PlaylistPanel() {
        super(new MigLayout("insets 0 0.35cm 0 0.35cm"));

        setBackground(ColorConstantsDark.ARTISTS_PANEL_BACKGROUND);
    }

    @PostConstruct
    public void init() {
        mainPanel = new JPanel(new MigLayout("insets 0, fill"));

        mainPanel.setOpaque(true);
        mainPanel.setBackground(ColorConstantsDark.ARTISTS_PANEL_BACKGROUND);

        add(mainPanel, "h 100%, w " + ImageSizes.LARGE.getSize() + "px!");

        mainPanel.add(albumControlPanel, "dock south");

        setupSongsPanel();
    }

    public void progress(long time) {
        currentSongLabel.setProgress(time);
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
    }

    public void changeAlbum(final Album album) {
        this.album = album;

        albumPlaylistPanel.changeAlbum(album);

        albumControlPanel.update(album);
    }

    public void setLyrics(final String lyrics) {
        lyricsPlaylistPanel.setLyrics(lyrics);
    }

    private void addAlbumPlaylistPanelToMainPanel() {
        mainPanel.add(albumPlaylistPanel, "h 100%, w 100%");
    }

    public void viewAlbumPanel() {

        mainPanel.remove(lyricsPlaylistPanel);
        mainPanel.add(albumPlaylistPanel, "w 100%, h 100%");

        repaint();
    }

    public void viewLyricsPanel() {

        mainPanel.remove(albumPlaylistPanel);
        mainPanel.add(lyricsPlaylistPanel, "w 100%, h 100%");

        repaint();
    }

    public Album getPlayingAlbum() {
        return album;
    }
}
