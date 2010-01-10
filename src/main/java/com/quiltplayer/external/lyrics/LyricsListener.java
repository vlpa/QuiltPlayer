package com.quiltplayer.external.lyrics;

import java.awt.event.ActionListener;

/**
 * Lyrics listener.
 * 
 * @author Vlado Palczynski
 */
public interface LyricsListener extends ActionListener {
    /**
     * @param e
     *            the lyricsEvent to set.
     */
    void lyricsEvent(LyricsEvent e);
}