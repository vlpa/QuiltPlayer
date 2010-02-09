package com.quiltplayer.core.player.simpleplayer;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.PlayerController;
import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.core.player.Player;
import com.quiltplayer.external.lyrics.LyricsListener;
import com.quiltplayer.model.Song;

/**
 * The Player implementation using BasicPlayer.
 * 
 * @author Vlado Palczynski
 */
@Component
public class BasicPlayerPlayer implements BasicPlayerListener, Player {

    private Song currentSong;

    private Integer volume = 5;

    /**
     * The logger.
     */
    private static Logger log = Logger.getLogger(BasicPlayerPlayer.class);

    /**
     * The basic controller.
     */
    private BasicController controller;

    /**
     * The player.
     */
    private BasicPlayer player = null;

    @Autowired
    private PlayerListener playerListener;

    /**
     * Throw event when new song is playing.
     */
    @Autowired
    private LyricsListener lyricsListener;

    /**
     * The time.
     */
    private long time;

    /**
     * Constructor.
     */
    public BasicPlayerPlayer() {
        player = new BasicPlayer();
        player.addBasicPlayerListener(this);

        controller = (BasicController) player;
        this.setController(controller);
    }

    public void play(final Song song) {
        log.debug("Playing...");

        currentSong = song;

        new Thread() {
            /*
             * (non-Javadoc)
             * 
             * @see java.lang.Thread#run()
             */
            @Override
            public void run() {
                try {
                    if (player.getStatus() == BasicPlayer.STOPPED
                            || player.getStatus() == BasicPlayer.UNKNOWN) {
                        {
                            File f = new File(song.getPath());
                            controller.open(f);

                            controller.play();
                        }
                    }
                    else if (player.getStatus() == BasicPlayer.PLAYING) {
                        log.debug("Player status is playing, stopping...");

                        stopPlay();

                        play(song);
                    }
                    else if (player.getStatus() == BasicPlayer.PAUSED) {
                        log.debug("Player status is paused, resuming...");

                        controller.resume();

                        playerListener.actionPerformed(new ActionEvent("", 0, EVENT_RESUMED_SONG));
                    }

                    setVolume();
                }
                catch (BasicPlayerException ex) {
                    log.error(ex.getMessage());
                }
            }
        }.start();

        playerListener.actionPerformed(new ActionEvent(song, 0, EVENT_PLAYING_NEW_SONG));
        lyricsListener.actionPerformed(new ActionEvent(song, 0, EVENT_PLAYING_NEW_SONG));
    }

    public void stopPlay() {
        log.debug("Stopping the player...");

        if (player.getStatus() == BasicPlayer.PAUSED || player.getStatus() == BasicPlayer.PLAYING) {
            try {
                controller.stop();

                playerListener.actionPerformed(new ActionEvent(currentSong, 0, EVENT_STOPPED_SONG));
            }
            catch (BasicPlayerException ex) {
                log.error(ex.getMessage());
            }
        }
    }

    /*
     * @see org.quiltplayer.core.player.Player#pause()
     */
    @Override
    public void pause() {
        log.debug("Pausing the player...");

        try {
            if (player.getStatus() == BasicPlayer.PLAYING) {
                controller.pause();

                playerListener.actionPerformed(new ActionEvent("", 0, EVENT_PAUSED_SONG));
            }
            else if (player.getStatus() == BasicPlayer.PAUSED) {
                controller.resume();

                playerListener.actionPerformed(new ActionEvent("", 0, EVENT_RESUMED_SONG));
            }
        }
        catch (BasicPlayerException ex) {
            log.error(ex.getMessage());
        }
    }

    /*
     * @see org.quiltplayer.core.player.Player#getElapsedTime()
     */
    @Override
    public long getElapsedTime() {
        return time;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    /*
     * @see javazoom.jlgui.basicplayer.BasicPlayerListener#opened(java.lang.Object, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void opened(Object arg0, Map arg1) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javazoom.jlgui.basicplayer.BasicPlayerListener#progress(int, long, byte[],
     * java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void progress(final int arg0, final long milliseconds, final byte[] arg2, Map arg3) {
        time = milliseconds;

        playerListener.actionPerformed(new ActionEvent(currentSong, 0, EVENT_PROGRESS));
    }

    /*
     * @see javazoom.jlgui.basicplayer.BasicPlayerListener#setController(javazoom
     * .jlgui.basicplayer.BasicController)
     */
    @Override
    public void setController(final BasicController arg0) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javazoom.jlgui.basicplayer.BasicPlayerListener#stateUpdated(javazoom.
     * jlgui.basicplayer.BasicPlayerEvent)Volume
     */
    public void stateUpdated(final BasicPlayerEvent arg0) {
        if (arg0.getCode() == BasicPlayerEvent.EOM) {
            log.debug("Event: OEM, jumping to next song...");

            playerListener.actionPerformed(new ActionEvent(currentSong, 0,
                    PlayerController.PlayerSongEvents.FINISHED.toString()));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#decreaseVolume()
     */
    private void setVolume() {
        try {
            player.setGain(volume.doubleValue() / 10);
        }
        catch (BasicPlayerException e) {
            log.error("Cannot set volume:" + e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#decreaseVolume()
     */
    @Override
    public void decreaseVolume() {
        if (volume >= 0) {
            try {
                volume -= 1;
                log.debug("Decreasing volume to: " + volume);
                player.setGain(volume.doubleValue() / 10);
            }
            catch (Exception e) {
                log.error("Cannot set volume:" + e.getMessage());
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.player.Player#increaseVolume()
     */
    @Override
    public void increaseVolume() {
        if (volume < 10) {
            try {
                volume += 1;
                log.debug("Increasing volume to: " + volume);

                player.setGain(volume.doubleValue() / 10);
            }
            catch (Exception e) {
                log.error("Cannot set volume:" + e.getMessage());
            }
        }

    }
}