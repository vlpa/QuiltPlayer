package com.quiltplayer.controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.core.storage.Storage;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.listeners.SelectionListener;
import com.quiltplayer.view.swing.panels.controlpanels.AlfabeticControlPane;
import com.quiltplayer.view.swing.views.impl.QuiltView;

@Controller
public class SelectionController implements SelectionListener {

    public static final String ALBUMS = "albums";

    public static final String ARTIST = "artist";

    @Autowired
    private QuiltPlayerFrame frame;

    @Autowired
    private QuiltView quiltView;

    @Autowired
    private ArtistStorage artistStorage;

    @Autowired
    private Storage storage;

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO This can be solved better ;)
        String type = ((List<String>) e.getSource()).get(0);
        String s = e.getActionCommand();

        final Collection<Artist> artists = artistStorage.getArtists();
        List<Album> newList = new ArrayList<Album>();

        if (type == ALBUMS) {

            List<Album> albums = storage.getAlbums(artists);

            if (s.equalsIgnoreCase(AlfabeticControlPane.ALL))
                newList = albums;
            else if (s.equalsIgnoreCase(AlfabeticControlPane.NUMERIC))
                for (Album album : albums) {
                    if (album.getTitle().substring(0, 1).matches("[0-9]")) {
                        newList.add(album);
                    }
                }
            else {
                for (Album album : albums) {
                    if (album.getTitle().toLowerCase().startsWith(s.toLowerCase())) {
                        newList.add(album);
                    }
                }
            }

            Collections.sort(newList);
        }
        else if (type == ARTIST) {
            List<Artist> newArtistList = new ArrayList<Artist>();

            if (s.equalsIgnoreCase(AlfabeticControlPane.ALL))
                newList = storage.getAlbums(artists);
            else if (s.equalsIgnoreCase(AlfabeticControlPane.NUMERIC)) {
                for (Artist artist : artists) {
                    if (artist.getArtistName().getName().substring(0, 1).matches("[0-9]")) {
                        newArtistList.add(artist);
                    }
                }

                newList = storage.getAlbums(newArtistList);
            }
            else {
                for (Artist artist : artists) {
                    if (artist.getArtistName().getName().toLowerCase().startsWith(s.toLowerCase())) {
                        newArtistList.add(artist);
                    }
                }

                newList = storage.getAlbums(newArtistList);
            }

            Collections.sort(newList, new Comparator<Album>() {

                /*
                 * (non-Javadoc)
                 * 
                 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
                 */
                @Override
                public int compare(Album o1, Album o2) {
                    return o1.getArtist().getArtistName().getName().compareToIgnoreCase(
                            o2.getArtist().getArtistName().getName());
                }

            });
        }

        quiltView.setList(newList);

        frame.updateUI();
    }
}
