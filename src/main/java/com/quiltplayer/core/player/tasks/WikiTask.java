package com.quiltplayer.core.player.tasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.quiltplayer.external.wiki.WikipediaService;
import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.views.impl.WikiView;

public class WikiTask implements Runnable {

    private WikipediaService wikipediaService;

    private Album album;

    private WikiView wikiView;

    public WikiTask(final Album album, final WikipediaService wikipediaService,
            final WikiView wikiView) {

        this.album = album;
        this.wikipediaService = wikipediaService;
        this.wikiView = wikiView;
    }

    public void run() {

        String content = "";
        try {
            content = wikipediaService.getWikiContentForPageName(album.getArtist().getArtistName()
                    .getName());
        }
        catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(content);

        wikiView.setContent(content);
    }
}
