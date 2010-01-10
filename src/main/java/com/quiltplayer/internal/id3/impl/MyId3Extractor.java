package com.quiltplayer.internal.id3.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
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

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.id3utils.Id3Extractor#extractFile(java.io.File)
     */
    public Id3DataModel extractFile(File file) {
        if (file.isDirectory())
            throw new IllegalArgumentException("File cannot be directory");

        return extract(file);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.id3utils.Id3Extractor#extractFile(java.io.File)
     */
    public Id3DataModel extractFile(File file, DataStorage storage) {
        this.storage = storage;

        return extract(file);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.id3utils.Id3Extractor#getFiles(java.io.File)
     */
    public File[] getFiles(File path) {
        return findFiles(path);
    }

    private DataStorage storage;

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

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.extractor.id3.Id3Extractor#extractId3Tags()
     */
    public final List<Id3DataModel> extractId3Tags(File path) {
        List<Id3DataModel> l = new ArrayList<Id3DataModel>();

        File[] files = findFiles(path);

        for (File file : files) {
            l.add(extract(file));
        }

        return l;
    }

    private File[] findFiles(File root) {
        FileFilter filter = new FileFilter() {
            public boolean accept(File f) {
                return f.isFile()
                        && (f.getName().toLowerCase().endsWith(".mp3") || f.getName().toLowerCase()
                                .endsWith(".ogg"));
            }
        };

        Collection<File> files = listFiles(root, filter);

        File[] arr = new File[files.size()];
        return files.toArray(arr);

    }

    private Collection<File> listFiles(File directory, FileFilter filter) {
        // List of files / directories
        List<File> files = new ArrayList<File>();

        // Get files / directories in the directory
        File[] entries = directory.listFiles();

        // Go over entries
        if (entries != null) {
            for (File entry : entries) {
                if (filter.accept(entry))
                    files.add(entry);

                if (entry.isDirectory())
                    files.addAll(listFiles(entry, filter));
            }
        }

        return files;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.id3utils.Id3Extractor#extractId3Tags(java.io.File,
     * com.quiltplayer.id3utils.model.DataStorage)
     */
    public void extractId3Tags(File path, DataStorage storage) {

        this.storage = storage;

        List<Id3DataModel> l = new ArrayList<Id3DataModel>();
        traverse(path, l);
    }

    private final void traverse(final File f, final List<Id3DataModel> l) {
        if (f.isDirectory()) {
            onDirectory(f);

            final File[] childs = f.listFiles();
            for (File child : childs) {
                traverse(child, l);
            }
            return;
        }
        onFile(f, l);
    }

    /**
     * @param directory
     *            the directory to set.
     */
    private void onDirectory(final File directory) {
        // Could be used for information gathering and publishing
        log.debug("Scanning directory: " + directory.getName());
    }

    /**
     * @param file
     *            the file to extract.
     */
    private void onFile(final File file, List<Id3DataModel> l) {
        // Could also be used for information gathering and publishing
        if (file.getName().toLowerCase().endsWith(".mp3")) {
            l.add(extract(file));
        }
    }

    /**
     * @param file
     *            the file to extract from.
     * @return Id3DataModel
     */
    private Id3DataModel extract(final File file) {
        String albumTitle = null;
        String artistName = null;
        String songTitle = null;
        Number trackNumber = null;

        // Extract information from ID3-tag
        try {
            MusicMetadataSet srcSet = myID3.read(file); // read metadata

            // perhaps no metadata
            if (srcSet == null) {
                return null;
            }

            IMusicMetadata metadata = srcSet.getSimplified();

            if (srcSet.id3v2Raw != null) {
                artistName = srcSet.id3v2Raw.values.getArtist();
                albumTitle = srcSet.id3v2Raw.values.getAlbum();
                songTitle = srcSet.id3v2Raw.values.getSongTitle();
                trackNumber = srcSet.id3v2Raw.values.getTrackNumber();
            }
            else if (srcSet.id3v1Raw != null) {
                artistName = srcSet.id3v1Raw.values.getArtist();
                albumTitle = srcSet.id3v1Raw.values.getAlbum();
                songTitle = srcSet.id3v1Raw.values.getSongTitle();
                trackNumber = srcSet.id3v1Raw.values.getTrackNumber();
            }
            else {
                artistName = metadata.getArtist();
                albumTitle = metadata.getAlbum();
                songTitle = metadata.getSongTitle();
                trackNumber = metadata.getTrackNumber();
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

            return model;

        }
        catch (IOException e) {
            /*
             * Could be strange chars in the filepath. Could be published in the application.
             */
            unsuccessfull.add(file);
            log.error(e.getMessage());

            return null;
        }
        catch (Exception e) {
            unsuccessfull.add(file);
            log.error(e.getMessage());

            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.id3utils.Id3Extractor#extractByAlbum(java.util.List)
     */
    public List<Id3DataModel> extractByAlbum(List<File> files) {

        List<Id3DataModel> l = new ArrayList<Id3DataModel>();

        for (File file : files) {
            l.add(extract(file));
        }

        return l;
    }
}