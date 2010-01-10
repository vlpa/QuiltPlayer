package com.quiltplayer.controller;

import java.awt.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.ActiveView;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.listeners.ControlPanelListener;
import com.quiltplayer.view.swing.panels.ControlPanel;
import com.quiltplayer.view.swing.panels.ControlPanel.Tab;

/**
 * Controller for the control panel.
 * 
 * @author Vlado Palczynski
 */
@Controller
public class ControlPanelController implements ControlPanelListener {

    public static final String EVENT_VIEW_CONFIGURATION = "view.configuration";

    public static final String EVENT_INCREASE_GRID = "increase.grid";

    public static final String EVENT_DECREASE_GRID = "decrease.grid";

    @Autowired
    private QuiltPlayerFrame frame;

    @Autowired
    private ControlPanel controlPanel;

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (ControlPanel.EVENT_VIEW_ARTIST == actionCommand) {
            controlPanel.updateTab(Tab.ARTISTS);
            frame.updateUI(ActiveView.ALFABETIC_ARTISTS_VIEW);
        }
        else if (ControlPanel.EVENT_ALBUM_QUILT == actionCommand) {
            controlPanel.updateTab(Tab.QUILT);
            frame.updateUI(ActiveView.QUILT_VIEW);
        }
        else if (ControlPanel.EVENT_VIEW_SEARCH == actionCommand) {
            controlPanel.updateTab(Tab.SEARCH);
            frame.updateUI(ActiveView.SEARCH_VIEW);
        }
        else if (ControlPanel.EVENT_VIEW_CONFIGURATION == actionCommand) {
            controlPanel.updateTab(Tab.CONFIGURATION);
            frame.updateUI(ActiveView.CONFIGURATION_VIEW);
        }
        else if (ControlPanel.EVENT_VIEW_ABOUT == actionCommand) {
            controlPanel.updateTab(Tab.NONE);
            frame.updateUI(ActiveView.ABOUT_VIEW);
        }
        else if (EVENT_INCREASE_GRID == actionCommand) {
            increaseGrid();
            frame.updateUI();
        }
        else if (EVENT_DECREASE_GRID == actionCommand) {
            decreaseGrid();
            frame.updateUI();
        }
    }

    private void increaseGrid() {
        if (frame.getCurrentView().equals(ActiveView.QUILT_VIEW)) {
            Configuration.getInstance().setQuiltColumns(
                    Configuration.getInstance().getQuiltColumns() + 1);
        }
        else if (frame.getCurrentView().equals(ActiveView.ALFABETIC_ARTISTS_VIEW)) {
            Configuration.getInstance().setArtistColumns(
                    Configuration.getInstance().getArtistColumns() + 1);
        }
        else if (frame.getCurrentView().equals(ActiveView.ALBUM_VIEW)) {
            Configuration.getInstance().setAlbumColumns(
                    Configuration.getInstance().getAlbumColumns() + 1);
        }

        Configuration.getInstance().storeConfiguration();
    }

    public void decreaseGrid() {
        if (frame.getCurrentView().equals(ActiveView.QUILT_VIEW)) {
            Configuration.getInstance().setQuiltColumns(
                    Configuration.getInstance().getQuiltColumns() - 1);
        }
        else if (frame.getCurrentView().equals(ActiveView.ALFABETIC_ARTISTS_VIEW)) {
            Configuration.getInstance().setArtistColumns(
                    Configuration.getInstance().getArtistColumns() - 1);
        }
        else if (frame.getCurrentView().equals(ActiveView.ALBUM_VIEW)) {
            Configuration.getInstance().setAlbumColumns(
                    Configuration.getInstance().getAlbumColumns() - 1);
        }

        Configuration.getInstance().storeConfiguration();
    }
}