package com.quiltplayer.view.swing.panels.controlpanels;

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

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.ControlPanelController;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.utils.ClassPathUtils;
import com.quiltplayer.view.swing.buttons.QControlPanelButton;
import com.quiltplayer.view.swing.handlers.ExitHandler;
import com.quiltplayer.view.swing.listeners.ControlPanelListener;
import com.quiltplayer.view.swing.panels.MainTabs;

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

    public static final String EVENT_QUILT = "album.quilt";

    public static final String EVENT_VIEW_SEARCH = "view.search";

    public static final String EVENT_VIEW_ABOUT = "view.about";

    private Color[] gradient = { new Color(80, 80, 80), new Color(50, 50, 50),
            new Color(20, 20, 20), new Color(00, 00, 00) };

    // private Color[] gradient = { new Color(150, 00, 00), new Color(100, 0, 0), new Color(80, 0,
    // 0),
    // new Color(40, 0, 0) };

    // private Color[] gradient = { new Color(0, 0, 0), new Color(30, 30, 30) };

    private float[] dist = { 0.0f, 0.48f, 0.52f, 1.0f };

    // private float[] dist = { 0.0f, 1.0f };

    @Autowired
    private ControlPanelListener controlPanelListener;

    private QControlPanelButton quiltButton;

    private QControlPanelButton artistsButton;

    private QControlPanelButton searchButton;

    private QControlPanelButton configButton;

    private QControlPanelButton keyboardButton;

    @Autowired
    private GraphDatabaseService graphDatabaseService;

    private JButton exitButton;

    @Autowired
    private PlayerControlPanel playerControlPanel;

    @PostConstruct
    public void init() {
        setDefaults();
    }

    public void setDefaults() {
        setLayout(new MigLayout("insets 0, fill, flowy"));

        setupQuiltCollectionButton();

        setupAlfabeticArtistsButton();

        setupSearchButton();

        setupConfigurationButton();

        // addAboutButton();

        setupExitButton();

        setupKeyboardTab();

        final String s = "h 100%, w 3cm";

        // final String s = "h 100%, w 100%";

        final JPanel applicationButtons = new JPanel(new MigLayout(
                "insets 1cm 0 1cm 0, alignx center, flowy"));

        applicationButtons.setOpaque(false);
        applicationButtons.add(quiltButton, s);
        applicationButtons.add(artistsButton, s);
        applicationButtons.add(searchButton, s);
        applicationButtons.add(configButton, s);
        applicationButtons.add(keyboardButton, s);
        applicationButtons.add(exitButton, s);

        // add(playerControlPanel, "w 30%, dock west");
        add(applicationButtons, "h 100% - 2cm, dock east");

        updateUI();
    }

    private void setupQuiltCollectionButton() {
        quiltButton = new QControlPanelButton("Quilt", ClassPathUtils
                .getIconFromClasspath("white/small-tiles.png"), SwingConstants.BOTTOM,
                SwingConstants.RIGHT);

        quiltButton.addActionListener(controlPanelListener);
        quiltButton.setActionCommand(EVENT_QUILT);
    }

    private void setupAlfabeticArtistsButton() {
        artistsButton = new QControlPanelButton("Artists", ClassPathUtils
                .getIconFromClasspath("white/large-tiles.png"), SwingConstants.BOTTOM,
                SwingConstants.RIGHT);

        artistsButton.addActionListener(controlPanelListener);
        artistsButton.setActionCommand(ControlPanelController.EVENT_VIEW_ARTIST);
    }

    private void setupSearchButton() {
        searchButton = new QControlPanelButton("Spotify", ClassPathUtils
                .getIconFromClasspath("white/Search.png"), SwingConstants.BOTTOM,
                SwingConstants.RIGHT);

        searchButton.addActionListener(controlPanelListener);
        searchButton.setActionCommand(EVENT_VIEW_SEARCH);

        if (!Configuration.getInstance().getSpotifyProperties().isUseSpotify())
            enableSearchTab(false);
    }

    private void setupConfigurationButton() {
        configButton = new QControlPanelButton("Config", ClassPathUtils
                .getIconFromClasspath("white/Settings.png"), SwingConstants.BOTTOM,
                SwingConstants.RIGHT);

        configButton.addActionListener(controlPanelListener);
        configButton.setActionCommand(ControlPanelController.EVENT_VIEW_CONFIGURATION);
    }

    private void setupKeyboardTab() {

        keyboardButton = new QControlPanelButton("Keys", ClassPathUtils
                .getIconFromClasspath("white/Settings.png"), SwingConstants.BOTTOM,
                SwingConstants.RIGHT);
        keyboardButton.addActionListener(controlPanelListener);
        keyboardButton.setActionCommand(ControlPanelController.EVENT_VIEW_KEYBOARD);
    }

    private void setupExitButton() {
        exitButton = new QControlPanelButton("End", ClassPathUtils
                .getIconFromClasspath("white/Power.png"), SwingConstants.BOTTOM,
                SwingConstants.RIGHT);
        exitButton.addActionListener(new ExitHandler(graphDatabaseService));
    }

    public void updateTab(MainTabs tab) {
        quiltButton.inactivate();
        artistsButton.inactivate();
        configButton.inactivate();
        searchButton.inactivate();

        if (tab == null) {
            // Nada
        }
        else if (tab == MainTabs.QUILT) {
            quiltButton.activate();
        }
        else if (tab == MainTabs.ARTISTS) {
            artistsButton.activate();
        }
        else if (tab == MainTabs.CONFIGURATION) {
            configButton.activate();
        }
        else if (tab == MainTabs.SEARCH) {
            searchButton.activate();
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
            searchButton.setEnabled(true);
        else
            searchButton.setEnabled(false);
    }

    public PlayerControlPanel getPlayerControlPanel() {
        return playerControlPanel;
    }

    public void flashKeyboard() {
        System.out.println("Flash here");
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /* Horizontal */
        // Point2D start = new Point2D.Float(0, 0);
        // Point2D end = new Point2D.Float(0, getHeight());

        /* Vertical */
        Point2D start = new Point2D.Float(0, getHeight());
        Point2D end = new Point2D.Float(getWidth(), getHeight());

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist, gradient);

        g2d.setPaint(p);

        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
