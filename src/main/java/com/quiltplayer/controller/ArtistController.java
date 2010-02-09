package com.quiltplayer.controller;

import java.awt.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.view.swing.ActiveView;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.listeners.ArtistListener;
import com.quiltplayer.view.swing.panels.PlaylistPanel;
import com.quiltplayer.view.swing.views.ListView;
import com.quiltplayer.view.swing.views.impl.DefaultAlbumView;

/**
 * Controller for artist views.
 * 
 * @author Vlado Palczynski
 */
@Controller()
public class ArtistController implements ArtistListener {
    public static final String EVENT_DELETE_ARTIST = "delete.artist";

    public static final String ACTION_GET_ARTIST_ALBUMS = "get.artist";

    @Autowired
    private QuiltPlayerFrame frame;

    @Autowired
    @Qualifier("defaultAlbumView")
    private ListView<Album> albumView;

    @Autowired
    private ArtistStorage artistStorage;

    @Autowired
    private PlaylistPanel playlistPanel;

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (ACTION_GET_ARTIST_ALBUMS == actionCommand) {
            Artist artist;
            if (e.getSource() instanceof Artist)
                artist = (Artist) e.getSource();
            else
                artist = playlistPanel.getCurrentSongLabel().getSong().getAlbum().getArtist();

            for (Album album : artist.getAlbums()) {
                album.setArtist(artist);
            }

            albumView.setList(artist.getAlbums());
            ((DefaultAlbumView) albumView).setArtist(artist);
            frame.updateUI(ActiveView.ALBUM_VIEW);

        }
        else if (actionCommand == EVENT_DELETE_ARTIST) {
            artistStorage.delete((Artist) e.getSource());

            frame.updateUI(ActiveView.ALFABETIC_ARTISTS_VIEW);
        }
    }
}