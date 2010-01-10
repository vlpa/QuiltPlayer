package com.quiltplayer.external.lyrics;

/**
 * LyricsService interface.
 * 
 * @author Vlado Palczynski
 */
public interface LyricsService {

    /**
     * Get lyrics.
     * 
     * @param artistName
     *            the artistName to set.
     * @param title
     *            the title to set.
     */
    public void getLyrics(String artistName, String title);
}
