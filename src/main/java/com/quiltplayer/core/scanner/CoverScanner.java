package com.quiltplayer.core.scanner;

import com.quiltplayer.model.Album;

/**
 * Scans for album covers.
 * 
 * @author Vlado Palczynski
 */
public interface CoverScanner extends Runnable {
    /**
     * Initialize a full scanning of albums/songs in the music paths existing.
     */
    void scanCovers();

    /**
     * Scan covers for one album.
     */
    void scanCovers(Album album);

    /**
     * Cancels an ongoing scan.
     */
    void cancelScanCovers();
}
