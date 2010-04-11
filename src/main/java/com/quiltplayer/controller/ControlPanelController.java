package com.quiltplayer.controller;

import java.awt.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.view.swing.ActiveView;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.listeners.ControlPanelListener;
import com.quiltplayer.view.swing.panels.MainTabs;
import com.quiltplayer.view.swing.panels.PlaylistPanel;
import com.quiltplayer.view.swing.panels.controlpanels.AlbumControlPanel;
import com.quiltplayer.view.swing.panels.controlpanels.ControlPanel;
import com.quiltplayer.view.swing.window.Keyboard;

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

    public static final String EVENT_VIEW_SONGS = "view.playlist";

    public static final String EVENT_VIEW_WIKI = "view.wiki";

    public static final String EVENT_VIEW_COVERS = "view.covers";

    @Autowired
    private QuiltPlayerFrame frame;

    @Autowired
    private ControlPanel controlPanel;

    @Autowired
    private Keyboard keyboardPanel;

    @Autowired
    private AlbumControlPanel albumControlPanel;

    @Autowired
    private PlaylistPanel playlistPanel;

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (EVENT_VIEW_ARTIST == actionCommand) {
            controlPanel.updateTab(MainTabs.ARTISTS);
            albumControlPanel.updateTab(null);
            frame.updateUI(ActiveView.ARTISTS);
        }
        else if (ControlPanel.EVENT_QUILT == actionCommand) {
            controlPanel.updateTab(MainTabs.QUILT);
            albumControlPanel.updateTab(null);
            frame.updateUI(ActiveView.QUILT);
        }
        else if (EVENT_VIEW_WIKI == actionCommand) {
            albumControlPanel.updateTab(MainTabs.WIKI);
            controlPanel.updateTab(null);
            frame.updateUI(ActiveView.WIKI);
        }
        else if (EVENT_VIEW_COVERS == actionCommand) {
            albumControlPanel.updateTab(MainTabs.IMAGES);
            albumControlPanel.updateSingleTab(null);
            controlPanel.updateTab(null);
            frame.removeAlbumView();
            frame.updateUI(ActiveView.COVERS);
        }
        else if (ControlPanel.EVENT_VIEW_SEARCH == actionCommand) {
            controlPanel.updateTab(MainTabs.SEARCH);
            albumControlPanel.updateTab(null);
            frame.updateUI(ActiveView.SEARCH);
        }
        else if (EVENT_VIEW_CONFIGURATION == actionCommand) {
            controlPanel.updateTab(MainTabs.CONFIGURATION);
            albumControlPanel.updateTab(null);
            frame.updateUI(ActiveView.CONFIGURATION);
        }
        else if (ControlPanel.EVENT_VIEW_ABOUT == actionCommand) {
            albumControlPanel.updateTab(null);
            controlPanel.updateTab(MainTabs.ABOUT);
            frame.updateUI(ActiveView.ABOUT);
        }
        else if (EVENT_VIEW_LYRICS == actionCommand) {
            albumControlPanel.updateSingleTab(AlbumControlPanel.Buttons.LYRICS);
            playlistPanel.viewLyricsPanel();
        }
        else if (EVENT_VIEW_SONGS == actionCommand) {

            if (frame.playlistPanelVisible && playlistPanel.mode == PlaylistPanel.Mode.SONG) {
                frame.toggleAlbumView();
                playlistPanel.mode = PlaylistPanel.Mode.HIDDEN;
                albumControlPanel.updateSingleTab(null);
            }
            else if (frame.playlistPanelVisible == false) {
                playlistPanel.viewAlbumPanel();
                frame.toggleAlbumView();
                albumControlPanel.updateSingleTab(AlbumControlPanel.Buttons.PLAYLIST);
            }
            else {
                playlistPanel.viewAlbumPanel();
                albumControlPanel.updateSingleTab(AlbumControlPanel.Buttons.PLAYLIST);
            }
        }
        else if (EVENT_VIEW_KEYBOARD == actionCommand) {
            if (keyboardPanel.isVisible())
                keyboardPanel.setVisible(false);
            else
                keyboardPanel.setVisible(true);
        }
    }
}