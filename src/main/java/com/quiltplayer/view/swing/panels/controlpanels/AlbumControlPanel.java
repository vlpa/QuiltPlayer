package com.quiltplayer.view.swing.panels.controlpanels;

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
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.ArtistController;
import com.quiltplayer.controller.ControlPanelController;
import com.quiltplayer.view.swing.buttons.QControlPanelButton;
import com.quiltplayer.view.swing.listeners.ArtistListener;
import com.quiltplayer.view.swing.listeners.ControlPanelListener;
import com.quiltplayer.view.swing.listeners.EditAlbumListener;
import com.quiltplayer.view.swing.panels.PlaylistPanel;
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
public class AlbumControlPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EditAlbumListener editAlbumListener;

    private Color[] gradient = { new Color(100, 100, 100), new Color(60, 60, 60),
            new Color(40, 40, 40), new Color(10, 10, 10) };

    private float[] dist = { 0.0f, 0.48f, 0.52f, 1.0f };

    @Autowired
    private ControlPanelListener controlPanelListener;

    @Autowired
    private ArtistListener artistListener;

    private enum Buttons {
        LYRICS, EDIT, PLAYLIST
    };

    private QControlPanelButton lyricsButton;

    private QControlPanelButton editButton;

    private QControlPanelButton albumButton;

    private QControlPanelButton moreAlbumsButton;

    @PostConstruct
    public void init() {
        setDefaults();
    }

    public void setDefaults() {
        setLayout(new MigLayout("insets 0, fill, wrap 4"));

        setupLyricsButton();

        setupEditButton();

        setupPlaylistButton();

        setupMoreAlbumsButton();

        final String layout = "h 2cm, w 3cm";

        add(albumButton, layout);
        add(lyricsButton, layout);
        add(editButton, layout);
        add(moreAlbumsButton, layout);
    }

    private void setupMoreAlbumsButton() {
        moreAlbumsButton = new QControlPanelButton("More albums",
                getIconFromClasspath("white/MoreAlbums.png"), SwingConstants.BOTTOM);
        moreAlbumsButton.addActionListener(artistListener);
        moreAlbumsButton.setActionCommand(ArtistController.ACTION_GET_ARTIST_ALBUMS);
    }

    private void setupLyricsButton() {
        lyricsButton = new QControlPanelButton("Lyrics", getIconFromClasspath("white/Lyrics.png"),
                SwingConstants.BOTTOM);
        lyricsButton.addActionListener(controlPanelListener);
        lyricsButton.setActionCommand(ControlPanelController.EVENT_VIEW_LYRICS);
    }

    private void setupPlaylistButton() {
        albumButton = new QControlPanelButton("Songs", getIconFromClasspath("white/SongTiles.png"),
                SwingConstants.BOTTOM);
        albumButton.addActionListener(controlPanelListener);
        albumButton.setActionCommand(ControlPanelController.EVENT_VIEW_ALBUM);
    }

    private void setupEditButton() {
        editButton = new QControlPanelButton("Edit album",
                getIconFromClasspath("white/Settings.png"), SwingConstants.BOTTOM);
        editButton.addActionListener(editAlbumListener);
        editButton.setActionCommand(PlaylistPanel.EVENT_UPDATE_ALBUM_ID3);
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

    public void updateTab(Buttons tab) {
        lyricsButton.inactivate();
        editButton.inactivate();
        albumButton.inactivate();

        if (tab == null) {
            // Nada
        }
        else if (tab == Buttons.LYRICS) {
            lyricsButton.activate();
        }
        else if (tab == Buttons.EDIT) {
            editButton.activate();
        }
        else if (tab == Buttons.PLAYLIST) {
            albumButton.activate();
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