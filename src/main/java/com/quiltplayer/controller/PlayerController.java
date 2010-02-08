package com.quiltplayer.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.core.player.Player;
import com.quiltplayer.core.player.PlayerFactory;
import com.quiltplayer.core.playlist.PlayList;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Song;
import com.quiltplayer.view.swing.SongStatus;
import com.quiltplayer.view.swing.panels.ControlPanel;
import com.quiltplayer.view.swing.panels.PlaylistPanel;
import com.quiltplayer.view.swing.panels.components.SongLabel;

/**
 * Controller regarding the player.
 * 
 * @author Vlado Palczynski
 */
@Controller
public class PlayerController implements PlayerListener {

    /**
     * Event for stopping play, should free up audio resources.
     */
    public static final String EVENT_STOP_SONG = "stop.song";

    /**
     * Event for pausing song.
     */
    public static final String EVENT_PAUSE_SONG = "pause.song";

    /**
     * Event for resuming song.
     */
    public static final String EVENT_PLAY_SONG = "play.song";

    /**
     * Event for decreasing volume.
     */
    public static final String EVENT_FINISHED_SONG = "finished.song";

    /**
     * Event for changing song.
     */
    public static final String EVENT_CHANGE_SONG = "change.song";

    public static final String EVENT_NEXT_SONG = "next.song";

    public static final String EVENT_PREVIOUS_SONG = "previous.song";

    /**
     * Swing invoker.
     */
    private Runnable invoker;

    /**
     * The player.
     */
    @Autowired
    private PlayerFactory playerFactory;

    /**
     * The PlayList.
     */
    @Autowired
    private PlayList playList;

    /**
     * The play list.
     */
    @Autowired
    private PlaylistPanel playlistPanel;

    @Autowired
    private ControlPanel controlPanel;

    // private SongStatus songStatus = new SongStatus();

    public PlayerController() {
        invoker = new Runnable() {
            public void run() {
                playlistPanel.progress(playerFactory.getElapsedTime());
            }
        };
    }

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {
        final String cmd = e.getActionCommand();

        if (Player.EVENT_PROGRESS == cmd) {
            SwingUtilities.invokeLater(invoker);
        }
        else if (Player.EVENT_STOPPED_SONG == cmd) {
            if (playlistPanel.getCurrentSongLabel() != null)
                playlistPanel.getCurrentSongLabel().setInactive();

            controlPanel.getPlayerControlPanel().setStopped();
        }
        else if (Player.EVENT_PAUSED_SONG == cmd) {
            controlPanel.getPlayerControlPanel().setPaused();
        }
        else if (Player.EVENT_RESUMED_SONG == cmd) {
            controlPanel.getPlayerControlPanel().setPlaying();
        }
        else if (Player.EVENT_PLAYING_NEW_SONG == cmd) {
            Song s = (Song) e.getSource();

            Component[] components = playlistPanel.getSongLabels();

            for (int i = 0; i < components.length; i++) {
                SongLabel songLabel = (SongLabel) components[i];

                if (songLabel.getSong().equals(s)) {
                    songLabel.setActive();
                    playlistPanel.setCurrentSongLabel(songLabel);

                    break;
                }
            }
            controlPanel.getPlayerControlPanel().setPlaying();
        }
        else if (EVENT_STOP_SONG == cmd) {
            playerFactory.stop();
        }
        else if (EVENT_PAUSE_SONG == cmd) {
            playerFactory.pause();
        }
        else if (EVENT_PLAY_SONG == cmd) {
            playerFactory.pause();
        }
        else if (EVENT_CHANGE_SONG == cmd) {
            playerFactory.stop();
            playlistPanel.inactivateCurrentSongLabel();
            playList.jumpToSong((Song) e.getSource());
            playerFactory.play(playList.getCurrentSong());
        }
        else if (EVENT_NEXT_SONG == cmd) {
            playlistPanel.inactivateCurrentSongLabel();
            playList.nextSong();
            playerFactory.play(playList.getCurrentSong());
        }
        else if (EVENT_PREVIOUS_SONG == cmd) {
            playlistPanel.inactivateCurrentSongLabel();
            playList.prevSong();
            playerFactory.play(playList.getCurrentSong());
        }
        else if (EVENT_FINISHED_SONG == cmd) {
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
