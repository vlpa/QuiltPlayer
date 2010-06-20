package com.quiltplayer.view.swing.views.impl;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.controller.ConfigurationController;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.buttons.QTab;
import com.quiltplayer.view.swing.listeners.ConfigurationListener;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.panels.QScrollPane.ScrollDirection;
import com.quiltplayer.view.swing.panels.controlpanels.ControlPanel;
import com.quiltplayer.view.swing.views.View;
import com.quiltplayer.view.swing.views.impl.configurations.ConfigurationPanel;
import com.quiltplayer.view.swing.views.impl.configurations.LogPanel;
import com.quiltplayer.view.swing.views.impl.configurations.ScanningConfigurationPanel;
import com.quiltplayer.view.swing.views.impl.configurations.SpotifyConfigurationPanel;

/**
 * Configurations view.
 * 
 * @author Vlado Palczynski
 * 
 */
@org.springframework.stereotype.Component
public class ConfigurationView implements View, ActionListener {

    private static final String SAVE = "save";

    private enum TAB {
        CONFIGURATION, SPOTIFY, SCANNERS, PROXY, LOG
    };

    private Component tab;

    @Autowired
    private ControlPanel controlPanel;

    @Autowired
    private SpotifyConfigurationPanel spotifyPanel;

    @Autowired
    private ScanningConfigurationPanel scannerPanel;

    @Autowired
    private ConfigurationPanel configurationPanel;

    @Autowired
    private LogPanel logPanel;

    private QTab proxy;

    private QTab spotify;

    private QTab music;

    private QTab configuration;

    private QTab log;

    /**
     * Event to toggle full screen.
     */
    public static final String EVENT_TOGGLE_FULLSCREEN = "toggle.fullscreen";

    /**
     * Event to cancel scan covers.
     */
    public static final String EVENT_CANCEL_SCAN_COVERS = "cancel.scan.covers";

    /**
     * Action listener.
     */
    @Autowired
    private ConfigurationListener configurationListener;

    /**
     * The main panel.
     */
    private JPanel panel;

    private JPanel tabPanel;

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.view.components.View#getUI()
     */
    @Override
    public JComponent getUI() {
        panel = new JPanel(new MigLayout("insets 0, wrap 1, alignx center, aligny top"));

        /* Otherwise gray */
        panel.setOpaque(true);

        setupTabs();

        tabPanel = new JPanel(new MigLayout("ins 0, wrap 5, center, w 50%"));
        tabPanel.setOpaque(false);

        final String s = "h 1.3cm, w 3cm";

        tabPanel.add(configuration, s);
        tabPanel.add(music, s);
        tabPanel.add(spotify, s);
        tabPanel.add(proxy, s);
        tabPanel.add(log, s);

        changeTab(TAB.CONFIGURATION);

        final JButton saveButton = setupSaveButton();
        tabPanel.add(saveButton, "cell 0 2, span 5, right, gapy 0.5cm, " + QButton.MIG_HEIGHT);

        panel.add(tabPanel, "top,cell 0 0, w 100%, center, gapy 0.5cm");

        return new QScrollPane(panel, ScrollDirection.VERTICAL);
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

        log = new QTab("Log");
        log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logPanel.updateLogText();
                changeTab(TAB.LOG);
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
        else if (tab == TAB.LOG) {
            this.tab = logPanel;
        }

        tabPanel.add(this.tab, "cell 0 1, top, left, span 5, newline, grow, gapy 0.5cm 0.5cm");
        tabPanel.updateUI();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == SAVE) {
            Configuration config = Configuration.getInstance();
            config.getSpotifyProperties().setUseSpotify(spotifyPanel.spotifyCheckBox.isSelected());

            if (spotifyPanel.spotifyCheckBox.isSelected()) {
                config.getSpotifyProperties().setSpotifyUserName(spotifyPanel.spotifyUserName.getText());
                config.getSpotifyProperties().setSpotifyPassword(
                        new String(spotifyPanel.spotifyPassword.getPassword()).toCharArray());

                controlPanel.enableSearchTab(true);
            }
            else
                controlPanel.enableSearchTab(false);

            if (!configurationPanel.fontSelectBox.getSelectedItem().equals(
                    Configuration.getInstance().getFontBalancer())) {
                config.setFontBalancer(Float.parseFloat((String) configurationPanel.fontSelectBox.getSelectedItem()));
            }

            configurationListener.actionPerformed(new ActionEvent("", 0,
                    ConfigurationController.EVENT_UPDATE_CONFIGURATION));
        }
    }
}
