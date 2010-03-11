package com.quiltplayer.internal.id3.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.cmc.music.metadata.IMusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;
import org.springframework.stereotype.Component;

import com.quiltplayer.internal.id3.Id3Extractor;
import com.quiltplayer.internal.id3.model.DataStorage;
import com.quiltplayer.internal.id3.model.Id3DataModel;

/**
 * General ID3 extractor using myId3. Error handling is limited but the general idea is that the
 * failed files should be published in the application.
 * 
 * @author Vlado Palczynski
 */
@Component
public class MyId3Extractor implements Id3Extractor {

    private DataStorage storage;

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.id3utils.Id3Extractor#extractFile(java.io.File)
     */
    public void extractId3Tags(Collection<File> files, DataStorage storage) {
        this.storage = storage;

        int i = 0;

        for (File file : files) {
            extract(file);
            storage.progress(++i);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.id3utils.Id3Extractor#extractByAlbum(java.util.List)
     */
    public Collection<Id3DataModel> extractId3Tags(Collection<File> files) {

        List<Id3DataModel> l = new ArrayList<Id3DataModel>();

        for (File file : files) {
            extract(file);
        }

        return l;
    }

    /**
     * Storage for unsuccessful entries.
     */
    private List<File> unsuccessfull = new ArrayList<File>();

    /**
     * Logger.
     */
    private static Logger log = Logger.getLogger(MyId3Extractor.class);

    /**
     * The id3 implementation to use.
     */
    private MyID3 myID3 = new MyID3();

    /**
     * @param file
     *            the file to extract from.
     * @return Id3DataModel
     */
    private void extract(final File file) {
        String albumTitle = null;
        String artistName = null;
        String songTitle = null;
        Number trackNumber = null;

        // Extract information from ID3-tag
        try {
            MusicMetadataSet srcSet = myID3.read(file); // read metadata

            // perhaps no metadata
            if (srcSet == null) {
                return;
            }

            IMusicMetadata metadata = srcSet.getSimplified();

            if (srcSet.id3v2Raw != null) {
                artistName = srcSet.id3v2Raw.values.getArtist();
                albumTitle = srcSet.id3v2Raw.values.getAlbum();
                songTitle = srcSet.id3v2Raw.values.getSongTitle();
                trackNumber = srcSet.id3v2Raw.values.getTrackCount();
            }
            else if (srcSet.id3v1Raw != null) {
                artistName = srcSet.id3v1Raw.values.getArtist();
                albumTitle = srcSet.id3v1Raw.values.getAlbum();
                songTitle = srcSet.id3v1Raw.values.getSongTitle();
                trackNumber = srcSet.id3v1Raw.values.getTrackCount();
            }
            else {
                artistName = metadata.getArtist();
                albumTitle = metadata.getAlbum();
                songTitle = metadata.getSongTitle();
                trackNumber = metadata.getTrackCount();
            }

            Id3DataModel model = new Id3DataModel();
            model.setAlbumTitle(albumTitle);
            model.setArtistName(artistName);
            model.setSongTitle(songTitle);
            model.setTrackNumber(trackNumber);
            model.setPath(file.getAbsoluteFile());

            if (storage != null) {
                storage.store(model);
            }
        }
        catch (Exception e) {
            unsuccessfull.add(file);
            log.error(e.getMessage());
        }
    }
}