package com.quiltplayer.view.swing.views.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.core.factory.SpotifyObjectFactory;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.labels.AlbumSearchLabel;
import com.quiltplayer.view.swing.labels.SpotifyArtistLabel;
import com.quiltplayer.view.swing.labels.TrackSearchLabel;
import com.quiltplayer.view.swing.listeners.ArtistListener;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.listeners.SearchListener;
import com.quiltplayer.view.swing.panels.QPanel;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.textfields.QTextField;
import com.quiltplayer.view.swing.views.View;

import de.felixbruns.jotify.media.Album;
import de.felixbruns.jotify.media.Artist;
import de.felixbruns.jotify.media.Result;

/**
 * Search collection + Spotify
 * 
 * @author Vlado Palczynski
 * 
 */
@org.springframework.stereotype.Component
public class SearchView implements Serializable, View {

    private static final long serialVersionUID = 1L;

    public static final String EVENT_SEARCH = "search";

    private JPanel panel;

    private JPanel searchPanel;

    private JTextField searchField;

    private JButton searchButton;

    @Autowired
    private SearchListener searchListener;

    @Autowired
    private ArtistListener artistListener;

    @Autowired
    private ChangeAlbumListener changeAlbumListener;

    private Result result;

    private FocusListener focusListener = new com.quiltplayer.view.swing.listeners.FocusListener();

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.view.components.View#close()
     */
    @Override
    public void close() {
    }

    @PostConstruct
    public void init() {
        setupSearchField();
        setupSearchButton();
        // addKeyboardButton();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.view.components.View#getUI()
     */
    @Override
    public Component getUI() {
        panel = new QPanel();
        panel.setOpaque(true);
        panel.addFocusListener(focusListener);

        MigLayout layout = new MigLayout(
                "wrap 3, alignx center, aligny center, fillx, filly, gapy 50");
        panel.setLayout(layout);
        panel.setBackground(Configuration.getInstance().getColorConstants().getBackground());

        searchPanel = new JPanel(new MigLayout("insets 0, top"));
        searchPanel.setBackground(Configuration.getInstance().getColorConstants().getBackground());

        searchPanel.add(searchField, "center, w 6cm, gapy 15");
        searchPanel.add(searchButton, "left, gapy 15");

        panel.add(searchPanel, "top, span 4, alignx center");

        if (result != null) {
            addArtists(panel);
            addAlbums(panel);
            addTracks(panel);
        }

        QScrollPane pane = new QScrollPane(panel);

        searchField.requestFocus();

        return pane;
    }

    private void setupSearchField() {
        searchField = new QTextField(true);

        KeyListener k = new KeyAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchListener.actionPerformed(new ActionEvent(searchField.getText(), 0,
                            EVENT_SEARCH));
                }
            }

        };

        searchField.addKeyListener(k);
    }

    private void addArtists(JPanel panel) {
        JPanel artists = new QPanel(new MigLayout("insets 0, wrap 1"));
        artists.setBackground(Configuration.getInstance().getColorConstants().getBackground());
        SpotifyArtistLabel label = null;

        artists.add(addHeader(" Artists"), "w 100%, h 18");

        if (!result.getArtists().isEmpty()) {
            for (Artist artist : result.getArtists()) {
                label = new SpotifyArtistLabel(SpotifyObjectFactory.getArtist(artist));
                label.addActionListener(artistListener);

                artists.add(label, "left");
            }
        }

        panel.add(artists, "top, wmin 10%, w 30%, wmax 30%, alignx center, gapx 1% 1%");
    }

    private JLabel addHeader(String title) {
        JLabel charLabel = new JLabel();

        Font font = FontFactory.getFont(15f);

        charLabel.setFont(font);
        charLabel.setForeground(Configuration.getInstance().getColorConstants()
                .getArtistViewCharColor());
        charLabel.setText(title);
        charLabel.setBackground(Configuration.getInstance().getColorConstants()
                .getArtistViewCharBackground());
        charLabel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50)));
        charLabel.setOpaque(true);

        return charLabel;
    }

    private void addAlbums(JPanel panel) {
        JPanel albums = new QPanel(new MigLayout("insets 0, top, wrap 1"));
        albums.setBackground(Configuration.getInstance().getColorConstants().getBackground());

        albums.add(addHeader(" Albums"), "w 100%, h 18");

        if (!result.getAlbums().isEmpty()) {
            for (Album album : result.getAlbums()) {
                AlbumSearchLabel label = new AlbumSearchLabel(SpotifyObjectFactory.getAlbum(album));
                label.addActionListener(changeAlbumListener);

                albums.add(label);
            }
        }

        panel.add(albums, "top, wmin 10%, w 30%, wmax 30%, alignx center");
    }

    private void addTracks(JPanel panel) {
        JPanel tracks = new QPanel(new MigLayout("insets 0, wrap 1"));
        tracks.setBackground(Configuration.getInstance().getColorConstants().getBackground());
        TrackSearchLabel label = null;

        tracks.add(addHeader(" Tracks"), "w 100%, h 18");

        if (!result.getTracks().isEmpty()) {
            for (de.felixbruns.jotify.media.Track track : result.getTracks()) {
                label = new TrackSearchLabel(SpotifyObjectFactory.getTrack(track));
                label.addActionListener(changeAlbumListener);

                tracks.add(label, "left");
            }
        }

        panel.add(tracks, "top, wmin 10%, w 30%, wmax 30%, alignx center, gapx 1% 1%");
    }

    private void setupSearchButton() {
        searchButton = new QButton("Search Spotify");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchListener.actionPerformed(new ActionEvent(searchField.getText(), 0,
                        EVENT_SEARCH));
            }
        });
    }

    /**
     * @param result
     *            the result to set
     */
    public final void setResult(Result result) {
        this.result = result;
    }
}