package com.quiltplayer.view.swing.views;

import java.awt.Component;

/**
 * Interface for the artist views.
 * 
 * @author Vlado Palczynski
 */
public interface ArtistView {
    static final String EVENT_CHANGE_ALBUM = "change.album";

    static final String EVENT_VIEW_ARTIST = "view.artist";

    /**
     * Returns the graphical component. This component will be used as Cover viewer/browser by the
     * Coverok framework.
     */
    Component getUI();

    /**
     * Release resources used by this album view.
     */
    void close();
}