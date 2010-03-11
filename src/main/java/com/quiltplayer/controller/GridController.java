package com.quiltplayer.controller;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.ActiveView;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.listeners.GridListener;

/**
 * @author Vlado Palczynski
 */

@Controller()
public class GridController implements GridListener {

    private Logger log = Logger.getLogger(GridController.class);

    @Autowired
    private QuiltPlayerFrame frame;

    public static final String EVENT_INCREASE_GRID = "increase.grid";

    public static final String EVENT_DECREASE_GRID = "decrease.grid";

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {

        ActiveView view = frame.getCurrentView();

        if (EVENT_INCREASE_GRID == e.getActionCommand()) {
            if (view == ActiveView.QUILT)
                Configuration.getInstance().getGridProperties().setQuiltGrid(
                        Configuration.getInstance().getGridProperties().getQuiltGrid() + 1);
            else if (view == ActiveView.ALBUMS)
                Configuration.getInstance().getGridProperties().setAlbumsGrid(
                        Configuration.getInstance().getGridProperties().getAlbumsGrid() + 1);
            else if (view == ActiveView.ARTISTS)
                Configuration.getInstance().getGridProperties().setArtistGrid(
                        Configuration.getInstance().getGridProperties().getArtistGrid() + 1);
        }
        else if (EVENT_DECREASE_GRID == e.getActionCommand()) {
            if (view == ActiveView.QUILT)
                Configuration.getInstance().getGridProperties().setQuiltGrid(
                        Configuration.getInstance().getGridProperties().getQuiltGrid() - 1);
            else if (view == ActiveView.ALBUMS)
                Configuration.getInstance().getGridProperties().setAlbumsGrid(
                        Configuration.getInstance().getGridProperties().getAlbumsGrid() - 1);
            else if (view == ActiveView.ARTISTS)
                Configuration.getInstance().getGridProperties().setArtistGrid(
                        Configuration.getInstance().getGridProperties().getArtistGrid() - 1);
        }

        Configuration.getInstance().storeConfiguration();

        frame.updateUI();
    }
}
