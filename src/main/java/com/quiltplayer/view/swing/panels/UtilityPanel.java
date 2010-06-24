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

import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.buttons.QSongButton;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.panels.QScrollPane.ScrollDirection;
import com.quiltplayer.view.swing.panels.utility.AlbumUtilityPanel;
import com.quiltplayer.view.swing.panels.utility.EditUtilityPanel;
import com.quiltplayer.view.swing.panels.utility.LyricsUtilityPanel;

/**
 * Represents the playlist panel. One Panel will give you information about the
 * album, tracks and so forth. The other will give you information regarding the
 * artist.
 * 
 * This class will outline the panel and delegate the UI for the different
 * implementation.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component
public class UtilityPanel extends JPanel {

    public enum Mode {
        SONG, LYRICS, EDIT, HIDDEN
    }

    public Mode mode = Mode.HIDDEN;

    protected static final long serialVersionUID = 1L;

    protected static final String EVENT_CHANGE_SONG = "change.song";

    public static final String EVENT_UPDATE_ALBUM_ID3 = "update.album.id3";

    private QSongButton currentSongLabel;

    protected Album album;

    private JComponent lyricsPlaylistComponent;

    private JComponent editAlbumComponent;

    private QScrollPane scrollPane;

    @Autowired
    private QuiltPlayerFrame frame;

    @Autowired
    protected AlbumUtilityPanel albumPlaylistPanel;

    @Autowired
    protected LyricsUtilityPanel lyricsPlaylistPanel;

    @Autowired
    protected EditUtilityPanel editPlaylistPanel;

    public boolean playlistPanelVisible;

    private boolean lyricsPanelVisible;

    private boolean editAlbumPanelVisible;

    public UtilityPanel() {
        super(new MigLayout("ins 0, gap 0"));
        setOpaque(false);
    }

    @PostConstruct
    public void init() {
        scrollPane = new QScrollPane(lyricsPlaylistPanel, ScrollDirection.VERTICAL);
        lyricsPlaylistComponent = new JXLayer<JScrollPane>(scrollPane, new JScrollPaneLayerUI());

        scrollPane = new QScrollPane(editPlaylistPanel, ScrollDirection.VERTICAL);
        editAlbumComponent = new JXLayer<JScrollPane>(scrollPane, new JScrollPaneLayerUI());
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

            albumPlaylistPanel.updateUI();
            frame.repaintUi();
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

    public void toggleAlbumView() {
        if (playlistPanelVisible) {
            removePanelFromFrame(albumPlaylistPanel);
            playlistPanelVisible = false;
        }
        else {
            addPanelToFrame(albumPlaylistPanel, "cell 3 0");
            playlistPanelVisible = true;
        }

        albumPlaylistPanel.repaint();
    }

    public void toggleLyricsView() {
        if (lyricsPanelVisible) {
            removePanelFromFrame(lyricsPlaylistComponent);
            lyricsPanelVisible = false;
        }
        else {
            addPanelToFrame(lyricsPlaylistComponent, "cell 2 0");
            lyricsPanelVisible = true;
        }

        lyricsPlaylistPanel.repaint();
        frame.getUI().repaint();
    }

    public void toggleEditView() {
        if (editAlbumPanelVisible) {
            removePanelFromFrame(editAlbumComponent);
            editAlbumPanelVisible = false;
        }
        else {
            addPanelToFrame(editAlbumComponent, "cell 1 0");
            editAlbumPanelVisible = true;
        }

        editPlaylistPanel.repaint();
    }

    private void addPanelToFrame(Component c, String cell) {
        frame.getUtilityPanel().add(c, cell + ",  h 100%");
        frame.getUtilityPanel().updateUI();
    }

    private void removePanelFromFrame(Component c) {
        frame.getUtilityPanel().remove(c);
        frame.getUtilityPanel().updateUI();
    }
}
