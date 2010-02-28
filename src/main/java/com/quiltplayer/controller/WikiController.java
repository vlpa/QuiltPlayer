package com.quiltplayer.controller;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;

import com.quiltplayer.core.player.tasks.WikiTask;
import com.quiltplayer.external.wiki.WikipediaService;
import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.listeners.WikiListener;
import com.quiltplayer.view.swing.views.impl.WikiView;

/**
 * Controller for changing albums.
 * 
 * @author Vlado Palczynski
 */
@Controller
public class WikiController implements WikiListener {

    private Logger log = Logger.getLogger(WikiController.class);

    public static final String EVENT_UPDATE_WIKI = "update.wiki";

    @Autowired
    private WikiView wikiView;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private WikipediaService wikipediaService;

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (e.getActionCommand().equals(EVENT_UPDATE_WIKI)) {
            Album album = (Album) e.getSource();

            taskExecutor.execute(new WikiTask(album, wikipediaService, wikiView));
        }
    }
}
