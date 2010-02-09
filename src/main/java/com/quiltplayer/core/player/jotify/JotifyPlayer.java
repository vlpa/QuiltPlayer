package com.quiltplayer.core.player.jotify;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.PlayerController;
import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.core.player.Player;
import com.quiltplayer.core.repo.spotify.JotifyRepository;
import com.quiltplayer.external.lyrics.LyricsListener;
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

    @Autowired
    private LyricsListener lyricsListener;

    private Song currentSong;

    private boolean isPaused = false;

    private Thread playThread;

    @Override
    public synchronized long getElapsedTime() {
        return (long) jotifyRepository.getInstance().position() * 1000000;
    }

    @Override
    public synchronized void pause() {
        if (!isPaused) {
            jotifyRepository.getInstance().pause();

            playerListener.actionPerformed(new ActionEvent("", 0, EVENT_PAUSED_SONG));

            isPaused = true;
        }
        else {
            jotifyRepository.getInstance().play();

            playerListener.actionPerformed(new ActionEvent("", 0, EVENT_RESUMED_SONG));

            isPaused = false;
        }
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

        isPaused = false;

        final PlaybackListener pl = this;

        playThread = new Thread() {
            /*
             * (non-Javadoc)
             * 
             * @see java.lang.Thread#run()
             */
            @Override
            public void run() {

                if (s instanceof JotifySong) {
                    jotifyRepository.getInstance().play(((JotifySong) s).getSpotifyTrack(), pl);
                }
                else {
                    Track track = new Track(s.getSpotifyId());
                    track = jotifyRepository.getInstance().browse(track);

                    jotifyRepository.getInstance().play(track, pl);
                }
            }
        };

        playThread.start();

        playerListener.actionPerformed(new ActionEvent(s, 0, EVENT_PLAYING_NEW_SONG));
        lyricsListener.actionPerformed(new ActionEvent(s, 0, EVENT_PLAYING_NEW_SONG));
    }

    @Override
    public synchronized void stop() {
        log.debug("Stopping play...");

        jotifyRepository.getInstance().stop();

        playerListener.actionPerformed(new ActionEvent("", 0, EVENT_STOPPED_SONG));
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
}
