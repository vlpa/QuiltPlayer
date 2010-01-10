package com.quiltplayer.core.scanner.impl;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.core.scanner.Id3Scanner;
import com.quiltplayer.core.scanner.ScanningEvent;
import com.quiltplayer.core.scanner.ScanningEvent.Scanner;
import com.quiltplayer.core.scanner.ScanningEvent.Status;
import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.core.storage.Storage;
import com.quiltplayer.internal.id3.Id3Extractor;
import com.quiltplayer.internal.id3.model.DataStorage;
import com.quiltplayer.internal.id3.model.Id3DataModel;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.model.ArtistName;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.StringId;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.listeners.ScanningListener;

/**
 * Default implementation of Id3Scanner.
 * 
 * @author Vlado Palczynski
 */
@Component
public class DefaultId3Scanner implements Id3Scanner, Runnable {
    /**
     * The logger.
     */
    private static Logger log = Logger.getLogger(DefaultId3Scanner.class);

    private Thread t;
    /**
     * The id3 extractor.
     */
    @Autowired
    private Id3Extractor id3Extractor;

    @Autowired
    private Storage storage;

    @Autowired
    private ArtistStorage artistStorage;

    /**
     * The scanning listener.
     */
    @Autowired
    private ScanningListener listener;

    private DataStorage dataStorage = new DataStorage() {
        /*
         * (non-Javadoc)
         * 
         * @see com.quiltplayer.id3utils.model.DataStorage#store(com.quiltplayer.
         * id3utils.model.Id3DataModel)
         */
        @Override
        public void store(Id3DataModel model) {
            if (model != null && StringUtils.isNotEmpty(model.getAlbumTitle())
                    && StringUtils.isNotEmpty(model.getArtistName())
                    && StringUtils.isNotBlank(model.getSongTitle())) {
                StringId artistId = new StringId(model.getArtistName());
                Artist artist = artistStorage.getArtist(artistId);

                if (artist == null) {
                    log.debug("Artist not found, creating...");
                    artist = artistStorage.createArtist(new StringId(model.getArtistName()));
                }

                // TODO this sets the same value on name twice
                artist.setArtistName(new ArtistName(model.getArtistName()));

                StringId albumId = new StringId(model.getAlbumTitle());
                Album album = storage.getAlbum(albumId);

                if (album == null) {
                    log.debug("Album not found, creating...");
                    album = storage.createAlbum(albumId);
                    artist.addAlbum(album);
                }

                /* If album exists but not on this artist */
                if (!artist.hasAlbum(album))
                    artist.addAlbum(album);

                File file = model.getPath();

                album.setTitle(model.getAlbumTitle());

                album.setLastModified(file.getParentFile().lastModified());
                album.setArtist(artist);
                album.setType(Album.TYPE_FILE);
                album.setSpotifyId(null);

                StringId songId = new StringId(model.getAlbumTitle() + model.getSongTitle());
                Song song = storage.getSong(album.getTitle(), songId);

                if (song == null) {
                    song = storage.createSong(songId);
                    album.addSong(song);
                }

                song.setPath(file.getAbsolutePath());
                song.setFileName(file.getName());
                song.setTitle(model.getSongTitle());
                song.setTrackNumber(model.getTrackNumber());
                song.setSpotifyId(null);
                song.setType(Song.TYPE_FILE);
            }

        }
    };

    /*
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        log.debug("Id3 colection scanning thread started...");

        // Extract ID3-information from songs.
        listener.scannerEvent(new ScanningEvent(Status.STARTED, Scanner.ID3));

        id3Extractor.extractId3Tags(new File(Configuration.getInstance().getMusicPath()),
                dataStorage);

        listener.scannerEvent(new ScanningEvent(Status.DONE, Scanner.ID3));

        log.debug("Id3 colection scanning finished.");
    }

    /*
     * @see org.coverrock.CollectionScanner#scanCollection()
     */
    @Override
    public void scanCollection() {
        t = new Thread(this);
        t.start();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.core.scanner.CollectionScanner#cancelScanCollection()
     */
    @SuppressWarnings("deprecation")
    @Override
    public void cancelScanCollection() {
        t.stop();

        listener.scannerEvent(new ScanningEvent(Status.DONE, Scanner.ID3));

        log.debug("Id3 colection scanning stopped.");
    }

    /*
     * @see org.coverrock.CollectionScanner#updateCollection()
     */
    @Override
    public void updateCollection() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.core.scanner.CollectionScanner#cancelUpdateCollection()
     */
    @Override
    public void cancelUpdateCollection() {
    }
}
