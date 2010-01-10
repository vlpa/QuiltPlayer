package com.quiltplayer.controller;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.core.scanner.CoverScanner;
import com.quiltplayer.core.scanner.Id3Scanner;
import com.quiltplayer.core.scanner.ScanningEvent;
import com.quiltplayer.core.scanner.ScanningEvent.Scanner;
import com.quiltplayer.core.scanner.ScanningEvent.Status;
import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.listeners.ScanningListener;
import com.quiltplayer.view.swing.panels.playlistpanels.AlbumPlaylistPanel;
import com.quiltplayer.view.swing.views.impl.ConfigurationView;
import com.quiltplayer.view.swing.views.impl.EditAlbumView;

/**
 * Controller for all scanning.
 * 
 * @author Vlado Palczynski
 */
@Controller
public class ScanningController implements ScanningListener {

    private Logger log = Logger.getLogger(ScanningController.class);

    public static final String EVENT_RESCAN_ALBUM = "rescan.album";

    @Autowired
    private QuiltPlayerFrame frame;

    @Autowired
    private Id3Scanner collectionScanner;

    @Autowired
    private CoverScanner coverScanner;

    @Autowired
    private AlbumPlaylistPanel playlistPanel;

    @Autowired
    private EditAlbumView editAlbumView;

    @Autowired
    private ConfigurationView configurationView;

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {

        if (e.getActionCommand().equals(ConfigurationView.EVENT_SCAN_COVERS)) {
            coverScanner.scanCovers();
        }
        else if (e.getActionCommand().equals(ConfigurationView.EVENT_CANCEL_SCAN_COVERS)) {
            coverScanner.cancelScanCovers();
        }
        else if (e.getActionCommand().equals(ConfigurationView.EVENT_UPDATE_COLLECTION)) {
            collectionScanner.scanCollection();
        }
        else if (e.getActionCommand().equals(ConfigurationView.EVENT_CANCEL_UPDATE_COLLECTION)) {
            collectionScanner.cancelScanCollection();
        }
        else if (e.getActionCommand().equals(EVENT_RESCAN_ALBUM)) {
            Album album = (Album) e.getSource();

            if (album != null) {
                log.debug("Rescanning album " + album.getTitle());

                coverScanner.scanCovers(album);
            }
            else {
                // Error
                log.error("Album does not exist.");
            }
        }
    }

    /*
     * @see org.quiltplayer.view.listeners.ScanningListener#scannerEvent(org.quiltplayer
     * .core.scanner.ScanningEvent)
     */
    @Override
    public final void scannerEvent(final ScanningEvent e) {
        if (Scanner.ID3 == e.getScanner()) {
            if (Status.STARTED == e.getStatus()) {
                configurationView.disableUpdateButton();
            }
            else if (Status.DONE == e.getStatus()) {
                configurationView.enableUpdateButton();
                frame.updateUI();
            }
        }
        else if (Scanner.COVERS == e.getScanner()) {
            if (Status.STARTED == e.getStatus()) {
                configurationView.disableScanButton();
            }
            else if (Status.DONE == e.getStatus()) {
                configurationView.enableScanButton();
                frame.updateUI();
                playlistPanel.updateAlbumUI();
            }
        }
        else if (Scanner.COVER == e.getScanner()) {
            if (Status.STARTED == e.getStatus()) {
                editAlbumView.disableRescanButton();
            }
            else if (Status.DONE == e.getStatus()) {
                editAlbumView.enableRescanButton();
                frame.updateUI();
                playlistPanel.updateAlbumUI();
            }
        }
    }
}