package com.quiltplayer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.external.lyrics.LyricsEvent;
import com.quiltplayer.external.lyrics.LyricsListener;
import com.quiltplayer.external.lyrics.LyricsService;
import com.quiltplayer.external.lyrics.Status;
import com.quiltplayer.model.Song;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;

/**
 * Controller handling lyrics.
 * 
 * @author Vlado Palczynski
 * 
 */
@Controller
public class LyricsController implements ActionListener, LyricsListener {

    @Autowired
    private LyricsService lyricsService;

    @Autowired
    private QuiltPlayerFrame frame;

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {
        final String cmd = e.getActionCommand();

        if (PlayerController.PlayerEvents.PLAYING.toString() == cmd) {
            Song song = (Song) e.getSource();

            lyricsService.getLyrics(song.getAlbum().getArtist().getArtistName().getNameForSearches(), song.getTitle());
        }
    }

    /*
     * @see com.quiltplayer.core.lyrics.LyricsListener#lyricsEvent(com.quiltplayer
     * .core.lyrics.LyricsEvent)
     */
    @Override
    public final void lyricsEvent(final LyricsEvent e) {
        if (e.getStatus() == Status.FOUND) {
            frame.lyricsPlaylistPanel.setLyrics(e.getLyrics());
            frame.lyricsPlaylistPanel.repaint();

        }
        else if (e.getStatus() == Status.NOT_FOUND || e.getStatus() == Status.ERROR) {
            frame.lyricsPlaylistPanel.setLyrics("None found...");
            frame.lyricsPlaylistPanel.repaint();
        }
    }
}
