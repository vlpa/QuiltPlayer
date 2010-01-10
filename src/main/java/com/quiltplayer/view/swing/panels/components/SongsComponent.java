/**
 * QuiltPlayer v1.0 Copyright (C) 2008-2009 Vlado Palczynski
 * vlado.palczynski@quiltplayer.com http://www.quiltplayer.com This program is
 * free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or (at your option) any later version. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package com.quiltplayer.view.swing.panels.components;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Song;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.scrollbars.QScrollBar;

/**
 * Show the tracks.
 * 
 * @author Vlado Palczynski
 */
public class SongsComponent extends QPlaylistComponent {

    private static final int VERTICAL_UNIT_INCRENET = 20;

    /**
     * The main panel.
     */
    private JPanel songsPanel = new JPanel(new MigLayout("insets 0, wrap 1", "[grow, left]",
            "[grow, shrink 0, center]"));

    /**
     * The scollpanel.
     */
    private JScrollPane songsScroller;

    /**
     * The listener.
     */
    private PlayerListener playerListener;

    /**
     * The current album.
     */
    private Album album;

    /**
     * The songs panel.
     */
    private JPanel songs = new JPanel(new MigLayout("insets 0, wrap 1"));

    public JPanel create(Album album) {
        this.album = album;

        songs.setBackground(Configuration.getInstance().getColorConstants()
                .getPlaylistSongBackgroundInactive());

        if (songsScroller != null)
            songs.remove(songsScroller);

        songs.add(addSongs(), "");

        return songs;

    }

    private JScrollPane addSongs() {
        songsPanel.removeAll();
        songsPanel.setBackground(Configuration.getInstance().getColorConstants()
                .getPlaylistSongBackgroundInactive());

        for (Song song : album.getSongCollection().getSongs()) {
            SongLabel songLabel = new SongLabel(song);
            songLabel.addActionListener(playerListener);

            songsPanel.add(songLabel);
        }

        songsScroller = new JScrollPane(songsPanel);
        songsScroller.setVerticalScrollBar(new QScrollBar(JScrollBar.VERTICAL));
        songsScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        songsScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        songsScroller.setBorder(BorderFactory.createEmptyBorder());
        songsScroller.setWheelScrollingEnabled(true);
        songsScroller.getVerticalScrollBar().setUnitIncrement(VERTICAL_UNIT_INCRENET);

        return songsScroller;
    }

    /**
     * @param playerListener
     *            the playerListener to set.
     */
    public void setPlayerListener(PlayerListener playerListener) {
        this.playerListener = playerListener;
    }

    public JPanel getSongsPanel() {
        return songsPanel;
    }
}
