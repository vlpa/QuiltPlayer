package com.quiltplayer.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.core.player.PlayerFactory;
import com.quiltplayer.core.repo.ArtistRepository;
import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.core.storage.Storage;
import com.quiltplayer.internal.id3.Id3Extractor;
import com.quiltplayer.internal.id3.Id3Modifier;
import com.quiltplayer.internal.id3.model.Id3DataModel;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.model.ArtistName;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.StringId;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.listeners.EditAlbumListener;
import com.quiltplayer.view.swing.panels.PlaylistPanel;
import com.quiltplayer.view.swing.panels.controlpanels.AlbumControlPanel;
import com.quiltplayer.view.swing.panels.playlistpanels.EditPlaylistPanel;

/**
 * Controller for editing ID3s.
 * 
 * @author Vlado Palczynski
 */
@Controller
public class EditAlbumController implements EditAlbumListener {

    private Logger log = Logger.getLogger(EditAlbumController.class);

    public static final String EVENT_DELETE_ALBUM = "delete.album";

    @Autowired
    private Id3Extractor id3Extractor;

    @Autowired
    private Id3Modifier id3Modifier;

    @Autowired
    private Storage storage;

    @Autowired
    private ArtistStorage artistStorage;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private PlayerFactory playerFactory;

    @Autowired
    private PlaylistPanel playlistPanel;

    @Autowired
    private AlbumControlPanel albumControlPanel;

    @Autowired
    private QuiltPlayerFrame frame;

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {
        String cmd = e.getActionCommand();

        if (PlaylistPanel.EVENT_UPDATE_ALBUM_ID3 == e.getActionCommand()) {
            if (frame.playlistPanelVisible && playlistPanel.mode == PlaylistPanel.Mode.EDIT) {
                frame.toggleAlbumView();
                playlistPanel.mode = PlaylistPanel.Mode.HIDDEN;
                albumControlPanel.updateSingleTab(null);
            }
            else if (frame.playlistPanelVisible == false) {
                playlistPanel.viewEditPanel();
                frame.toggleAlbumView();
                albumControlPanel.updateSingleTab(AlbumControlPanel.Buttons.EDIT);
            }
            else {
                playlistPanel.viewEditPanel();
                albumControlPanel.updateSingleTab(AlbumControlPanel.Buttons.EDIT);
            }
        }
        else if (EditPlaylistPanel.SAVE == cmd) {
            List<Object> l = (List<Object>) e.getSource();

            Album album = (Album) l.get(0);
            Id3DataModel model = (Id3DataModel) l.get(1);

            if (!model.getArtistName().equals(album.getArtist().getArtistName().getNameForSearches())
                    || !model.getAlbumTitle().equals(album.getTitle())) {

                log.debug("Updating ID3 information.");

                modifyID3Tags(album, model);
            }

            // Check changed artist name
            if (!model.getArtistName().equals(album.getArtist().getArtistName().getNameForSearches())) {
                changedArtistName(album, model);
            }

            if (!model.getAlbumTitle().equals(album.getTitle())) {
                changeAlbumName(album, model);
            }
        }
        else if (EVENT_DELETE_ALBUM == cmd) {
            log.debug("Deleting album");

            playerFactory.stop();
            playerFactory.removeCurrentSong();

            Album album = (Album) e.getSource();
            Artist artist = album.getArtist();
            artist.removeAlbum(album);

            storage.delete(album);
        }
    }

    private void changeAlbumName(Album album, Id3DataModel model) {
        Artist artist = album.getArtist();
        artist.removeAlbum(album);

        Album newAlbum = storage.getAlbum(new StringId(model.getAlbumTitle()));

        if (newAlbum == null) {
            log.debug("Album does not exist, rename current...");

            album.setId(new StringId(model.getAlbumTitle()));
            album.setTitle(model.getAlbumTitle());

            artist.addAlbum(album);
        }
        else {
            log.debug("Album did exist, moving songs...");

            for (Song song : album.getSongCollection().getSongs())
                newAlbum.addSong(song);

            storage.delete(album);
        }
    }

    private void changedArtistName(Album album, Id3DataModel model) {
        Artist oldArtist = album.getArtist();
        oldArtist.removeAlbum(album);

        if (!oldArtist.hasAlbums())
            artistStorage.delete(oldArtist);

        Artist newArtist = artistStorage.getArtist(new StringId(model.getArtistName()));

        if (newArtist == null) {
            newArtist = artistStorage.createArtist(new StringId(model.getArtistName()));
            newArtist.setArtistName(new ArtistName(model.getArtistName()));
        }

        newArtist.addAlbum(album);

        artistRepository.rebuildArtistCache(artistStorage.getArtists());
    }

    /**
     * Make the updates to the songs.
     * 
     * @param album
     * @param model
     */
    private void modifyID3Tags(Album album, Id3DataModel model) {
        List<File> songFiles = new ArrayList<File>();

        if (album.getType() == Album.TYPE_FILE) {

            for (Song song : album.getSongCollection().getSongs()) {
                songFiles.add(new File(song.getPath()));
            }

            Collection<Id3DataModel> list = id3Extractor.extractId3Tags(songFiles);

            for (Id3DataModel tmp : list) {
                tmp.setArtistName(model.getArtistName());
                tmp.setAlbumTitle(model.getAlbumTitle());
            }

            try {
                id3Modifier.modifyId3Tags(list);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if (album.getType() == Album.TYPE_SPOTIFY) {
            for (Song song : album.getSongCollection().getSongs()) {
                song.getAlbum().setTitle(model.getAlbumTitle());
            }
        }
    }
}