package com.quiltplayer.core.player;

import com.quiltplayer.model.Song;

/**
 * Player interface.
 * 
 * @author Vlado Palczynski
 */
public interface Player {

    /**
     * Start playing songs from the playlist.
     */
    void play(Song song);

    /**
     * Start playing songs from the playlist.
     */
    void stop();

    /**
     * Pauses/resumes the playing song.
     */
    void pause();

    /**
     * @return long
     */
    long getElapsedTime();

    /**
     * If an album gets removed.
     */
    void removeCurrentSong();

    /**
     * 
     * @param i
     *            the position to seek to.
     */
    void seek(int i);
}
