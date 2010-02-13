package com.quiltplayer.core.player.tasks;

import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.core.player.PlayerSelector;
import com.quiltplayer.model.Song;

public class PlayTask implements Runnable {

    @Autowired
    private PlayerSelector playerSelector;

    private Song song;

    public PlayTask(final Song song, final PlayerSelector playerSelector) {
        this.song = song;
        this.playerSelector = playerSelector;
    }

    public void run() {
        playerSelector.play(song);
    }
}
