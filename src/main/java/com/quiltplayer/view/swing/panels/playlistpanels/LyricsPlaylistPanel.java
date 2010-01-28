package com.quiltplayer.view.swing.panels.playlistpanels;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.panels.QScrollPane;

/**
 * Display the lyrics of the playing song.
 * 
 * @author Vlado Palczynski
 * 
 */
@Component
public class LyricsPlaylistPanel extends AbstractPlaylistPanel {

    private static final long serialVersionUID = 1L;

    private static final int VERTICAL_UNIT_INCRENET = 20;

    private JTextArea lyricsArea;

    public LyricsPlaylistPanel() {
        super(new MigLayout("insets 15, wrap 1, alignx center, aligny center"));

        setupTextArea();
    }

    private void setupTextArea() {
        lyricsArea = new JTextArea();
        lyricsArea.setText("No lyrics...");
        lyricsArea.setCaretPosition(0);
        lyricsArea.setFont(FontFactory.getFont(13f));
        lyricsArea.setCaretPosition(0);
        lyricsArea.setEditable(false);
        lyricsArea.setWrapStyleWord(true);
        lyricsArea.setLineWrap(true);
        lyricsArea.setForeground(ColorConstantsDark.PLAYLIST_LYRICS_COLOR);
        lyricsArea.setBackground(ColorConstantsDark.ARTISTS_PANEL_BACKGROUND);
        lyricsArea.setOpaque(false);

        JScrollPane lyricsScroller = new QScrollPane(lyricsArea);
        lyricsScroller.setBorder(BorderFactory.createEmptyBorder());
        lyricsScroller.setWheelScrollingEnabled(true);
        lyricsScroller.getVerticalScrollBar().setUnitIncrement(VERTICAL_UNIT_INCRENET);

        add(lyricsScroller, "w 100%");
    }

    /**
     * @param lyrics
     *            the lyrics to set
     */
    public final void setLyrics(String lyrics) {
        lyricsArea.setText(lyrics);

        SwingUtilities.updateComponentTreeUI(this);
    }

}
