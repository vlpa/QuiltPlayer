package com.quiltplayer.core.player;

import java.awt.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.core.player.tasks.PlayTask;
import com.quiltplayer.external.lyrics.LyricsListener;
import com.quiltplayer.model.Song;

/**
 * Factory for players.
 * 
 * @author Vlado Palczynski
 * 
 */
@Component
public class PlayerFactory {

    @Autowired
    private PlayerListener playerListener;

    @Autowired
    private LyricsListener lyricsListener;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private PlayerSelector playerSelector;

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#getElapsedTime()
     */
    public long getElapsedTime() {
        return playerSelector.getElapsedTime();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#removeCurrentSong()
     */
    public void removeCurrentSong() {
        playerSelector.removeCurrentSong();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#pause()
     */
    public synchronized void pause() {
        playerSelector.pause();

        playerListener.actionPerformed(new ActionEvent("", 0, Player.EVENT_PAUSED_SONG));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#stop()
     */
    public synchronized void stop() {
        playerSelector.stop();

        playerListener.actionPerformed(new ActionEvent("", 0, Player.EVENT_STOPPED_SONG));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#play(com.quiltplayer.model.Song)
     */
    public synchronized void play(Song song) {
        taskExecutor.execute(new PlayTask(song, playerSelector));

        playerListener.actionPerformed(new ActionEvent(song, 0, Player.EVENT_PLAYING_NEW_SONG));
        lyricsListener.actionPerformed(new ActionEvent(song, 0, Player.EVENT_PLAYING_NEW_SONG));
    }
}
