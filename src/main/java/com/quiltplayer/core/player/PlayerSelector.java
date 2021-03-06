package com.quiltplayer.core.player;

import java.awt.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.PlayerController;
import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.core.player.jotify.JotifyPlayer;
import com.quiltplayer.core.player.simpleplayer.BasicPlayerPlayer;
import com.quiltplayer.external.lyrics.LyricsListener;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.impl.NullAlbum;
import com.quiltplayer.model.neo.NeoSong;

@Component
public class PlayerSelector implements Player {

    @Autowired
    private BasicPlayerPlayer basicPlayer;

    @Autowired
    private JotifyPlayer jotifyPlayer;

    @Autowired
    private PlayerListener playerListener;

    @Autowired
    private LyricsListener lyricsListener;

    private Song currentSong;

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#getElapsedTime()
     */
    @Override
    public synchronized long getElapsedTime() {
        if (currentSong instanceof NullAlbum)
            return 0;

        return getPlayer(currentSong).getElapsedTime();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#pause()
     */
    @Override
    public synchronized void pause() {
        getPlayer(currentSong).pause();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#play(com.quiltplayer.model.Song)
     */
    @Override
    public synchronized void play(Song song) {
        currentSong = song;
        getPlayer(song).play(song);

        playerListener.actionPerformed(new ActionEvent(song, 0, PlayerController.PlayerEvents.PLAYING.toString()));
        lyricsListener.actionPerformed(new ActionEvent(song, 0, PlayerController.PlayerEvents.PLAYING.toString()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#stop()
     */
    @Override
    public synchronized void stop() {
        getPlayer(currentSong).stop();
    }

    private Player getPlayer(Song song) {
        if (song == null || song instanceof NeoSong && song.getType().equals(Song.TYPE_FILE)) {
            return basicPlayer;
        }

        return jotifyPlayer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#removeCurrentSong()
     */
    @Override
    public synchronized void removeCurrentSong() {
        currentSong = null;
    }

    @Override
    public synchronized void seek(int i) {
        getPlayer(currentSong).seek(i);
    }

}