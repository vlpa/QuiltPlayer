package com.quiltplayer.view.swing.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.ControlPanelController;
import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.utils.ClassPathUtils;
import com.quiltplayer.view.swing.buttons.QControlPanelButton;
import com.quiltplayer.view.swing.handlers.ExitHandler;
import com.quiltplayer.view.swing.listeners.ControlPanelListener;
import com.quiltplayer.view.swing.panels.controlpanels.PlayerControlPanel;

/**
 * GUI for the control panel.
 * 
 * @author Vlado Palczynski
 */
/**
 * @author Vlado Palczynski
 */
@Component
public class ControlPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public static final String EVENT_VIEW_ARTIST = "view.artist";

    public static final String EVENT_ALBUM_QUILT = "album.quilt";

    public static final String EVENT_VIEW_CONFIGURATION = "view.configuration";

    public static final String EVENT_VIEW_SEARCH = "view.search";

    public static final String EVENT_VIEW_ABOUT = "view.about";

    private Color[] gradient = { new Color(100, 100, 100), new Color(60, 60, 60),
            new Color(40, 40, 40), new Color(10, 10, 10) };

    private float[] dist = { 0.0f, 0.60f, 0.64f, 1.0f };

    @Autowired
    private ControlPanelListener listener;

    public enum Tab {
        NONE, QUILT, ARTISTS, CONFIGURATION, SEARCH
    };

    private QControlPanelButton quiltTab;

    private QControlPanelButton artistsTab;

    private QControlPanelButton searchTab;

    private QControlPanelButton configTab;

    private QControlPanelButton keyboardTab;

    public QControlPanelButton albumViewButton;

    private JButton exitButton;

    @Autowired
    private PlayerControlPanel playerControlPanel;

    @PostConstruct
    public void init() {
        setDefaults();
    }

    public void setDefaults() {
        setLayout(new MigLayout("insets 0, fill, wrap 10"));

        setupQuiltCollectionButton();

        setupAlfabeticArtistsButton();

        setupSearchButton();

        setupConfigurationButton();

        // addAboutButton();

        setupExitButton();

        setupKeyboardTab();

        setupAlbumViewButton();

        final String s = "h 100%, w 3cm";

        final JPanel applicationButtons = new JPanel(new MigLayout("insets 0, alignx center"));
        applicationButtons.setOpaque(false);
        applicationButtons.add(quiltTab, s);
        applicationButtons.add(artistsTab, s);
        applicationButtons.add(searchTab, s);
        applicationButtons.add(configTab, s);
        applicationButtons.add(keyboardTab, s);
        applicationButtons.add(exitButton, s);

        playerControlPanel.add(albumViewButton, "cell 0 0");

        add(playerControlPanel, "w " + ImageSizes.LARGE.getSize() + "px, dock west, gapx 10 10");
        add(applicationButtons, "w 100% - " + ImageSizes.LARGE.getSize() + "px, gapx 100 100");
    }

    private void setupQuiltCollectionButton() {
        quiltTab = new QControlPanelButton("Quilt", ClassPathUtils
                .getIconFromClasspath("white/small-tiles.png"), SwingConstants.TOP);

        quiltTab.addActionListener(listener);
        quiltTab.setActionCommand(EVENT_ALBUM_QUILT);
    }

    private void setupAlfabeticArtistsButton() {
        artistsTab = new QControlPanelButton("Artists", ClassPathUtils
                .getIconFromClasspath("white/large-tiles.png"), SwingConstants.TOP);

        artistsTab.addActionListener(listener);
        artistsTab.setActionCommand(EVENT_VIEW_ARTIST);
    }

    private void setupAlbumViewButton() {
        albumViewButton = new QControlPanelButton("Album view", ClassPathUtils
                .getIconFromClasspath("white/AlbumView.png"), SwingConstants.TOP);
        albumViewButton.addActionListener(listener);
        albumViewButton.setActionCommand(ControlPanelController.EVENT_TOGGLE_ALBUM_VIEW);
        albumViewButton.activate();
    }

    private void setupSearchButton() {
        searchTab = new QControlPanelButton("Spotify", ClassPathUtils
                .getIconFromClasspath("white/Search.png"), SwingConstants.TOP);

        searchTab.addActionListener(listener);
        searchTab.setActionCommand(EVENT_VIEW_SEARCH);

        if (!Configuration.getInstance().isUseSpotify())
            enableSearchTab(false);
    }

    private void setupConfigurationButton() {
        configTab = new QControlPanelButton("Config", ClassPathUtils
                .getIconFromClasspath("white/Settings.png"), SwingConstants.TOP);

        configTab.addActionListener(listener);
        configTab.setActionCommand(EVENT_VIEW_CONFIGURATION);
    }

    private void setupKeyboardTab() {

        keyboardTab = new QControlPanelButton("Keys", ClassPathUtils
                .getIconFromClasspath("white/Settings.png"), SwingConstants.TOP);
        keyboardTab.addActionListener(listener);
        keyboardTab.setActionCommand(ControlPanelController.EVENT_VIEW_KEYBOARD);
    }

    private void setupExitButton() {
        exitButton = new QControlPanelButton("End", ClassPathUtils
                .getIconFromClasspath("white/Power.png"), SwingConstants.TOP);
        exitButton.addActionListener(new ExitHandler());
    }

    public void updateTab(Tab tab) {
        quiltTab.inactivate();
        artistsTab.inactivate();
        configTab.inactivate();
        searchTab.inactivate();

        if (tab == null) {
            // Nada
        }
        else if (tab == Tab.QUILT) {
            quiltTab.activate();
        }
        else if (tab == Tab.ARTISTS) {
            artistsTab.activate();
        }
        else if (tab == Tab.CONFIGURATION) {
            configTab.activate();
        }
        else if (tab == Tab.SEARCH) {
            searchTab.activate();
        }

        repaint();
        updateUI();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
    }

    public void enableSearchTab(boolean b) {
        if (b)
            searchTab.setEnabled(true);
        else
            searchTab.setEnabled(false);
    }

    public PlayerControlPanel getPlayerControlPanel() {
        return playerControlPanel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(0, getHeight());

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist, gradient);

        g2d.setPaint(p);

        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
