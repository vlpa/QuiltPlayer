package com.quiltplayer.view.swing.views.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.controller.ConfigurationController;
import com.quiltplayer.core.scanner.tasks.FileScannerTask;
import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.core.storage.Storage;
import com.quiltplayer.internal.id3.Id3Extractor;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.buttons.QTab;
import com.quiltplayer.view.swing.listeners.ConfigurationListener;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.panels.controlpanels.ControlPanel;
import com.quiltplayer.view.swing.views.View;
import com.quiltplayer.view.swing.views.impl.configurations.ConfigurationPanel;
import com.quiltplayer.view.swing.views.impl.configurations.ScannerPanel;
import com.quiltplayer.view.swing.views.impl.configurations.SpotifyPanel;

/**
 * Configurations view.
 * 
 * @author Vlado Palczynski
 * 
 */
@org.springframework.stereotype.Component
public class ConfigurationView implements View, ActionListener, PropertyChangeListener {

    private static final String SAVE = "save";

    private enum TAB {
        CONFIGURATION, SPOTIFY, SCANNERS, PROXY
    };

    private Component tab;

    @Autowired
    private ControlPanel controlPanel;

    @Autowired
    private Id3Extractor id3Extractor;

    @Autowired
    private Storage storage;

    @Autowired
    private ArtistStorage artistStorage;

    @Autowired
    private SpotifyPanel spotifyPanel;

    @Autowired
    private ScannerPanel scannerPanel;

    @Autowired
    private ConfigurationPanel configurationPanel;

    private QTab proxy;

    private QTab spotify;

    private QTab music;

    private QTab configuration;

    private JFileChooser fc;

    /**
     * Event to start updating collection.
     */
    public static final String EVENT_UPDATE_COLLECTION = "update.collection";

    /**
     * Event to toggle full screen.
     */
    public static final String EVENT_TOGGLE_FULLSCREEN = "toggle.fullscreen";

    /**
     * Event to cancel update collection.
     */
    public static final String EVENT_CANCEL_UPDATE_COLLECTION = "cancel.update.collection";

    /**
     * Event to start scanning covers,
     */
    public static final String EVENT_SCAN_COVERS = "scan.covers";

    /**
     * Event to cancel scan covers.
     */
    public static final String EVENT_CANCEL_SCAN_COVERS = "cancel.scan.covers";

    /**
     * Action listener.
     */
    @Autowired
    private ConfigurationListener listener;

    /**
     * The main panel.
     */
    private JPanel panel;

    private JPanel tabPanel;

    private JProgressBar musicScrollBar;

    private FileScannerTask task;

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.view.components.View#getUI()
     */
    @Override
    public Component getUI() {
        panel = new JPanel(new MigLayout("insets 0, wrap 1, alignx center, aligny top"));

        setupTabs();

        tabPanel = new JPanel(new MigLayout("ins 0, wrap 4, center"));

        final String s = "h 1.3cm, w 3cm";

        tabPanel.add(configuration, s);
        tabPanel.add(music, s);
        tabPanel.add(spotify, s);
        tabPanel.add(proxy, s);

        changeTab(TAB.CONFIGURATION);

        JButton saveButton = setupSaveButton();
        tabPanel.add(saveButton, "cell 0 2, span 4, right");

        panel.add(tabPanel, "top, gapy 0.5cm, cell 0 0, w 100%, center");

        return new QScrollPane(panel);
    }

    private JButton setupSaveButton() {
        JButton saveButton = new QButton("Save");
        saveButton.addActionListener(this);
        saveButton.setActionCommand(SAVE);

        return saveButton;
    }

    private void setupTabs() {
        configuration = new QTab("Configuration");
        configuration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTab(TAB.CONFIGURATION);
            }
        });

        music = new QTab("Scan music");
        music.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTab(TAB.SCANNERS);
            }
        });

        spotify = new QTab("Spotify");
        spotify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTab(TAB.SPOTIFY);
            }
        });

        proxy = new QTab("Proxy");
        proxy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTab(TAB.PROXY);
            }
        });
    }

    private void changeTab(final TAB tab) {
        if (this.tab != null) {
            tabPanel.remove(this.tab);
        }

        if (tab == TAB.CONFIGURATION) {
            this.tab = configurationPanel;
        }
        else if (tab == TAB.PROXY) {
        }
        else if (tab == TAB.SCANNERS) {
            this.tab = scannerPanel;
        }
        else if (tab == TAB.SPOTIFY) {
            this.tab = spotifyPanel;
        }

        tabPanel.add(this.tab, "cell 0 1, top, left, span 6, w 50%, newline, shrink");
        tabPanel.updateUI();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == EVENT_UPDATE_COLLECTION) {
            scannerPanel.scanPathButton.setEnabled(false);
            scannerPanel.cancelScanPathButton.setEnabled(true);
            scannerPanel.scanPathButton.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            task = new FileScannerTask(id3Extractor, storage, artistStorage);
            task.addPropertyChangeListener(this);
            task.execute();
        }
        else if (e.getActionCommand() == EVENT_CANCEL_UPDATE_COLLECTION) {
            task.cancel(true);
            scannerPanel.scanPathButton.setEnabled(true);
            scannerPanel.cancelScanPathButton.setEnabled(false);
        }

        else if (e.getActionCommand() == SAVE) {
            Configuration config = Configuration.getInstance();
            config.setMusicPath(scannerPanel.musicPath.getText());
            config.setUseSpotify(spotifyPanel.spotifyCheckBox.isSelected());

            if (spotifyPanel.spotifyCheckBox.isSelected()) {
                config.setSpotifyUserName(spotifyPanel.spotifyUserName.getText());
                config.setSpotifyPassword(new String(spotifyPanel.spotifyPassword.getPassword())
                        .toCharArray());

                controlPanel.enableSearchTab(true);
            }
            else
                controlPanel.enableSearchTab(false);

            if (!configurationPanel.fontSelectBox.getSelectedItem().equals(
                    Configuration.getInstance().getFontBalancer())) {
                config.setFontBalancer(Float.parseFloat((String) configurationPanel.fontSelectBox
                        .getSelectedItem()));
            }

            listener.actionPerformed(new ActionEvent("", 0,
                    ConfigurationController.EVENT_UPDATE_CONFIGURATION));
        }

        else if (e.getSource() == scannerPanel.fileChooserButton) {
            if (fc == null) {
                fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setOpaque(false);
            }

            JFrame frame = new JFrame();
            frame.setBackground(Color.black);

            int returnVal = fc.showOpenDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION)
                scannerPanel.musicPath.setText(fc.getSelectedFile().getAbsolutePath());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (task.isDone()) {
            scannerPanel.scanPathButton.setEnabled(true);
            scannerPanel.cancelScanPathButton.setEnabled(false);
        }
        else {
            int progress = task.getProgress();
            musicScrollBar.setValue(progress);
            musicScrollBar.repaint();
            musicScrollBar.updateUI();
        }

    }
}
