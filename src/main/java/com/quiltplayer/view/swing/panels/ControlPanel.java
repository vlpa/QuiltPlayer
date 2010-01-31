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
import com.quiltplayer.view.swing.buttons.QTab;
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

    private static final Color SPOTIFY_COLOR = new Color(117, 182, 28);

    private static final int HEIGHT = 42;

    public static final String EVENT_VIEW_ARTIST = "view.artist";

    public static final String EVENT_ALBUM_QUILT = "album.quilt";

    public static final String EVENT_VIEW_CONFIGURATION = "view.configuration";

    public static final String EVENT_VIEW_SEARCH = "view.search";

    public static final String EVENT_VIEW_ABOUT = "view.about";

    private Color[] gradient = { new Color(125, 125, 125), new Color(120, 120, 120) };

    private float[] dist = { 0.0f, 1.0f };

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
        setup();
    }

    public void setup() {
        setLayout(new MigLayout("insets 0, wrap 1, aligny center"));

        setOpaque(false);
        // setBackground(new Color(40, 40, 40));

        setupQuiltCollectionButton();

        setupAlfabeticArtistsButton();

        setupSearchTab();

        setupConfigurationTab();

        // addAboutButton();

        addExitButton();

        setupKeybouardTab();

        // addIncreaseVolumeButton();

        // addDecreaseVolumeButton();

        final String s = "gapy 20 20, w 1cm, h 1cm";
        add(exitButton, s);
        add(quiltTab, s);
        add(artistsTab, s);
        add(searchTab, s);
        add(configTab, s);
        add(keyboardTab, s);

        updateUI();
    }

    private void setupQuiltCollectionButton() {
        Resource gearImage = new ClassPathResource("small-tiles.png");
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

        quiltTab = new QControlPanelButton("Quilt", icon);

        quiltTab.addActionListener(listener);
        quiltTab.setActionCommand(EVENT_ALBUM_QUILT);
    }

    private void setupAlfabeticArtistsButton() {
        Resource gearImage = new ClassPathResource("large-tiles.png");
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

        artistsTab = new QControlPanelButton("Artists", icon);

        artistsTab.addActionListener(listener);
        artistsTab.setActionCommand(EVENT_VIEW_ARTIST);
    }

    private void setupSearchTab() {
        Resource gearImage = new ClassPathResource("Search.png");
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

        searchTab = new QControlPanelButton("Spotify", icon);

        searchTab.addActionListener(listener);
        searchTab.setActionCommand(EVENT_VIEW_SEARCH);

        if (!Configuration.getInstance().isUseSpotify())
            enableSearchTab(false);
    }

    private void setupConfigurationTab() {
        Resource gearImage = new ClassPathResource("Gear.png");
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

        configTab = new QControlPanelButton("Config", icon);

        configTab.addActionListener(listener);
        configTab.setActionCommand(EVENT_VIEW_CONFIGURATION);
    }

    private void setupKeybouardTab() {
        Resource gearImage = new ClassPathResource("Gear.png");
        ImageIcon icon = null;

        try {
            icon = new ImageIcon(gearImage.getURL());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(SizeHelper.getControlPanelIconSize(), SizeHelper
                .getControlPanelIconSize(), java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);

        keyboardTab = new QControlPanelButton("K", icon);
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

    private void addExitButton() {
        Resource gearImage = new ClassPathResource("Power.png");
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

        exitButton = new QTab("End", icon);
        exitButton.addActionListener(new ExitHandler());
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(1, HEIGHT);

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist, gradient);

        g2d.setPaint(p);

        // g2d.fillRoundRect(0, 0, getWidth() + 5, getHeight(), 9, 9);

        /**
         * Arcs must be uneven or is gets unsymmetrically.
         */
        // g2d.fillRoundRect(0, 0, width, DEFAULT_HEIGHT, 9, 9);
        // g.setColor(new Color(30, 30, 30));
        super.paintComponent(g);
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
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
        // TODO Auto-generated method stub
        g.setColor(new Color(25, 25, 25));
        g.drawLine(0, getHeight() - 3, getWidth(), getHeight() - 3);
        g.setColor(new Color(20, 20, 20));
        g.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);
        g.setColor(new Color(15, 15, 15));
        g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }

    public void enableSearchTab(boolean b) {
        if (b)
            searchTab.setEnabled(true);
        else
            searchTab.setEnabled(false);
    }
}
