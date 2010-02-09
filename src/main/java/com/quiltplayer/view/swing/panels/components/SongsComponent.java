package com.quiltplayer.view.swing.panels.components;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Song;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.buttons.QSongButton;
import com.quiltplayer.view.swing.panels.QScrollPane;

/**
 * Show the tracks.
 * 
 * @author Vlado Palczynski
 */
public class SongsComponent extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * The listener.
     */
    private PlayerListener playerListener;

    /**
     * The current album.
     */
    private Album album;

    private QScrollPane pane = new QScrollPane(this);

    public SongsComponent() {
        setLayout(new MigLayout("insets 0, wrap 1, w 100%"));
        setBackground(ColorConstantsDark.ARTISTS_PANEL_BACKGROUND);
        setOpaque(true);
    }

    public JScrollPane create(Album album) {
        this.album = album;

        if (album.getSongCollection() != null)
            addSongs();

        return pane;
    }

    private void addSongs() {
        removeAll();

        for (Song song : album.getSongCollection().getSongs()) {
            QSongButton songLabel = new QSongButton(song, playerListener);
            songLabel.addActionListener(playerListener);

            add(songLabel, "w 100%");
        }
    }

    /**
     * @param playerListener
     *            the playerListener to set.
     */
    public void setPlayerListener(PlayerListener playerListener) {
        this.playerListener = playerListener;
    }

    public JPanel getSongsPanel() {
        return this;
    }
}
