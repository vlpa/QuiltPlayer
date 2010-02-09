package com.quiltplayer.core.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.core.player.jotify.JotifyPlayer;
import com.quiltplayer.core.player.simpleplayer.BasicPlayerPlayer;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.impl.NullAlbum;
import com.quiltplayer.model.neo.NeoSong;

/**
 * Factory for players.
 * 
 * @author Vlado Palczynski
 * 
 */
@Component
public class PlayerFactory implements Player {

    @Autowired
    private BasicPlayerPlayer basicPlayer;

    @Autowired
    private JotifyPlayer jotifyPlayer;

    private Player currentPlayer;

    private Song currentSong;

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#getElapsedTime()
     */
    @Override
    public long getElapsedTime() {
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
    public void pause() {
        currentPlayer.pause();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#stop()
     */
    @Override
    public void stop() {
        getPlayer(currentSong).stop();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#play(com.quiltplayer.model.Song)
     */
    @Override
    public void play(Song song) {
        currentSong = song;

        getPlayer(song).play(song);
    }

    private Player getPlayer(Song song) {
        if (song == null || song instanceof NeoSong && song.getType().equals(Song.TYPE_FILE)) {
            currentPlayer = basicPlayer;

            return basicPlayer;
        }

        currentPlayer = jotifyPlayer;

        return jotifyPlayer;
    }

    public void removeCurrentSong() {
        currentSong = null;
    }
}
