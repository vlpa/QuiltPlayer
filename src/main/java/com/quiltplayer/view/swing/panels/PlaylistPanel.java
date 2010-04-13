package com.quiltplayer.view.swing.panels;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.jxlayer.JXLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.buttons.QSongButton;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.panels.controlpanels.PlayerControlPanel;
import com.quiltplayer.view.swing.panels.playlistpanels.AlbumPlaylistPanel;
import com.quiltplayer.view.swing.panels.playlistpanels.EditPlaylistPanel;
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

    public enum Mode {
        SONG, LYRICS, EDIT, HIDDEN
    }

    public Mode mode = Mode.HIDDEN;

    protected static final long serialVersionUID = 1L;

    protected static final String EVENT_CHANGE_SONG = "change.song";

    public static final String EVENT_UPDATE_ALBUM_ID3 = "update.album.id3";

    private QSongButton currentSongLabel;

    protected Album album;

    private JComponent albumPlaylistComponent;

    private JComponent lyricsPlaylistComponent;

    private QScrollPane scrollPane;

    @Autowired
    private PlayerControlPanel playerControlPanel;

    @Autowired
    @Qualifier("albumPlaylistPanel")
    protected AlbumPlaylistPanel albumPlaylistPanel;

    @Autowired
    @Qualifier("lyricsPlaylistPanel")
    protected LyricsPlaylistPanel lyricsPlaylistPanel;

    @Autowired
    @Qualifier("editPlaylistPanel")
    protected EditPlaylistPanel editPlaylistPanel;

    public PlaylistPanel() {
        super(new MigLayout("ins 0.0cm 0.0cm 0.0cm 0.0cm, fill, alignx center"));

        setBackground(ColorConstantsDark.PLAYLIST_BACKGROUND);
    }

    @PostConstruct
    public void init() {
        add(playerControlPanel, "south");

        viewAlbumPanel();
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

    public void changeAlbum(final Album album) {
        this.album = album;

        albumPlaylistPanel.changeAlbum(album);
        editPlaylistPanel.changeAlbum(album);
    }

    public void setLyrics(final String lyrics) {
        lyricsPlaylistPanel.setLyrics(lyrics);
    }

    public synchronized void viewAlbumPanel() {
        if (mode != Mode.SONG) {
            removeComponentsIfNull();

            scrollPane = new QScrollPane(albumPlaylistPanel);

            albumPlaylistComponent = new JXLayer<JScrollPane>(scrollPane, new JScrollPaneLayerUI());

            add(albumPlaylistComponent, "w 100%, h 100%, center");

            updateUI();

            mode = Mode.SONG;
        }
    }

    public synchronized void viewLyricsPanel() {
        if (mode != Mode.LYRICS) {
            removeComponentsIfNull();

            final QScrollPane pane = new QScrollPane(lyricsPlaylistPanel);

            lyricsPlaylistComponent = new JXLayer<JScrollPane>(pane, new JScrollPaneLayerUI());

            add(lyricsPlaylistComponent, "w 100%, h 100%, center");

            updateUI();

            mode = Mode.LYRICS;
        }
    }

    public synchronized void viewEditPanel() {
        if (mode != Mode.EDIT) {
            removeComponentsIfNull();

            add(editPlaylistPanel, "w 100%, h 100%, center");

            updateUI();

            mode = Mode.EDIT;
        }
    }

    private void removeComponentsIfNull() {
        if (albumPlaylistComponent != null)
            remove(albumPlaylistComponent);
        if (lyricsPlaylistComponent != null)
            remove(lyricsPlaylistComponent);

        remove(editPlaylistPanel);
    }

    public Album getPlayingAlbum() {
        return album;
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

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JPanel#updateUI()
     */
    @Override
    public void updateUI() {

        if (mode == Mode.EDIT)
            editPlaylistPanel.updateUI();
        else if (mode == Mode.SONG)
            albumPlaylistPanel.updateUI();

        super.updateUI();
    }
}
