package com.quiltplayer.view.swing.panels.components;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.quiltplayer.external.wiki.WikipediaService;
import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.scrollbars.QScrollBar;

/**
 * Handles the lyrics view in the PlaylistPane.
 * 
 * @author Vlado Palczynski
 */
public class WikiComponent extends QPlaylistComponent {

    private static final int VERTICAL_UNIT_INCRENET = 20;

    private JScrollPane wikiScroller;

    public JPanel create(Album album) {
        WikipediaService ws = new WikipediaService();

        JEditorPane pane = null;
        try {
            pane = new JEditorPane(ws.getWikiContentForPageName(album.getArtist().getArtistName()
                    .getName()));
            pane.setForeground(ColorConstantsDark.PLAYLIST_LYRICS_COLOR);
            pane.setBackground(ColorConstantsDark.ARTISTS_PANEL_BACKGROUND);
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

        wikiScroller = new JScrollPane(pane);
        wikiScroller.setVerticalScrollBar(new QScrollBar(JScrollBar.VERTICAL));
        wikiScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        wikiScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        wikiScroller.setBorder(BorderFactory.createEmptyBorder());
        wikiScroller.setWheelScrollingEnabled(true);
        wikiScroller.getVerticalScrollBar().setUnitIncrement(VERTICAL_UNIT_INCRENET);

        mainPanel.add(wikiScroller, "w 100%");

        return mainPanel;
    }
}
