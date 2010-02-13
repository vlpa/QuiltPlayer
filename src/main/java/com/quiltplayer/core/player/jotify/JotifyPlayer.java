package com.quiltplayer.core.player.jotify;

import java.awt.event.ActionEvent;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.PlayerController;
import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.core.player.Player;
import com.quiltplayer.core.repo.spotify.JotifyRepository;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.jotify.JotifySong;

import de.felixbruns.jotify.media.Track;
import de.felixbruns.jotify.player.PlaybackListener;

/**
 * 
 * @author vlado
 * 
 */
@Component
public class JotifyPlayer implements Player, PlaybackListener {

    private Logger log = Logger.getLogger(JotifyPlayer.class);

    @Autowired
    private JotifyRepository jotifyRepository;

    @Autowired
    private PlayerListener playerListener;

    private Song currentSong;

    @Override
    public synchronized long getElapsedTime() {
        return (long) JotifyRepository.getInstance().position() * 1000000;
    }

    @Override
    public synchronized void pause() {
        JotifyRepository.getInstance().pause();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#play(com.quiltplayer.model.Song)
     */
    @Override
    public synchronized void play(final Song s) {
        log.debug("Initializing play for spotify song:" + s.getTitle());

        currentSong = s;

        stop();

        if (s instanceof JotifySong) {
            try {
                jotifyRepository.getInstance().play(((JotifySong) s).getSpotifyTrack(), this);
            }
            catch (TimeoutException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else {
            Track track = new Track(s.getSpotifyId());
            try {
                /* We need the files of the track... */
                track = jotifyRepository.getInstance().browse(track);
                jotifyRepository.getInstance().play(track, this);
            }
            catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void stop() {
        log.debug("Stopping play...");

        JotifyRepository.getInstance().stop();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.felixbruns.jotify.player.PlaybackListener#playbackFinished(de.felixbruns
     * .jotify.media.Track)
     */
    @Override
    public synchronized void playbackFinished(Track track) {
        playerListener.actionPerformed(new ActionEvent(currentSong, 0,
                PlayerController.PlayerSongEvents.FINISHED.toString()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.felixbruns.jotify.player.PlaybackListener#playbackPosition(de.felixbruns
     * .jotify.media.Track, int)
     */
    @Override
    public void playbackPosition(Track track, int position) {
        playerListener.actionPerformed(new ActionEvent(currentSong, 0, EVENT_PROGRESS));
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.felixbruns.jotify.player.PlaybackListener#playbackResumed(de.felixbruns
     * .jotify.media.Track)
     */
    @Override
    public void playbackResumed(Track track) {
        log.debug("Playback resumed...");

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.felixbruns.jotify.player.PlaybackListener#playbackStarted(de.felixbruns
     * .jotify.media.Track)
     */
    @Override
    public void playbackStarted(Track track) {
        log.debug("Playback started...");
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.felixbruns.jotify.player.PlaybackListener#playbackStopped(de.felixbruns
     * .jotify.media.Track)
     */
    @Override
    public void playbackStopped(Track track) {
        log.debug("Playback stopped...");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#removeCurrentSong()
     */
    @Override
    public void removeCurrentSong() {
        // TODO Auto-generated method stub

    }
}
