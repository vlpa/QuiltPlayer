package com.quiltplayer.core.player.jotify;

import java.awt.event.ActionEvent;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.PlayerController;
import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.core.player.Player;
import com.quiltplayer.core.repo.spotify.JotifyRepository;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.jotify.JotifySong;

import de.felixbruns.jotify.media.File;
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

    private boolean isPaused = false;

    @Autowired
    private PlayerListener playerListener;

    private Song currentSong;

    @Override
    public long getElapsedTime() {
        return (long) JotifyRepository.getInstance().position() * 1000;
    }

    @Override
    public void pause() {
        JotifyRepository.getInstance().pause();

        isPaused = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#play(com.quiltplayer.model.Song)
     */
    @Override
    public void play(final Song s) {
        log.debug("Initializing play for spotify song:" + s.getTitle());

        currentSong = s;

        if (isPaused) {
            JotifyRepository.getInstance().play();

            isPaused = false;
        }
        if (s instanceof JotifySong) {
            try {
                JotifyRepository.getInstance().play(((JotifySong) s).getSpotifyTrack(), File.BITRATE_96, this);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Track track = new Track(s.getSpotifyId());
            try {
                /* We need the files of the track... */
                track = JotifyRepository.getInstance().browse(track);
                JotifyRepository.getInstance().play(track, File.BITRATE_96, this);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
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
    public void playbackFinished(Track track) {
        playerListener.actionPerformed(new ActionEvent(currentSong, 0, PlayerController.PlayEvents.FINISH.toString()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.felixbruns.jotify.player.PlaybackListener#playbackPosition(de.felixbruns
     * .jotify.media.Track, int)
     */
    @Override
    public void playbackPosition(Track track, int position) {
        playerListener.actionPerformed(new ActionEvent(currentSong, 0, PlayerController.PlayerEvents.PROGRESSED
                .toString()));
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

    @Override
    public void seek(int i) {
        try {
            JotifyRepository.getInstance().seek(i);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
