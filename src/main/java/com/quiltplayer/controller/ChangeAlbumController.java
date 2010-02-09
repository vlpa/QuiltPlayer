package com.quiltplayer.controller;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.core.player.PlayerFactory;
import com.quiltplayer.core.playlist.PlayList;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Song;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.panels.PlaylistPanel;

/**
 * Controller for changing albums.
 * 
 * @author Vlado Palczynski
 */
@Controller
public class ChangeAlbumController implements ChangeAlbumListener {

    private Logger log = Logger.getLogger(ChangeAlbumController.class);

    public static final String EVENT_CHANGE_ALBUM = "change.album";

    public static final String EVENT_CHANGE_ALBUM_AND_PLAY_SONG = "change.album.and.song";

    @Autowired
    private PlayerFactory playerFactory;

    @Autowired
    private PlayList playList;

    @Autowired
    private PlaylistPanel playlistPanel;

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (e.getActionCommand().equals(EVENT_CHANGE_ALBUM)) {
            Album album = (Album) e.getSource();

            if (album == null) {
                log.error("Album could not be found, inconsistency in the collection...");
            }

            playerFactory.stopPlay();

            playlistPanel.changeAlbum(album);

            playList.clearPlaylist();

            playList.addSongs(album.getSongCollection().getSongs());

            playerFactory.play(playList.getCurrentSong());
        }
        else if (e.getActionCommand().equals(EVENT_CHANGE_ALBUM_AND_PLAY_SONG)) {
            Album album = (Album) ((Object[]) e.getSource())[0];
            Song song = (Song) ((Object[]) e.getSource())[1];

            if (album == null) {
                log.error("Album could not be found, inconsistency in the collection...");
            }

            playerFactory.stopPlay();

            playlistPanel.changeAlbum(album);

            playList.clearPlaylist();
            playList.addSongs(album.getSongCollection().getSongs());
            playList.jumpToSong(song);
            playerFactory.play(playList.getCurrentSong());
        }
    }
}
