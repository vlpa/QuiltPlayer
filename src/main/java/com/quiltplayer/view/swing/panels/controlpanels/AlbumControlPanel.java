package com.quiltplayer.view.swing.panels.controlpanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.quiltplayer.controller.AddAlbumController;
import com.quiltplayer.controller.ArtistController;
import com.quiltplayer.controller.ControlPanelController;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.jotify.JotifyAlbum;
import com.quiltplayer.utils.ClassPathUtils;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.buttons.QControlPanelButton;
import com.quiltplayer.view.swing.listeners.AddAlbumListener;
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
public class AlbumControlPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EditAlbumListener editAlbumListener;

    private Color[] gradient = { new Color(20, 20, 20), new Color(0, 0, 0) };

    private float[] dist = { 0.0f, 1.0f };

    @Autowired
    private PlayerControlPanel playerControlPanel;
    @Autowired
    private ControlPanelListener controlPanelListener;

    @Autowired
    private ArtistListener artistListener;

    @Autowired
    private AddAlbumListener addAlbumListener;

    public enum Buttons {
        LYRICS, EDIT, PLAYLIST, ADD, IMAGES, WIKI, ALBUMS
    };

    private QControlPanelButton lyricsButton;

    private QControlPanelButton editButton;

    private QControlPanelButton addButton;

    private QControlPanelButton albumButton;

    private QControlPanelButton moreAlbumsButton;

    private QControlPanelButton wikiButton;

    private QControlPanelButton coversButton;

    public QControlPanelButton albumViewButton;

    private static final String LAYOUT = "h 100%, w 100%, center";

    @PostConstruct
    public void init() {
        setDefaults();
    }

    public void setDefaults() {
        setLayout(new MigLayout("ins 1cm 0 1cm 0, flowy, fill"));

        setOpaque(true);

        setBackground(ColorConstantsDark.ALBUM_PANEL);

        setupAlbumViewButton();

        setupPlaylistButton();

        setupLyricsButton();

        setupEditButton();

        setupAddButton();

        setupMoreAlbumsButton();

        setupWikiButton();

        setupCoversButton();

        add(albumViewButton, LAYOUT + ", cell 0 0");
        add(albumButton, LAYOUT + ", cell 0 1");
        add(lyricsButton, LAYOUT + ", cell 0 2");
        add(coversButton, LAYOUT + ", cell 0 3");
        add(editButton, LAYOUT + ", cell 0 4");
        add(moreAlbumsButton, LAYOUT + ", cell 0 5");
        add(wikiButton, LAYOUT + ", cell 0 6");
        // add(playerControlPanel, "south");
    }

    public void update(final Album album) {
        if (album instanceof JotifyAlbum) {
            remove(editButton);
            remove(addButton);
            add(addButton, LAYOUT + ", cell 0 4");
        }
        else {
            remove(editButton);
            remove(addButton);
            add(editButton, LAYOUT + ", cell 0 4");
        }

        repaint();
    }

    private void setupAlbumViewButton() {
        albumViewButton = new QControlPanelButton("Show/hide", ClassPathUtils
                .getIconFromClasspath("white/AlbumView.png"), SwingConstants.BOTTOM,
                SwingConstants.LEFT);
        albumViewButton.addActionListener(controlPanelListener);
        albumViewButton.setActionCommand(ControlPanelController.EVENT_TOGGLE_ALBUM_VIEW);
    }

    private void setupWikiButton() {
        wikiButton = new QControlPanelButton("Wiki", ClassPathUtils
                .getIconFromClasspath("white/Settings.png"), SwingConstants.BOTTOM,
                SwingConstants.LEFT);

        wikiButton.addActionListener(controlPanelListener);
        wikiButton.setActionCommand(ControlPanelController.EVENT_VIEW_WIKI);
    }

    private void setupCoversButton() {
        coversButton = new QControlPanelButton("Images", ClassPathUtils
                .getIconFromClasspath("white/Settings.png"), SwingConstants.BOTTOM,
                SwingConstants.LEFT);

        coversButton.addActionListener(controlPanelListener);
        coversButton.setActionCommand(ControlPanelController.EVENT_VIEW_COVERS);
    }

    private void setupMoreAlbumsButton() {
        moreAlbumsButton = new QControlPanelButton("All albums",
                getIconFromClasspath("white/MoreAlbums.png"), SwingConstants.BOTTOM,
                SwingConstants.LEFT);
        moreAlbumsButton.addActionListener(artistListener);
        moreAlbumsButton.setActionCommand(ArtistController.ACTION_GET_ARTIST_ALBUMS);
    }

    private void setupLyricsButton() {
        lyricsButton = new QControlPanelButton("Lyrics", getIconFromClasspath("white/Lyrics.png"),
                SwingConstants.BOTTOM, SwingConstants.LEFT);
        lyricsButton.addActionListener(this);
        lyricsButton.addActionListener(controlPanelListener);
        lyricsButton.setActionCommand(ControlPanelController.EVENT_VIEW_LYRICS);
    }

    private void setupPlaylistButton() {
        albumButton = new QControlPanelButton("Songs", getIconFromClasspath("white/SongTiles.png"),
                SwingConstants.BOTTOM, SwingConstants.LEFT);
        albumButton.addActionListener(this);
        albumButton.addActionListener(controlPanelListener);
        albumButton.setActionCommand(ControlPanelController.EVENT_VIEW_ALBUM);
        albumButton.activate();
    }

    private void setupEditButton() {
        editButton = new QControlPanelButton("Edit album",
                getIconFromClasspath("white/Settings.png"), SwingConstants.BOTTOM,
                SwingConstants.LEFT);
        editButton.addActionListener(editAlbumListener);
        editButton.setActionCommand(PlaylistPanel.EVENT_UPDATE_ALBUM_ID3);
    }

    private void setupAddButton() {
        addButton = new QControlPanelButton("Add album",
                getIconFromClasspath("white/Settings.png"), SwingConstants.BOTTOM,
                SwingConstants.LEFT);
        addButton.addActionListener(addAlbumListener);
        addButton.setActionCommand(AddAlbumController.EVENT_ADD_ALBUM);
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
        albumButton.inactivate();

        if (tab == null) {
            // Nada
        }
        else if (tab == Buttons.LYRICS) {
            lyricsButton.activate();
        }
        else if (tab == Buttons.ALBUMS) {
            albumButton.activate();
        }

        repaint();
        updateUI();
    }

    public void updateMainTab(Buttons tab) {
        coversButton.inactivate();
        editButton.inactivate();
        moreAlbumsButton.inactivate();
        wikiButton.inactivate();

        if (tab == null) {
            // NadaF
        }
        else if (tab == Buttons.IMAGES) {
            coversButton.activate();
        }
        else if (tab == Buttons.EDIT) {
            editButton.activate();
        }
        else if (tab == Buttons.PLAYLIST) {
            albumButton.activate();
        }
        else if (tab == Buttons.WIKI) {
            wikiButton.activate();
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

        Point2D start = new Point2D.Float(0, getHeight());
        Point2D end = new Point2D.Float(getWidth(), getHeight());

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist, gradient);
        g2d.setPaint(p);

        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
