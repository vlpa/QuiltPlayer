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
import com.quiltplayer.view.swing.buttons.QSongButton;
import com.quiltplayer.view.swing.panels.PlaylistPanel;
import com.quiltplayer.view.swing.panels.controlpanels.ControlPanel;

/**
 * Controller regarding the player.
 * 
 * @author Vlado Palczynski
 */
@Controller
public class PlayerController implements PlayerListener {

    public enum PlayerSongEvents {
        CHANGE, STOP, PAUSE, PLAY, FINISHED, PREVIOUS, NEXT
    }

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
                QSongButton songLabel = (QSongButton) components[i];

                if (songLabel.getSong().equals(s)) {
                    songLabel.setActive();
                    playlistPanel.setCurrentSongLabel(songLabel);

                    break;
                }
            }
            controlPanel.getPlayerControlPanel().setPlaying();
        }
        else if (PlayerSongEvents.STOP.toString() == cmd) {
            playerFactory.stop();
        }
        else if (PlayerSongEvents.PAUSE.toString().equals(cmd)) {
            playerFactory.pause();
        }
        else if (PlayerSongEvents.PLAY.toString() == cmd) {
            playerFactory.play(playList.getCurrentSong());
        }
        else if (PlayerSongEvents.CHANGE.toString() == cmd) {
            playlistPanel.inactivateCurrentSongLabel();
            playerFactory.stop();
            playList.jumpToSong((Song) e.getSource());
            playerFactory.play(playList.getCurrentSong());
        }
        else if (PlayerSongEvents.NEXT.toString() == cmd) {
            playlistPanel.inactivateCurrentSongLabel();
            playList.nextSong();
            playerFactory.play(playList.getCurrentSong());
        }
        else if (PlayerSongEvents.PREVIOUS.toString() == cmd) {
            playlistPanel.inactivateCurrentSongLabel();
            playList.prevSong();
            playerFactory.play(playList.getCurrentSong());
        }
        else if (PlayerSongEvents.FINISHED.toString() == cmd) {
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
