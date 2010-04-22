package com.quiltplayer.core.player;

import java.awt.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.PlayerController;
import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.core.player.tasks.PlayTask;
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
    public void pause() {
        playerSelector.pause();

        playerListener.actionPerformed(new ActionEvent("", 0, PlayerController.PlayerEvents.PAUSEED.toString()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#stop()
     */
    public void stop() {
        playerSelector.stop();

        playerListener.actionPerformed(new ActionEvent("", 0, PlayerController.PlayerEvents.STOPPED.toString()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#play(com.quiltplayer.model.Song)
     */
    public void play(Song song) {
        taskExecutor.execute(new PlayTask(song, playerSelector));
    }

    public void seek(int i) {
        playerSelector.seek(i);
    }
}
