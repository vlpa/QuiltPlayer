package com.quiltplayer.view.swing.panels.playlistpanels;

import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.jxlayer.JXLayer;
import org.springframework.stereotype.Component;

import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.panels.QScrollPane;

/**
 * Display the lyrics of the playing song.
 * 
 * @author Vlado Palczynski
 * 
 */
@Component
public class LyricsPlaylistPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int VERTICAL_UNIT_INCRENET = 20;

    private JTextArea lyricsArea;

    public LyricsPlaylistPanel() {
        super(new MigLayout("insets 15, wrap 1, alignx center, aligny center"));
        setOpaque(true);
        setBackground(ColorConstantsDark.PLAYLIST_BACKGROUND);

        setupTextArea();
    }

    private void setupTextArea() {
        lyricsArea = new JTextArea();
        lyricsArea.setOpaque(true);
        lyricsArea.setBackground(ColorConstantsDark.PLAYLIST_BACKGROUND);
        lyricsArea.setForeground(ColorConstantsDark.PLAYLIST_LYRICS_COLOR);
        lyricsArea.setText("No lyrics...");
        lyricsArea.setCaretPosition(0);
        lyricsArea.setFont(FontFactory.getFont(13f));
        lyricsArea.setCaretPosition(0);
        lyricsArea.setEditable(false);
        lyricsArea.setWrapStyleWord(true);
        lyricsArea.setLineWrap(true);
        lyricsArea.setDragEnabled(false);

        JScrollPane lyricsScroller = new QScrollPane(lyricsArea);
        lyricsScroller.setBorder(BorderFactory.createEmptyBorder());
        lyricsScroller.getVerticalScrollBar().setUnitIncrement(VERTICAL_UNIT_INCRENET);

        JXLayer<JScrollPane> jx = new JXLayer<JScrollPane>(lyricsScroller, new JScrollPaneLayerUI());

        add(jx, "w 100%");
    }

    /**
     * @param lyrics
     *            the lyrics to set
     */
    public final void setLyrics(String lyrics) {
        lyricsArea.setText(lyrics);
        lyricsArea.setLocation(0, 0);

        repaint();
        updateUI();
    }

}
