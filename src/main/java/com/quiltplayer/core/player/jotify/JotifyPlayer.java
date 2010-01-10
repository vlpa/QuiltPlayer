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

import de.felixbruns.jotify.media.Album;
import de.felixbruns.jotify.media.Artist;
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

    @Override
    public void decreaseVolume() {
        // TODO Auto-generated method stub

    }

    @Override
    public long getElapsedTime() {
        return (long) jotifyRepository.getInstance().position() * 1000000;
    }

    @Override
    public void increaseVolume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        if (!isPaused) {
            jotifyRepository.getInstance().pause();

            // actionListener.actionPerformed(new ActionEvent(currentSong, 0,
            // PlayerController.EVENT_PAUSE_SONG));

            isPaused = true;
        }
        else {
            jotifyRepository.getInstance().play();

            // actionListener.actionPerformed(new ActionEvent(currentSong, 0,
            // PlayerController.EVENT_RESUME_SONG));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#play(com.quiltplayer.model.Song)
     */
    @Override
    public void play(Song s) {
        log.debug("Initializing play for spotify song:" + s.getTitle());

        currentSong = s;

        isPaused = false;

        if (s instanceof JotifySong)
            jotifyRepository.getInstance().play(((JotifySong) s).getSpotifyTrack(), this);
        else {
            Artist artist = new Artist(s.getAlbum().getArtist().getSpotifyId(), s.getAlbum()
                    .getArtist().getArtistName().getName());

            Album album = new Album(s.getAlbum().getSpotifyId(), s.getAlbum().getTitle(), artist);

            Track track = new Track(s.getSpotifyId(), s.getTitle(), artist, album);
            // track.addFile(new File(s.getId(), s.getfs.getFileName()));
            jotifyRepository.getInstance().play(track, this);
        }

        playerListener.actionPerformed(new ActionEvent(s, 0, EVENT_PLAYING_NEW_SONG));
        lyricsListener.actionPerformed(new ActionEvent(s, 0, EVENT_PLAYING_NEW_SONG));
    }

    @Override
    public void stop() {
        log.debug("Stopping play...");

        jotifyRepository.getInstance().stop();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.felixbruns.jotify.player.PlaybackListener#playbackFinished(de.felixbruns
     * .jotify.media.Track)
     */
    @Override
    public void playbackFinished(Track track) {
        playerListener.actionPerformed(new ActionEvent(currentSong, 0,
                PlayerController.EVENT_FINISHED_SONG));
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
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.felixbruns.jotify.player.PlaybackListener#playbackStarted(de.felixbruns
     * .jotify.media.Track)
     */
    @Override
    public void playbackStarted(Track track) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.felixbruns.jotify.player.PlaybackListener#playbackStopped(de.felixbruns
     * .jotify.media.Track)
     */
    @Override
    public void playbackStopped(Track track) {

    }
}
