package com.quiltplayer.core.player;

import com.quiltplayer.model.Song;

/**
 * Player interface.
 * 
 * @author Vlado Palczynski
 */
public interface Player {

    String EVENT_PLAYING_NEW_SONG = "playing.new.song";

    String EVENT_STOPPED_SONG = "song.stopped";

    String EVENT_PROGRESS = "progress";

    String EVENT_PAUSED_SONG = "song.paused";

    String EVENT_RESUMED_SONG = "song.resumed";

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
}
