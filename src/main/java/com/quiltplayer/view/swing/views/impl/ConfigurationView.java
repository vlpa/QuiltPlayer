package com.quiltplayer.view.swing.views.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.controller.ConfigurationController;
import com.quiltplayer.core.scanner.tasks.FileScannerTask;
import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.core.storage.Storage;
import com.quiltplayer.internal.id3.Id3Extractor;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.ColorConstantsLight;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.checkbox.QCheckBox;
import com.quiltplayer.view.swing.designcomponents.TextFieldComponents;
import com.quiltplayer.view.swing.labels.QLabel;
import com.quiltplayer.view.swing.listeners.ConfigurationListener;
import com.quiltplayer.view.swing.listeners.ScanningListener;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.panels.controlpanels.ControlPanel;
import com.quiltplayer.view.swing.textfields.QPasswordField;
import com.quiltplayer.view.swing.textfields.QTextField;
import com.quiltplayer.view.swing.views.View;

/**
 * Configurations view.
 * 
 * @author Vlado Palczynski
 * 
 */
@org.springframework.stereotype.Component
public class ConfigurationView implements View, ActionListener, PropertyChangeListener {

    private static final String SAVE = "save";

    @Autowired
    private ControlPanel controlPanel;

    @Autowired
    private Id3Extractor id3Extractor;

    @Autowired
    private Storage storage;

    @Autowired
    private ArtistStorage artistStorage;

    /**
     * Scanning listener.
     */
    @Autowired
    private ScanningListener scanningListener;

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
     * The path to music.
     */
    private JTextField musicPath = new QTextField();

    /**
     * The light color profile JRadioButton.
     */
    private JRadioButton lightColorProfile, darkColorProfile;

    /**
     * Color profile light.
     */
    private static final String COLOR_PROFILE_LIGHT = "light";

    /**
     * Color profile dark.
     */
    private static final String COLOR_PROFILE_DARK = "dark";

    /**
     * The proxy port.
     */
    private static JTextField proxyPort;

    /**
     * The proxy url.
     */
    private static JTextField proxyUrl;

    /**
     * The proxy user name.
     */
    private static JTextField proxyUsername;

    /**
     * The proxy password.
     */
    private static JPasswordField proxyPassword;

    /**
     * Action listener.
     */
    @Autowired
    private ConfigurationListener listener;

    /**
     * The scan button.
     */
    private JButton scanCoversButton, cancelScanCoversButton;

    /**
     * The update button.
     */
    private JButton scanPathButton, cancelScanPathButton;

    /**
     * Enable/disable fullscreen button
     */
    private JButton fullscreenButton;

    private JButton fileChooserButton;
    /**
     * The main panel.
     */
    private JPanel panel;

    /**
     * The proxy settings panel.
     */
    private JPanel proxySettings;

    private JComboBox fontSelectBox;

    /**
     * The proxy checkbox.
     */
    private JCheckBox proxyCheckBox;

    /**
     * The spotify checkbox.
     */
    private JCheckBox spotifyCheckBox;

    /**
     * The spotify settings panel.
     */
    private JPanel spotifySettings;

    /**
     * The spotify user name.
     */
    private static JTextField spotifyUserName = new QTextField();

    /**
     * The spotify password.
     */
    private static JPasswordField spotifyPassword = new QPasswordField();

    private JProgressBar musicScrollBar;

    private FileScannerTask task;

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.view.components.View#close()
     */
    @Override
    public void close() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.view.components.View#getUI()
     */
    @Override
    public Component getUI() {
        if (panel == null) {
            panel = new JPanel();
            panel.setOpaque(true);
            panel.setBackground(Configuration.getInstance().getColorConstants().getBackground());

            panel.setLayout(new MigLayout("insets 0, wrap 3, alignx center, aligny center"));

            fileChooserButton = new QButton("Select");
            fileChooserButton.addActionListener(this);

            panel.add(TextFieldComponents.textFieldComponentForForms("Root folder",
                    Configuration.ROOT_PATH, false), "left, w 40%, newline");
            panel.add(TextFieldComponents.textFieldComponentForForms("Storage folder",
                    Configuration.STORAGE_PATH, false), "left, w 40%, newline");
            panel.add(TextFieldComponents.textFieldComponentForForms("Album covers folder",
                    Configuration.ALBUM_COVERS_PATH, false), "left, w 40%, newline");
            panel.add(TextFieldComponents.textFieldComponentForFormsWithButton(
                    "Music directory to scan", musicPath, Configuration.ALBUM_COVERS_PATH, false,
                    fileChooserButton), "left, w 40%, newline");

            addFontSize();

            // addColorProfile();

            // addProxySettings();

            addSpotifySettings();
            panel.add(spotifySettings, "left, w 40%, newline");

            JButton saveButton = new QButton("Save");
            saveButton.addActionListener(this);
            saveButton.setActionCommand(SAVE);

            panel.add(saveButton, "w 40% - 2cm, span 2, w 2cm, newline, gapy 20 0, right, wrap");

            addScanMusicButton();

            addScanCoversButton();

            addToggleFullscreenButton();
        }

        return new QScrollPane(panel);
    }

