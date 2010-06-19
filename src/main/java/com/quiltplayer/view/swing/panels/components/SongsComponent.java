package com.quiltplayer.view.swing.panels.components;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

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
        setLayout(new MigLayout("ins 0, flowy, fill"));

        this.playerListener = playerListener;
        this.album = album;

        setOpaque(true);

        setBackground(ColorConstantsDark.PLAYLIST_BACKGROUND);

        setup();
    }

    public void setup() {
        if (album.getSongCollection() != null) {

            int i = 1;

            for (Song song : album.getSongCollection().getSongs()) {
                QSongButton songLabel = null;
                songLabel = new QSongButton(song, playerListener, i);
                songLabel.addActionListener(playerListener);

                add(songLabel, "grow, hmin 1.cm, center, growy, gapy 0.1cm");

                i++;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

        super.paintComponent(g);
    }
}