package com.quiltplayer.internal.id3;

import java.io.File;
import java.util.Collection;

import com.quiltplayer.internal.id3.model.DataStorage;
import com.quiltplayer.internal.id3.model.Id3DataModel;

/**
 * Interface for id3 extracting methods.
 * 
 * @author Vlado Palczynski
 */
public interface Id3Extractor {

    /**
     * Extract all ID3 tags from path and stores them in your data storage.
     */
    void extractId3Tags(Collection<File> files, DataStorage storage);

    /**
     * Extract information from a list of files.
     * 
     * @param album
     *            the album to extract.
     * @return List of the ID3 data model.
     */
    Collection<Id3DataModel> extractId3Tags(Collection<File> files);
}