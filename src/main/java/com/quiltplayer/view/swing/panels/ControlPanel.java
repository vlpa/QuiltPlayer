package com.quiltplayer.view.swing.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.ControlPanelController;
import com.quiltplayer.controller.PlayerController;
import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.buttons.QControlPanelButton;
import com.quiltplayer.view.swing.buttons.QTextButton;
import com.quiltplayer.view.swing.handlers.ExitHandler;
import com.quiltplayer.view.swing.listeners.ControlPanelListener;
import com.quiltplayer.view.swing.util.SizeHelper;

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
            new Color(40, 40, 40), new Color(20, 20, 20) };

    private float[] dist = { 0.0f, 0.60f, 0.65f, 1.0f };

    @Autowired
    private ControlPanelListener listener;

    @Autowired
    private PlayerListener playerListener;

    public enum Tab {
        NONE, QUILT, ARTISTS, CONFIGURATION, SEARCH
    };

    private QControlPanelButton quiltTab;

    private QControlPanelButton artistsTab;

    private QControlPanelButton searchTab;

    private QControlPanelButton configTab;

    private QControlPanelButton keyboardTab;

    private JButton exitButton;

    @PostConstruct
    public void init() {
        setDefaults();
    }

    public void setDefaults() {
        setLayout(new MigLayout("insets 0, wrap 1, aligny center"));

        setupQuiltCollectionButton();

        setupAlfabeticArtistsButton();

        setupSearchTab();

        setupConfigurationTab();

        // addAboutButton();

        setupExitButton();

        setupKeyboardTab();

        // addIncreaseVolumeButton();

        // addDecreaseVolumeButton();

        final String s = "gapy 20 20, w 1cm, h 1cm";
        add(exitButton, s + ", dock north");
        add(quiltTab, s);
        add(artistsTab, s);
        add(searchTab, s);
        add(configTab, s);
        add(keyboardTab, s);
    }

    private void setupQuiltCollectionButton() {
        quiltTab = new QControlPanelButton("Quilt", getIconFromClasspath("small-tiles.png"));

        quiltTab.addActionListener(listener);
        quiltTab.setActionCommand(EVENT_ALBUM_QUILT);
    }

    private void setupAlfabeticArtistsButton() {
        artistsTab = new QControlPanelButton("Artists", getIconFromClasspath("large-tiles.png"));

        artistsTab.addActionListener(listener);
        artistsTab.setActionCommand(EVENT_VIEW_ARTIST);
    }

    private void setupSearchTab() {
        searchTab = new QControlPanelButton("Spotify", getIconFromClasspath("white/Search.png"));

        searchTab.addActionListener(listener);
        searchTab.setActionCommand(EVENT_VIEW_SEARCH);

        if (!Configuration.getInstance().isUseSpotify())
            enableSearchTab(false);
    }

    private void setupConfigurationTab() {
        configTab = new QControlPanelButton("Config", getIconFromClasspath("white/Gear.png"));

        configTab.addActionListener(listener);
        configTab.setActionCommand(EVENT_VIEW_CONFIGURATION);
    }

    private void setupKeyboardTab() {

        keyboardTab = new QControlPanelButton("Keys", getIconFromClasspath("white/Gear.png"));
        keyboardTab.addActionListener(listener);
        keyboardTab.setActionCommand(ControlPanelController.EVENT_VIEW_KEYBOARD);
    }

    private void addDecreaseVolumeButton() {
        JButton decreaseButton = new QTextButton(")");
        decreaseButton.addActionListener(playerListener);
        decreaseButton.setActionCommand(PlayerController.EVENT_DECREASE_GAIN);
        decreaseButton.setToolTipText("Decrease volume");
        decreaseButton.setBorderPainted(false);
        decreaseButton.setOpaque(false);

        add(decreaseButton, "dock east, gapx 5, gapafter 2");
    }

    private void addIncreaseVolumeButton() {
        JButton decreaseButton = new QTextButton(")))");
        decreaseButton.addActionListener(playerListener);
        decreaseButton.setActionCommand(PlayerController.EVENT_INCREASE_GAIN);
        decreaseButton.setToolTipText("Increase volume");
        decreaseButton.setBorderPainted(false);
        decreaseButton.setOpaque(false);

        add(decreaseButton, "dock east, gapx 2, gapafter 30, shrinkprio 1");
    }

    private void setupExitButton() {
        exitButton = new QControlPanelButton("End", getIconFromClasspath("white/Power.png"));
        exitButton.addActionListener(new ExitHandler());
    }

    private ImageIcon getIconFromClasspath(final String classPathName) {
        Resource gearImage = new ClassPathResource(classPathName);
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(gearImage.getURL());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Image img = icon.getImage();
        img = img.getScaledInstance(SizeHelper.getControlPanelIconSize(), SizeHelper
                .getControlPanelIconSize(), java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        return icon;
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
        // g.setColor(new Color(25, 25, 25));
        // g.drawLine(0, getHeight() - 3, getWidth(), getHeight() - 3);
        // g.setColor(new Color(20, 20, 20));
        // g.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);
        // g.setColor(new Color(15, 15, 15));
        // g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }

    public void enableSearchTab(boolean b) {
        if (b)
            searchTab.setEnabled(true);
        else
            searchTab.setEnabled(false);
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
        Point2D end = new Point2D.Float(50, 0);

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist, gradient);

        g2d.setPaint(p);

        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
