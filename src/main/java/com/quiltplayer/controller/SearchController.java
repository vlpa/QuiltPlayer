package com.quiltplayer.controller;

import java.awt.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.core.repo.spotify.JotifyRepository;
import com.quiltplayer.view.swing.ActiveView;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.listeners.SearchListener;
import com.quiltplayer.view.swing.views.View;
import com.quiltplayer.view.swing.views.impl.SearchView;

import de.felixbruns.jotify.media.Result;

/**
 * Controller for searches.
 * 
 * @author Vlado Palczynski
 */
@Controller
public class SearchController implements SearchListener {

    public static final String ACTION_GET_ARTIST = "get.artist";

    @Autowired
    private JotifyRepository jotifyRepository;

    @Autowired
    private QuiltPlayerFrame frame;

    @Autowired
    private View searchView;

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (SearchView.EVENT_SEARCH == actionCommand) {
            String query = (String) e.getSource();

            if (StringUtils.isNotBlank(query)) {
                Result result = jotifyRepository.getInstance().search(query);
                ((SearchView) searchView).setResult(result);

                if (frame.getCurrentView() == ActiveView.SEARCH_VIEW)
                    frame.updateUI();
            }

        }
    }
}