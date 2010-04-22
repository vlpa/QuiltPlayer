package com.quiltplayer.model.jotify;

import org.apache.commons.lang.NotImplementedException;

import com.quiltplayer.model.Album;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.StringId;

import de.felixbruns.jotify.media.Track;

public class JotifySong implements Song {

    private Track spotifyTrack;

    public JotifySong(Track spotifyTrack) {
        this.spotifyTrack = spotifyTrack;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#getAlbum()
     */
    @Override
    public Album getAlbum() {
        return new JotifyAlbum(spotifyTrack.getAlbum());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#getFileName()
     */
    @Override
    public String getFileName() {
        // TODO
        // return spotifyTrack.getFiles().get(0).getId();
        return "";

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#getId()
     */
    @Override
    public StringId getId() {
        return new StringId(spotifyTrack.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#getPath()
     */
    @Override
    public String getPath() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#getTitle()
     */
    @Override
    public String getTitle() {
        return spotifyTrack.getTitle();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#getTrackNumber()
     */
    @Override
    public Number getTrackNumber() {
        return spotifyTrack.getTrackNumber();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#setAlbum(com.quiltplayer.model.Album)
     */
    @Override
    public void setAlbum(Album album) {
        throw new NotImplementedException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#setFileName(java.lang.String)
     */
    @Override
    public void setFileName(String fileName) {
        throw new NotImplementedException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#setId(com.quiltplayer.model.StringId)
     */
    @Override
    public void setId(StringId songId) {
        throw new NotImplementedException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#setPath(java.lang.String)
     */
    @Override
    public void setPath(String path) {
        throw new NotImplementedException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#setTitle(java.lang.String)
     */
    @Override
    public void setTitle(String title) {
        throw new NotImplementedException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#setTrackNumber(java.lang.Number)
     */
    @Override
    public void setTrackNumber(Number trackNumber) {
        throw new NotImplementedException();
    }

    /**
     * @return the spotifyTrack
     */
    public final Track getSpotifyTrack() {
        return spotifyTrack;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getTitle();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#getSpotifyId()
     */
    @Override
    public String getSpotifyId() {
        return spotifyTrack.getId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#setSpotifyId(java.lang.String)
     */
    @Override
    public void setSpotifyId(String spotifyId) {
        throw new NotImplementedException("");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#getType()
     */
    @Override
    public String getType() {
        return Song.TYPE_SPOTIFY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Song#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
        throw new NotImplementedException();
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof JotifySong) {
            return this.spotifyTrack.getId().equals(((JotifySong) o).getSpotifyTrack().getId());
        }

        return false;
    }

    @Override
    public int getLength() {
        return spotifyTrack.getLength();
    }

    @Override
    public void setLength(int length) {
        // Not needed
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
