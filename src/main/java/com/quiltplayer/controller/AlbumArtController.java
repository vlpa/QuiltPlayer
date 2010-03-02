package com.quiltplayer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.views.impl.AlbumArtView;

/**
 * Controller for adding non-ID3 albums.
 * 
 * @author Vlado Palczynski
 */

@Controller()
public class AlbumArtController implements ActionListener {

    public static final String EVENT_UPDATE_ALBUM = "update.album";

    private Logger log = Logger.getLogger(AlbumArtController.class);

    @Autowired
    private AlbumArtView albumArtView;

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {

        if (EVENT_UPDATE_ALBUM == e.getActionCommand()) {
            final Album album = (Album) e.getSource();
            albumArtView.setAlbum(album);
        }
    }
}
