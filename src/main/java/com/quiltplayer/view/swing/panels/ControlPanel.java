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

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.PlayerController;
import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.buttons.QTab;
import com.quiltplayer.view.swing.buttons.QTextButton;
import com.quiltplayer.view.swing.handlers.ExitHandler;
import com.quiltplayer.view.swing.listeners.ControlPanelListener;

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

    private QTab quiltTab;

    private QTab artistsTab;

    private QTab searchTab;

    private QTab configTab;

    private JButton exitButton;

    @PostConstruct
    public void init() {
        setup();
    }

    public void setup() {
        setLayout(new MigLayout("fillx, aligny center"));

        setOpaque(true);

        setBackground(new Color(40, 40, 40));

        addQuiltCollectionButton();

        addAlfabeticArtistsButton();

        addSearchTab();

        addConfigurationTab();

        // addAboutButton();

        addExitButton();

        // addIncreaseVolumeButton();

        // addDecreaseVolumeButton();

        updateUI();
    }

    private void addQuiltCollectionButton() {
        quiltTab = new QTab("Quilt");

        quiltTab.addActionListener(listener);
        quiltTab.setActionCommand(EVENT_ALBUM_QUILT);

        add(quiltTab, "dock west, gapx 15, w 3.5cm");
    }

    private void addAlfabeticArtistsButton() {
        artistsTab = new QTab("Artists");

        artistsTab.addActionListener(listener);
        artistsTab.setActionCommand(EVENT_VIEW_ARTIST);

        add(artistsTab, "dock west, gapx 5, w 3.5cm, h 1.1cm!");
    }

    private void addSearchTab() {
        searchTab = new QTab("Spotify");

        searchTab.addActionListener(listener);
        searchTab.setActionCommand(EVENT_VIEW_SEARCH);

        add(searchTab, "dock west, gapx 5, w 3.5cm");

        if (!Configuration.getInstance().isUseSpotify())
            enableSearchTab(false);
    }

    private void addConfigurationTab() {
        configTab = new QTab("Configuration");

        configTab.addActionListener(listener);
        configTab.setActionCommand(EVENT_VIEW_CONFIGURATION);

        add(configTab, "dock west, gapx 5, w 3.5cm");
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
        Color[] gradient = new Color[] { new Color(122, 27, 27), new Color(90, 20, 20),
                new Color(50, 10, 10), new Color(20, 5, 5) };

        exitButton = new QTab("X", gradient);
        exitButton.addActionListener(new ExitHandler());

        add(exitButton, "dock east, gapx 0, wrap, w 0.8	cm");
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

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paint(g);
    }

    public void enableSearchTab(boolean b) {
        if (b)
            searchTab.setEnabled(true);
        else
            searchTab.setEnabled(false);
    }
}
