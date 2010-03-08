package com.quiltplayer.core.scanner.tasks;

import java.io.File;
import java.util.Collection;

import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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

public class FileScannerTask extends SwingWorker<Integer, Void> {

    private static Logger log = Logger.getLogger(FileScannerTask.class);

    private Id3Extractor id3Extractor;

    private Storage storage;

    private ArtistStorage artistStorage;

    private Collection<File> files;

    public FileScannerTask(Id3Extractor id3Extractor, Storage storage, ArtistStorage artistStorage) {
        this.id3Extractor = id3Extractor;
        this.storage = storage;
        this.artistStorage = artistStorage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.SwingWorker#doInBackground()
     */
    @Override
    protected Integer doInBackground() throws Exception {

        getLengthOfTask();

        id3Extractor.extractId3Tags(files, dataStorage);

        return null;
    }

    private DataStorage dataStorage = new DataStorage() {

        /*
         * (non-Javadoc)
         * 
         * @see com.quiltplayer.internal.id3.model.DataStorage#progress()
         */
        @Override
        public void progress(int count) {
            Double d = new Double(count);
            double f = (d / files.size());
            setProgress(((Double) (f * 100)).intValue());
        }

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

    @SuppressWarnings("unchecked")
    private int getLengthOfTask() {
        File root = new File(Configuration.getInstance().getFolderProperties().getMusicPath());

        Collection files = FileUtils.listFiles(root, new String[] { "mp3", "MP3" }, true);

        this.files = files;

        return files.size();
    }
}
