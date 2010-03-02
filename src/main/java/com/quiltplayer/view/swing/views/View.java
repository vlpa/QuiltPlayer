package com.quiltplayer.view.swing.views;

import javax.swing.JComponent;

/**
 * @author Mario Boikov
 */
public interface View {
    static final String EVENT_VIEW_ARTIST = "view.artist";

    /**
     * Returns the graphical component.
     */
    JComponent getUI();

    /**
     * Release resources used by this album view.
     */
    // void close();
}