package com.quiltplayer.controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.core.storage.Storage;
import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.listeners.SelectionListener;
import com.quiltplayer.view.swing.panels.controlpanels.AlfabeticControlPane;
import com.quiltplayer.view.swing.views.impl.QuiltView;

@Controller
public class SelectionController implements SelectionListener {

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
        String s = e.getActionCommand();

        List<Album> albums = storage.getAlbums(artistStorage.getArtists());

        List<Album> newList = new ArrayList<Album>();

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

        quiltView.setList(newList);

        frame.updateUI();
    }
}
