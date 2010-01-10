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
import com.quiltplayer.view.swing.panels.PlaylistPanel;
import com.quiltplayer.view.swing.panels.components.SongLabel;
import com.quiltplayer.view.swing.util.VolumePane;

/**
 * Controller regarding the player.
 * 
 * @author Vlado Palczynski
 */
@Controller
public class PlayerController implements PlayerListener {

    /**
     * Event for pausing song.
     */
    public static final String EVENT_PAUSE_SONG = "pause.song";

    /**
     * Event for resuming song.
     */
    public static final String EVENT_RESUME_SONG = "resume.song";

    /**
     * Event for increasing volume.
     */
    public static final String EVENT_INCREASE_GAIN = "increase.gain";

    /**
     * Event for decreasing volume.
     */
    public static final String EVENT_DECREASE_GAIN = "decrease.gain";

    /**
     * Event for decreasing volume.
     */
    public static final String EVENT_FINISHED_SONG = "finished.song";

    /**
     * Event for changing song.
     */
    public static final String EVENT_CHANGE_SONG = "change.song";

    // private VolumePane volumePane;

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
            playlistPanel.getCurrentSongLabel().setInactive();
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
        }
        else if (PlayerController.EVENT_PAUSE_SONG == cmd) {
            Song s = (Song) e.getSource();
            Component[] components = playlistPanel.getSongLabels();

            for (int i = 0; i < components.length; i++) {
                SongLabel songLabel = (SongLabel) components[i];

                if (songLabel.getSong().equals(s)) {
                    songLabel.setPaused();
                    break;
                }
            }

            playerFactory.pause();
        }
        else if (PlayerController.EVENT_RESUME_SONG == cmd) {
            Song s = (Song) e.getSource();
            Component[] components = playlistPanel.getSongLabels();

            for (int i = 0; i < components.length; i++) {
                SongLabel songLabel = (SongLabel) components[i];

                if (songLabel.getSong().equals(s)) {
                    songLabel.setResumed();
                    break;
                }
            }

            playerFactory.pause();
        }
        else if (PlayerController.EVENT_CHANGE_SONG == cmd) {
            playerFactory.stop();
            playlistPanel.inactivateCurrentSongLabel();
            playList.jumpToSong((Song) e.getSource());
            playerFactory.play(playList.getCurrentSong());
        }
        else if (EVENT_DECREASE_GAIN == cmd) {
            playerFactory.decreaseVolume();
        }
        else if (EVENT_INCREASE_GAIN == cmd) {
            playerFactory.increaseVolume();
        }

        else if (EVENT_FINISHED_SONG == cmd) {
            playlistPanel.inactivateCurrentSongLabel();
            playList.nextSong();
            playerFactory.play(playList.getCurrentSong());
        }

        if (VolumePane.ACTION_CMD == cmd) {
            // TODO player.setVolume(frame.getVolumePane().getValue());
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
