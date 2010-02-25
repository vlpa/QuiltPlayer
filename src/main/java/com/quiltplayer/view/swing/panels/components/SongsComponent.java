package com.quiltplayer.view.swing.panels.components;

import javax.swing.JPanel;

import org.cmc.shared.swing.FlowWrapLayout;

import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Song;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.buttons.QSongButton;

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

    public SongsComponent(final Album album, final PlayerListener playerListener) {
        super(new FlowWrapLayout());

        this.playerListener = playerListener;
        this.album = album;

        setBackground(ColorConstantsDark.PLAYLIST_BACKGROUND);
        setOpaque(true);

        setup();
    }

    public void setup() {
        if (album.getSongCollection() != null) {

            int i = 1;

            for (Song song : album.getSongCollection().getSongs()) {

                QSongButton songLabel = null;
                songLabel = new QSongButton(song, playerListener, i);
                songLabel.addActionListener(playerListener);

                songLabel.setOpaque(false);

                add(songLabel);

                i++;
            }
        }
    }
}