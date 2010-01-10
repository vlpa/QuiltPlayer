package com.quiltplayer.view.swing.panels.playlistpanels;

import javax.swing.BorderFactory;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.springframework.stereotype.Component;

import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.scrollbars.QScrollBar;

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
        super();

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

        JScrollPane lyricsScroller = new JScrollPane(lyricsArea);
        lyricsScroller.setVerticalScrollBar(new QScrollBar(JScrollBar.VERTICAL));
        lyricsScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        lyricsScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        lyricsScroller.setBorder(BorderFactory.createEmptyBorder());
        lyricsScroller.setWheelScrollingEnabled(true);
        lyricsScroller.getVerticalScrollBar().setUnitIncrement(VERTICAL_UNIT_INCRENET);

        add(lyricsScroller, "w 100%, gapx 25 25, gapy 25 25");
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