    // private void addColorProfile()
    // {
    // JLabel colorProfileLabel = addColorProfileButtons();
    // panel.add(colorProfileLabel, "left, newline");
    //
    // JPanel tmpPanel = new JPanel();
    // tmpPanel.setOpaque(false);
    // tmpPanel.add(lightColorProfile);
    // tmpPanel.add(darkColorProfile);
    // panel.add(tmpPanel, "left, newline");
    // }

    private void addFontSize() {
        fontSelectBox = new JComboBox(new String[] { "-3", "-2", "-1", "0", "+1", "+2", "+3" });
        fontSelectBox.setOpaque(true);

        int currentValue = ((Integer) ((Float) Configuration.getInstance().getFontBalancer())
                .intValue());

        String currentValueAsString = null;
        if (currentValue > 0)
            currentValueAsString = "+" + currentValue;
        else
            currentValueAsString = currentValue + "";

        fontSelectBox.setSelectedItem(currentValueAsString);

        panel.add(new QLabel("Font adjust"), "left, newline");
        panel.add(fontSelectBox, "left, w 2cm, newline");
    }

    /**
     * Setup the color profile selector.
     * 
     * @return Color profile label
     */
    private JLabel addColorProfileButtons() {
        JLabel colorProfileLabel = new QLabel("Color profile");
        lightColorProfile = new JRadioButton("Light");
        lightColorProfile.setOpaque(false);
        lightColorProfile.setActionCommand(COLOR_PROFILE_LIGHT);
        lightColorProfile.addActionListener(this);

        darkColorProfile = new JRadioButton("Dark");
        darkColorProfile.setOpaque(false);
        darkColorProfile.setActionCommand(COLOR_PROFILE_DARK);
        darkColorProfile.addActionListener(this);

        if (Configuration.getInstance().getColorConstants() instanceof ColorConstantsLight) {
            lightColorProfile.setSelected(true);
        }
        else {
            darkColorProfile.setSelected(true);
        }

        return colorProfileLabel;
    }

