package com.quiltplayer.core.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.core.player.jotify.JotifyPlayer;
import com.quiltplayer.core.player.simpleplayer.BasicPlayerPlayer;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.impl.NullAlbum;
import com.quiltplayer.model.neo.NeoSong;

@Component
public class PlayerSelector implements Player {

    @Autowired
    private BasicPlayerPlayer basicPlayer;

    @Autowired
    private JotifyPlayer jotifyPlayer;

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
        getPlayer(currentSong).pause();
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

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#stop()
     */
    @Override
    public void stop() {
        getPlayer(currentSong).stop();
    }

    private synchronized Player getPlayer(Song song) {
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
    public void removeCurrentSong() {
        currentSong = null;
    }

}