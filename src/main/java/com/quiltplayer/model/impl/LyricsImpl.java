package com.quiltplayer.model.impl;

import com.quiltplayer.model.Lyrics;

public class LyricsImpl implements Lyrics {

    private String albumTitle;

    private String artistName;

    private String lyrics;

    private String writer;

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Lyrics#getAlbumTitle()
     */
    @Override
    public String getAlbumTitle() {
        return albumTitle;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Lyrics#getArtistName()
     */
    @Override
    public String getArtistName() {
        return artistName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Lyrics#getLyrics()
     */
    @Override
    public String getLyrics() {
        return lyrics;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Lyrics#getWriter()
     */
    @Override
    public String getWriter() {
        return writer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Lyrics#setAlbumTitle(java.lang.String)
     */
    @Override
    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Lyrics#setArtistName(java.lang.String)
     */
    @Override
    public void setArtistName(String artistName) {
        this.artistName = artistName;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Lyrics#setLyrics(java.lang.String)
     */
    @Override
    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Lyrics#setWriter(java.lang.String)
     */
    @Override
    public void setWriter(String writer) {
        this.writer = writer;
    }

}
