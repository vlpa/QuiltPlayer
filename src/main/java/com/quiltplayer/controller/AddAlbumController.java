package com.quiltplayer.controller;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.core.scanner.CoverScanner;
import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.core.storage.Storage;
import com.quiltplayer.external.covers.model.LocalImage;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.model.ArtistName;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.StringId;
import com.quiltplayer.model.jotify.JotifyAlbum;
import com.quiltplayer.view.swing.listeners.AddAlbumListener;
import com.quiltplayer.view.swing.panels.UtilityPanel;
import com.quiltplayer.view.swing.panels.controlpanels.AlbumControlPanel;

/**
 * Controller for adding non-ID3 albums.
 * 
 * @author Vlado Palczynski
 */

@Controller()
public class AddAlbumController implements AddAlbumListener {

    public static final String EVENT_ADD_ALBUM = "add.album";

    private Logger log = Logger.getLogger(AddAlbumController.class);

    @Autowired
    private Storage storage;

    @Autowired
    private ArtistStorage artistStorage;

    @Autowired
    private UtilityPanel playlistPanel;

    @Autowired
    private CoverScanner coverScanner;

    @Autowired
    private AlbumControlPanel albumControlPanel;

    /*
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {

        if (EVENT_ADD_ALBUM == e.getActionCommand()) {
            final Album album = playlistPanel.getPlayingAlbum();

            if (album instanceof JotifyAlbum) {

                // TODO Could be a Task...
                new Thread() {

                    /*
                     * (non-Javadoc)
                     * 
                     * @see java.lang.Thread#run()
                     */
                    @Override
                    public void run() {
                        // TODO double code, same as in DefaultID3Scanner.
                        StringId artistId = new StringId(album.getArtist().getArtistName().getName());

                        Artist artist = artistStorage.getArtist(artistId);

                        if (artist == null) {
                            log.debug("Artist " + artistId.getId() + " not found, creating...");
                            artist = artistStorage.createArtist(artistId);
                        }

                        artist.setArtistName(new ArtistName(album.getArtist().getArtistName().getName()));
                        artist.setSpotifyId(album.getArtist().getSpotifyId());

                        StringId albumId;
                        if (album instanceof JotifyAlbum)
                            albumId = album.getId();
                        else
                            albumId = new StringId(album.getTitle());

                        Album quiltAlbum = storage.getAlbum(albumId);

                        if (quiltAlbum == null) {
                            log.debug("Album not found, creating...");
                            quiltAlbum = storage.createAlbum(albumId);
                            artist.addAlbum(quiltAlbum);
                        }

                        if (!artist.hasAlbum(quiltAlbum))
                            artist.addAlbum(quiltAlbum);

                        quiltAlbum.setTitle(album.getTitle());
                        quiltAlbum.setArtist(artist);
                        quiltAlbum.setType(Album.TYPE_SPOTIFY);
                        quiltAlbum.setSpotifyId(album.getSpotifyId());
                        quiltAlbum.setYear(album.getYear());

                        for (Song s : album.getSongCollection().getSongs()) {
                            StringId songId = new StringId(album.getTitle() + s.getTitle());
                            Song song = storage.getSong(album.getTitle(), songId);

                            if (song == null) {
                                song = storage.createSong(songId);
                                quiltAlbum.addSong(song);
                            }

                            song.setFileName(s.getFileName());
                            song.setTitle(s.getTitle());
                            song.setTrackNumber(s.getTrackNumber());
                            song.setSpotifyId(s.getSpotifyId());
                            song.setType(Song.TYPE_SPOTIFY);
                            song.setDuration(s.getDuration());
                        }

                        /* Add cover */
                        if (!album.getImages().isEmpty()) {
                            quiltAlbum.deleteImages();

                            LocalImage image = album.getImages().get(0);
                            image.setType(LocalImage.TYPE_PRIMARY);

                            LocalImage localImage = storage.createLocalImage(quiltAlbum, image.getLargeImage()
                                    .getName(), image);

                            album.getImages().add(localImage);
                        }

                        coverScanner.scanCovers(quiltAlbum);
                        albumControlPanel.update(album);
                    }

                }.start();
            }
        }
    }
}
