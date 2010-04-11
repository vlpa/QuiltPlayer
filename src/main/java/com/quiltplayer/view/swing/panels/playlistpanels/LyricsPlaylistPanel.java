package com.quiltplayer.view.swing.panels.playlistpanels;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.FontFactory;

/**
 * Display the lyrics of the playing song.
 * 
 * @author Vlado Palczynski
 * 
 */
@Component
public class LyricsPlaylistPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextArea lyricsArea;

    public LyricsPlaylistPanel() {
        super(new MigLayout("insets 15, fill,  alignx center, aligny center"));
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
        lyricsArea.setEditable(true);
        lyricsArea.setLineWrap(true);
        lyricsArea.setFocusable(false);
        lyricsArea.setWrapStyleWord(true);
        lyricsArea
                .setDoubleBuffered(Configuration.getInstance().getUiProperties().isDoubleBuffer());

        add(lyricsArea, "w 100%");
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
