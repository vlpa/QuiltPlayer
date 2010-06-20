package com.quiltplayer.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.core.player.PlayerFactory;
import com.quiltplayer.core.playlist.PlayList;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Song;
import com.quiltplayer.view.swing.SongStatus;
import com.quiltplayer.view.swing.buttons.QSongButton;
import com.quiltplayer.view.swing.panels.UtilityPanel;
import com.quiltplayer.view.swing.panels.controlpanels.ControlPanel;
import com.quiltplayer.view.swing.panels.controlpanels.PlayerControlPanel;

/**
 * Controller regarding the player.
 * 
 * @author Vlado Palczynski
 */
@Controller
public class PlayerController implements PlayerListener {

    private Logger log = Logger.getLogger(PlayerController.class);

    public enum PlayEvents {
        CHANGE, STOP, PAUSE, PLAY, FINISH, PREVIOUS, NEXT, SEEK
    }

    public enum PlayerEvents {
        CHANGED, STOPPED, PAUSEED, PLAYING, FINISHED, PREVIOUS, NEXT, SEEKED, PROGRESSED, PROGRESSES_BYTES, RESUMED
    };

    private Runnable invoker;

    @Autowired
    private PlayerFactory playerFactory;

    @Autowired
    private PlayList playList;

    @Autowired
    private UtilityPanel playlistPanel;

    @Autowired
    private ControlPanel controlPanel;

    @Autowired
    private PlayerControlPanel playerControlPanel;

    // private SongStatus songStatus = new SongStatus();

    public PlayerController() {
        invoker = new Runnable() {
            public void run() {
                playerControlPanel.setProgress(playerFactory.getElapsedTime());
            }
        };
    }

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {
        final String cmd = e.getActionCommand();

        if (PlayerEvents.PROGRESSED.toString() == cmd) {
            SwingUtilities.invokeLater(invoker);
        }
        else if (PlayerEvents.STOPPED.toString() == cmd) {
            log.debug("Player stopped.");

            if (playlistPanel.getCurrentSongLabel() != null)
                playlistPanel.getCurrentSongLabel().setInactive();

            controlPanel.getPlayerControlPanel().setStopped();
        }
        else if (PlayerEvents.PAUSEED.toString() == cmd) {
            log.debug("Player paused.");

            controlPanel.getPlayerControlPanel().setPaused();
        }
        else if (PlayerEvents.RESUMED.toString() == cmd) {
            log.debug("Player resumed.");

            controlPanel.getPlayerControlPanel().setPlaying();
        }
        else if (PlayerEvents.PLAYING.toString() == cmd) {
            log.debug("Player playing.");

            Song song = (Song) e.getSource();

            Component[] components = playlistPanel.getSongLabels();

            for (int i = 0; i < components.length; i++) {
                QSongButton songLabel = (QSongButton) components[i];

                if (songLabel.getSong().equals(song)) {
                    songLabel.setActive();
                    playlistPanel.setCurrentSongLabel(songLabel);

                    break;
                }
            }
            controlPanel.getPlayerControlPanel().setPlaying();
            playerControlPanel.changeSong(song);

        }

        /* Play events, from UI */

        else if (PlayEvents.STOP.toString() == cmd) {
            log.debug("Stop requested...");

            playerFactory.stop();
        }
        else if (PlayEvents.PAUSE.toString().equals(cmd)) {
            log.debug("Pause requested...");

            playerFactory.pause();
        }
        else if (PlayEvents.PLAY.toString() == cmd) {
            log.debug("Play requested...");

            playerFactory.play(playList.getCurrentSong());
        }
        else if (PlayEvents.SEEK.toString() == cmd) {
            log.debug("Seek requested...");

            final int i = (Integer) e.getSource();
            playerFactory.seek(i);
        }
        else if (PlayEvents.CHANGE.toString() == cmd) {
            playlistPanel.inactivateCurrentSongLabel();
            playList.jumpToSong((Song) e.getSource());
            playerFactory.play(playList.getCurrentSong());
        }
        else if (PlayEvents.NEXT.toString() == cmd) {
            log.debug("Next requested...");

            playlistPanel.inactivateCurrentSongLabel();
            playList.nextSong();
            playerFactory.play(playList.getCurrentSong());
        }
        else if (PlayEvents.PREVIOUS.toString() == cmd) {
            log.debug("Previous requested...");

            playlistPanel.inactivateCurrentSongLabel();
            playList.prevSong();
            playerFactory.play(playList.getCurrentSong());
        }
        else if (PlayEvents.FINISH.toString() == cmd) {
            playlistPanel.inactivateCurrentSongLabel();
            playList.nextSong();
            playerFactory.play(playList.getCurrentSong());
        }
    }

    /**
     * Denna metoden finns i PlayerListener
     */
    public void newAlbum(final Album a) {
        // frame.getPlayListPane().setAlbum(a);
    }

    public void newSong(final Song s) {
        // frame.getPlaylistPanel().setCurrentSong(s);
    }

    public void songStatus(final SongStatus ss) {
        // songStatus.setElapsed(ss.getElapsed());
        // SwingUtilities.invokeLater(invoker);
    }
}
