package com.quiltplayer.internal.id3;

import java.io.File;
import java.util.List;

import com.quiltplayer.internal.id3.model.DataStorage;
import com.quiltplayer.internal.id3.model.Id3DataModel;

/**
 * Interface for id3 extracting methods.
 * 
 * @author Vlado Palczynski
 */
public interface Id3Extractor {

    /**
     * 
     * @param file
     * @return
     */
    Id3DataModel extractFile(File file);

    /**
     * 
     * @param file
     * @return
     */
    Id3DataModel extractFile(File file, DataStorage storage);

    /**
     * Extract all ID3 tags from path.
     */
    List<Id3DataModel> extractId3Tags(File path);

    /**
     * Get all music files from a path.
     * 
     * @param path
     *            the directory path.
     * @return File[] of all music files.
     * 
     */
    File[] getFiles(File path);

    /**
     * Extract all ID3 tags from path and stores them in your data storage.
     */
    void extractId3Tags(File path, DataStorage storage);

    /**
     * Extract information from a list of files.
     * 
     * @param album
     *            the album to extract.
     * @return List of the ID3 data model.
     */
    List<Id3DataModel> extractByAlbum(List<File> files);
}