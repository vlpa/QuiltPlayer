package com.quiltplayer.model;

public interface Lyrics {

    /**
     * @param artistName
     *            the artistName to set.
     */
    void setArtistName(String artistName);

    /**
     * @return the artist.
     */
    String getArtistName();

    /**
     * @param albumTitle
     *            the albumtitle to set.
     */
    void setAlbumTitle(String albumTitle);

    /**
     * @return the albumTitle.
     */
    String getAlbumTitle();

    /**
     * @param lyrics
     *            the lyrics to set.
     */
    void setLyrics(final String lyrics);

    /**
     * @return the lyrics.
     */
    String getLyrics();

    /**
     * @param writer
     *            the writer to set.
     */
    void setWriter(final String writer);

    /**
     * @return the writer
     */
    String getWriter();
}
