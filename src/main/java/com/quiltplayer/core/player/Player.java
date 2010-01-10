package com.quiltplayer.core.player;

import com.quiltplayer.model.Song;

/**
 * Player interface.
 * 
 * @author Vlado Palczynski
 */
public interface Player {
    String EVENT_PLAYING_NEW_SONG = "new.song";

    String EVENT_STOPPED_SONG = "stopped.song";

    String EVENT_PROGRESS = "progress";

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
     * Increase the volume of the player.
     */
    void increaseVolume();

    /**
     * Decrease the volume of the player.
     */
    void decreaseVolume();

    /**
     * @return long
     */
    long getElapsedTime();
}
