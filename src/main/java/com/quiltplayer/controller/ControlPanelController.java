package com.quiltplayer.controller;

import java.awt.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.view.swing.ActiveView;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.listeners.ControlPanelListener;
import com.quiltplayer.view.swing.panels.PlaylistPanel;
import com.quiltplayer.view.swing.panels.controlpanels.ControlPanel;
import com.quiltplayer.view.swing.panels.controlpanels.ControlPanel.Tab;
import com.quiltplayer.view.swing.window.KeyboardPanel;

/**
 * Controller for the control panel.
 * 
 * @author Vlado Palczynski
 */
@Controller
public class ControlPanelController implements ControlPanelListener {

    public static final String EVENT_VIEW_CONFIGURATION = "view.configuration";

    public static final String EVENT_VIEW_ARTIST = "view.artist";

    public static final String EVENT_VIEW_KEYBOARD = "view.keyboard";

    public static final String EVENT_VIEW_LYRICS = "view.lyrics";

    public static final String EVENT_VIEW_ALBUM = "view.playlist";

    public static final String EVENT_TOGGLE_ALBUM_VIEW = "toggle.album.view";

    public static final String EVENT_VIEW_WIKI = "view.wiki";

    @Autowired
    private QuiltPlayerFrame frame;

    @Autowired
    private ControlPanel controlPanel;

    @Autowired
    private KeyboardPanel keyboardPanel;

    @Autowired
    private PlaylistPanel playlistPanel;

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (EVENT_VIEW_ARTIST == actionCommand) {
            controlPanel.updateTab(Tab.ARTISTS);
            frame.updateUI(ActiveView.ALFABETIC_ARTISTS);
        }
        else if (ControlPanel.EVENT_ALBUM_QUILT == actionCommand) {
            controlPanel.updateTab(Tab.QUILT);
            frame.updateUI(ActiveView.QUILT);
        }
        else if (EVENT_VIEW_WIKI == actionCommand) {
            controlPanel.updateTab(Tab.WIKI);
            frame.updateUI(ActiveView.WIKI);
        }
        else if (ControlPanel.EVENT_VIEW_SEARCH == actionCommand) {
            controlPanel.updateTab(Tab.SEARCH);
            frame.updateUI(ActiveView.SEARCH);
        }
        else if (EVENT_VIEW_CONFIGURATION == actionCommand) {
            controlPanel.updateTab(Tab.CONFIGURATION);
            frame.updateUI(ActiveView.CONFIGURATION);
        }
        else if (ControlPanel.EVENT_VIEW_ABOUT == actionCommand) {
            controlPanel.updateTab(Tab.NONE);
            frame.updateUI(ActiveView.ABOUT);
        }
        else if (EVENT_VIEW_LYRICS == actionCommand) {
            playlistPanel.viewLyricsPanel();
        }
        else if (EVENT_VIEW_ALBUM == actionCommand) {
            playlistPanel.viewAlbumPanel();
        }
        else if (EVENT_TOGGLE_ALBUM_VIEW == actionCommand) {
            frame.toggleAlbumView();
        }
        else if (EVENT_VIEW_KEYBOARD == actionCommand) {
            if (keyboardPanel.isVisible())
                keyboardPanel.setVisible(false);
            else
                keyboardPanel.setVisible(true);
        }
    }
}