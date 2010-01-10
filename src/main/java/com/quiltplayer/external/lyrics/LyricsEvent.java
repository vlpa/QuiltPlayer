package com.quiltplayer.external.lyrics;

/**
 * Implementation of a Lyrics event.
 * 
 * @author Vlado Palczynski
 */
public class LyricsEvent {
    /**
     * The lyrics.
     */
    private String lyrics;

    /**
     * The status.
     */
    private Status status;

    public LyricsEvent(final Status status, final String lyrics) {
        this.status = status;
        this.lyrics = lyrics;
    }

    /**
     * @return the lyrics
     */
    public String getLyrics() {
        return lyrics;
    }

    /**
     * @param lyrics
     *            the lyrics to set
     */
    public void setLyrics(final String lyrics) {
        this.lyrics = lyrics;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(final Status status) {
        this.status = status;
    }
}
