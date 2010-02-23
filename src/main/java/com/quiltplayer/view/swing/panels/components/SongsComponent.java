package com.quiltplayer.view.swing.panels.components;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.cmc.shared.swing.FlowWrapLayout;

import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Song;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.buttons.QSongButton;

/**
 * Show the tracks. Create new instance as the panel seems to keep the dimensions of the previous
 * album when trying just ro remove all and add new songs again.
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

        JPanel panel = new JPanel(new MigLayout("ins 0, wrap 1"));
        panel.setOpaque(false);
        setup(panel);

        int songSize = album.getSongCollection().getSongs().size();

        JPanel p1 = new JPanel(new MigLayout("ins 0, wrap 1"));
        JPanel p2 = new JPanel(new MigLayout("ins 0, wrap 1"));

        setupButtons(songSize, p1, p2);

        // add(p1, "west, gapy 0.0cm");
        // add(panel, "w 100%, gapy 0.20cm!, aligny top");
        // add(p2, "east, gapy 0.65cm");

    }

    private void setupButtons(int songSize, JPanel p1, JPanel p2) {
        final String layout = "w 1cm!, h 1cm!, gapy 0 0.25cm! ";

        for (int i = 1; i <= songSize; i++) {
            if (i % 2 == 0) {
                p2.add(new QButton(i + ""), layout);
            }
            else {
                p1.add(new QButton(i + ""), layout);
            }
        }
    }

    public void setup(final JPanel panel) {
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