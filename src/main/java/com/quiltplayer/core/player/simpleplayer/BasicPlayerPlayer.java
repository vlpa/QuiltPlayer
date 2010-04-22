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
import com.quiltplayer.model.Song;

/**
 * The Player implementation using BasicPlayer.
 * 
 * @author Vlado Palczynski
 */
@Component
public class BasicPlayerPlayer implements BasicPlayerListener, Player {

    private Song currentSong;

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

        try {
            if (player.getStatus() == BasicPlayer.STOPPED || player.getStatus() == BasicPlayer.UNKNOWN) {
                {
                    File f = new File(song.getPath());

                    controller.open(f);
                    controller.play();
                }
            }
            else if (player.getStatus() == BasicPlayer.PLAYING) {
                log.debug("Player status is playing, stopping...");

                controller.stop();

                play(song);
            }
            else if (player.getStatus() == BasicPlayer.PAUSED) {
                log.debug("Player status is paused, resuming...");

                controller.resume();
            }
        }
        catch (BasicPlayerException ex) {
            log.error(ex.getMessage());
        }
    }

    public void stop() {
        log.debug("Stopping the player...");

        if (player.getStatus() == BasicPlayer.PAUSED || player.getStatus() == BasicPlayer.PLAYING) {
            try {
                controller.stop();
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

        playerListener.actionPerformed(new ActionEvent(currentSong, 0, PlayerController.PlayerEvents.PROGRESSED
                .toString()));
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

            playerListener.actionPerformed(new ActionEvent(currentSong, 0, PlayerController.PlayEvents.FINISH
                    .toString()));
        }
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
            player.seek(((Integer) i).longValue());
        }
        catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }
}