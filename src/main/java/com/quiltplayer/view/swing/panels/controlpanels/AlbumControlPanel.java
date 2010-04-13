package com.quiltplayer.view.swing.panels.controlpanels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.quiltplayer.view.swing.panels.MainTabs;
import com.quiltplayer.view.swing.panels.PlaylistPanel;

/**
 * Control panel for album.
 * 
 * @author Vlado Palczynski
 */
/**
 * @author Vlado Palczynski
 */
@Component
public class AlbumControlPanel extends JPanel implements ActionListener {

    public enum Buttons {
        LYRICS, EDIT, PLAYLIST, ADD
    };

    private static final long serialVersionUID = 1L;

    private Color[] gradient = { new Color(30, 30, 30), new Color(0, 0, 0) };

    private float[] dist = { 0.0f, 1.0f };

    @Autowired
    private EditAlbumListener editAlbumListener;

    @Autowired
    private ControlPanelListener controlPanelListener;

    @Autowired
    private ArtistListener artistListener;

    @Autowired
    private AddAlbumListener addAlbumListener;

    private QControlPanelButton lyricsButton;

    private QControlPanelButton editButton;

    private QControlPanelButton addButton;

    private QControlPanelButton albumButton;

    private QControlPanelButton moreAlbumsButton;

    private QControlPanelButton wikiButton;

    private QControlPanelButton coversButton;

    private static final String LAYOUT = "h 100%, w 100%, center";

    @PostConstruct
    public void init() {
        setDefaults();
    }

    public void setDefaults() {
        setLayout(new MigLayout("ins 0.5cm 0 0.5cm 0, flowy, fill"));

        setOpaque(false);

        setBackground(ColorConstantsDark.ALBUM_PANEL);

        setupPlaylistButton();

        setupLyricsButton();

        setupEditButton();

        setupAddButton();

        setupMoreAlbumsButton();

        setupWikiButton();

        setupCoversButton();

        add(albumButton, LAYOUT + ", cell 0 1");
        // add(lyricsButton, LAYOUT + ", cell 0 2");
        add(editButton, LAYOUT + ", cell 0 3");
        add(coversButton, LAYOUT + ", cell 0 4");
        add(moreAlbumsButton, LAYOUT + ", cell 0 5");
        add(wikiButton, LAYOUT + ", cell 0 6");
    }

    public void update(final Album album) {
        if (album instanceof JotifyAlbum) {
            remove(editButton);
            remove(addButton);
            add(addButton, LAYOUT + ", cell 0 3");
        }
        else {
            remove(editButton);
            remove(addButton);
            add(editButton, LAYOUT + ", cell 0 3");
        }

        repaint();
    }

    private void setupWikiButton() {
        wikiButton = new QControlPanelButton("Wiki", ClassPathUtils
                .getIconFromClasspath("white/Wikipedia.png"), SwingConstants.BOTTOM,
                SwingConstants.LEFT);

        wikiButton.addActionListener(controlPanelListener);
        wikiButton.setActionCommand(ControlPanelController.EVENT_VIEW_WIKI);
    }

    private void setupCoversButton() {
        coversButton = new QControlPanelButton("Images", ClassPathUtils
                .getIconFromClasspath("white/Images.png"), SwingConstants.BOTTOM,
                SwingConstants.LEFT);

        coversButton.addActionListener(controlPanelListener);
        coversButton.setActionCommand(ControlPanelController.EVENT_VIEW_COVERS);
    }

    private void setupMoreAlbumsButton() {
        moreAlbumsButton = new QControlPanelButton("All albums", ClassPathUtils
                .getIconFromClasspath("white/MoreAlbums.png"), SwingConstants.BOTTOM,
                SwingConstants.LEFT);
        moreAlbumsButton.addActionListener(artistListener);
        moreAlbumsButton.setActionCommand(ArtistController.ACTION_GET_ARTIST_ALBUMS);
    }

    private void setupLyricsButton() {
        lyricsButton = new QControlPanelButton("Lyrics", ClassPathUtils
                .getIconFromClasspath("white/Lyrics.png"), SwingConstants.BOTTOM,
                SwingConstants.LEFT);
        lyricsButton.addActionListener(this);
        lyricsButton.addActionListener(controlPanelListener);
        lyricsButton.setActionCommand(ControlPanelController.EVENT_VIEW_LYRICS);
    }

    private void setupPlaylistButton() {
        albumButton = new QControlPanelButton("Songs", ClassPathUtils
                .getIconFromClasspath("white/SongTiles.png"), SwingConstants.BOTTOM,
                SwingConstants.LEFT);
        albumButton.addActionListener(this);
        albumButton.addActionListener(controlPanelListener);
        albumButton.setActionCommand(ControlPanelController.EVENT_VIEW_SONGS);
    }

    private void setupEditButton() {
        editButton = new QControlPanelButton("Edit album", ClassPathUtils
                .getIconFromClasspath("white/EditAlbum.png"), SwingConstants.BOTTOM,
                SwingConstants.LEFT);
        editButton.addActionListener(editAlbumListener);
        editButton.setActionCommand(PlaylistPanel.EVENT_UPDATE_ALBUM_ID3);
    }

    private void setupAddButton() {
        addButton = new QControlPanelButton("Add album", ClassPathUtils
                .getIconFromClasspath("white/Add.png"), SwingConstants.BOTTOM, SwingConstants.LEFT);
        addButton.addActionListener(addAlbumListener);
        addButton.setActionCommand(AddAlbumController.EVENT_ADD_ALBUM);
    }

    public void updateSingleTab(Buttons tab) {
        lyricsButton.inactivate();
        albumButton.inactivate();
        editButton.inactivate();

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

    public void updateTab(MainTabs tab) {
        coversButton.inactivate();
        editButton.inactivate();
        moreAlbumsButton.inactivate();
        wikiButton.inactivate();

        if (tab == null) {
            // Nada
        }
        else if (tab == MainTabs.IMAGES) {
            coversButton.activate();
        }
        else if (tab == MainTabs.ALL_ALBUMS) {
            moreAlbumsButton.activate();
        }
        else if (tab == MainTabs.WIKI) {
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

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

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
