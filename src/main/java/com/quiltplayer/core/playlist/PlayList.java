package com.quiltplayer.core.playlist;

import java.util.Collection;

import com.quiltplayer.model.Song;

/**
 * Player interface.
 * 
 * @author Vlado Palczynski
 */
public interface PlayList {
    /**
     * Empty the playlist.
     */
    void clearPlaylist();

    /**
     * Add song to playlist.
     * 
     * @param song
     *            the song to add.
     */
    void addSong(Song song);

    /**
     * Add songs to playlist.
     * 
     * @param songs
     *            the songs to add.
     */
    void addSongs(Collection<Song> songs);

    /**
     * Jumps in playlist to the song.
     * 
     * @param song
     *            the song to jump to.
     */
    void jumpToSong(Song song);

    /**
     * Clears the playlist and plays the new album songs.
     * 
     * @param songs
     *            the songs which will be change to.
     */
    void changeAlbum(Collection<Song> songs);

    /**
     * Retrieve the current song playing.
     * 
     * @return Current song
     */
    Song getCurrentSong();

    void nextSong();

    void prevSong();
}
