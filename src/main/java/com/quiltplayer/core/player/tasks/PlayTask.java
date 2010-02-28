package com.quiltplayer.core.player.tasks;

import com.quiltplayer.core.player.PlayerSelector;
import com.quiltplayer.model.Song;

public class PlayTask implements Runnable {

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