    /**
	 * 
	 */
    private void addProxySettings() {
        proxyCheckBox = new JCheckBox("Proxy");
        proxyCheckBox.setOpaque(false);

        MouseListener l = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (proxyCheckBox.isSelected())
                    proxySettings.setVisible(true);
                else
                    proxySettings.setVisible(false);
            }
        };

        proxyCheckBox.addMouseListener(l);

        panel.add(proxyCheckBox, "left");
        panel.add(proxyCheckBox, "right");

        proxySettings = new JPanel(new MigLayout("insets 0, wrap 2, alignx center, aligny center"));
        proxySettings.setOpaque(false);

        if (Configuration.getInstance().isUseProxy()) {
            proxySettings.setVisible(true);
            proxyCheckBox.setSelected(true);
        }
        else
            proxySettings.setVisible(false);

        JLabel proxyPortLabel = new JLabel("Proxy port");
        proxyPort = new QTextField(false);
        proxyPort.setText(Configuration.getInstance().getProxyPort() + "");
        proxySettings.add(proxyPortLabel, "right");
        proxySettings.add(proxyPort, "left");

        JLabel proxyUrlLabel = new JLabel("Proxy URL");
        proxyUrl = new QTextField(false);
        proxyUrl.setText(Configuration.getInstance().getProxyUrl() + "");
        proxySettings.add(proxyUrlLabel, "right");
        proxySettings.add(proxyUrl, "left");

        JLabel proxyUsernameLabel = new QLabel("Proxy username");
        proxyUsername = new QTextField(false);
        proxyUsername.setText(Configuration.getInstance().getProxyUsername() + "");
        proxySettings.add(proxyUsernameLabel, "right");
        proxySettings.add(proxyUsername, "left");

        JLabel proxyPasswordLabel = new QLabel("Proxy password (not saved)");
        proxyPassword = new QPasswordField();
        proxySettings.add(proxyPasswordLabel, "right");
        proxySettings.add(proxyPassword, "left");

        panel.add(proxySettings);
    }

    /**
	 * 
	 */
    private void addSpotifySettings() {
        spotifySettings = new JPanel(new MigLayout("insets 0, wrap 2, fillx"));
        spotifySettings.setOpaque(false);

        spotifyCheckBox = new QCheckBox("Spotify account");

        MouseListener l = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (spotifyCheckBox.isSelected())
                    spotifySettings.setVisible(true);
                else
                    spotifySettings.setVisible(false);
            }
        };

        spotifyCheckBox.addMouseListener(l);

        panel.add(spotifyCheckBox, "newline");

        if (Configuration.getInstance().isUseSpotify()) {
            spotifySettings.setVisible(true);
            spotifyCheckBox.setSelected(true);
        }
        else
            spotifySettings.setVisible(false);

        spotifySettings.add(TextFieldComponents.textFieldComponentForForms("Spotify user name",
                spotifyUserName, Configuration.getInstance().getSpotifyUserName() + "", true),
                "left, w 100%, newline");
        spotifySettings.add(TextFieldComponents.textFieldComponentForForms("Spotify password",
                spotifyPassword, Configuration.getInstance().getSpotifyPassword() + "", true),
                "left, w 100%, newline");
    }

    private void addScanCoversButton() {
        scanCoversButton = new QButton("Search covers");
        scanCoversButton.addActionListener(scanningListener);
        scanCoversButton.setActionCommand(EVENT_SCAN_COVERS);

        panel.add(scanCoversButton, "w 2.7cm");

        cancelScanCoversButton = new QButton("X");
        cancelScanCoversButton.setOpaque(false);
        cancelScanCoversButton.addActionListener(scanningListener);
        cancelScanCoversButton.setActionCommand(EVENT_CANCEL_SCAN_COVERS);

        panel.add(cancelScanCoversButton, "gapy 10 0, w 0.8cm");
    }

    private void addScanMusicButton() {
        scanPathButton = new QButton("Scan path");
        scanPathButton.addActionListener(this);
        scanPathButton.setActionCommand(EVENT_UPDATE_COLLECTION);

        panel.add(scanPathButton, "w 2.7cm");

        musicScrollBar = new JProgressBar(0, 100);
        panel.add(musicScrollBar, "w 100");

        cancelScanPathButton = new QButton("X");
        cancelScanPathButton.addActionListener(this);
        cancelScanPathButton.setActionCommand(EVENT_CANCEL_UPDATE_COLLECTION);

        panel.add(cancelScanPathButton, "gapy 0 0, w 0.8cm");
    }

    private void addToggleFullscreenButton() {
        fullscreenButton = new QButton("Toggle fullscreen");
        fullscreenButton.addActionListener(listener);
        fullscreenButton.setActionCommand(EVENT_TOGGLE_FULLSCREEN);

        panel.add(fullscreenButton, "gapy 10, w 2.7cm");
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == EVENT_UPDATE_COLLECTION) {
            scanPathButton.setEnabled(false);
            cancelScanPathButton.setEnabled(true);
            scanPathButton.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            task = new FileScannerTask(id3Extractor, storage, artistStorage);
            task.addPropertyChangeListener(this);
            task.execute();
        }
        else if (e.getActionCommand() == EVENT_CANCEL_UPDATE_COLLECTION) {
            task.cancel(true);
            scanPathButton.setEnabled(true);
            cancelScanPathButton.setEnabled(false);
        }
        else if (e.getActionCommand() == COLOR_PROFILE_LIGHT) {
            lightColorProfile.setSelected(true);
            darkColorProfile.setSelected(false);
        }
        else if (e.getActionCommand() == COLOR_PROFILE_DARK) {
            darkColorProfile.setSelected(true);
            lightColorProfile.setSelected(false);
        }
        else if (e.getActionCommand() == SAVE) {
            Configuration config = Configuration.getInstance();
            config.setMusicPath(musicPath.getText());
            config.setUseSpotify(spotifyCheckBox.isSelected());

            if (spotifyCheckBox.isSelected()) {
                config.setSpotifyUserName(spotifyUserName.getText());
                config.setSpotifyPassword(new String(spotifyPassword.getPassword()).toCharArray());

                controlPanel.enableSearchTab(true);
            }
            else
                controlPanel.enableSearchTab(false);

            if (!fontSelectBox.getSelectedItem().equals(
                    Configuration.getInstance().getFontBalancer())) {
                config.setFontBalancer(Float.parseFloat((String) fontSelectBox.getSelectedItem()));
            }

            listener.actionPerformed(new ActionEvent("", 0,
                    ConfigurationController.EVENT_UPDATE_CONFIGURATION));
        }

        else if (e.getSource() == fileChooserButton) {
            if (fc == null) {
                fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setOpaque(false);
            }

            JFrame frame = new JFrame();
            frame.setBackground(Color.black);

            int returnVal = fc.showOpenDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION)
                musicPath.setText(fc.getSelectedFile().getAbsolutePath());
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
            scanPathButton.setEnabled(true);
            cancelScanPathButton.setEnabled(false);
        }
        else {
            int progress = task.getProgress();
            musicScrollBar.setValue(progress);
            musicScrollBar.repaint();
            musicScrollBar.updateUI();
        }

    }
}
