package com.quiltplayer.core.scanner;

/**
 * Scans all the root folder albums id3 tags.
 * 
 * @author Vlado Palczynski
 */
public interface Id3Scanner {
    /**
     * Initialize a full scanning of albums/songs in the music paths existing.
     */
    void scanCollection();

    /**
     * Cancels an ongoing collection scan..
     */
    void cancelScanCollection();

    /**
     * Check for new albums/songs and add them to the collection.
     */
    void updateCollection();

    /**
     * Cancels an ongoing collection update.
     */
    void cancelUpdateCollection();
}
