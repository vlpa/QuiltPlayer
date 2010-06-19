package com.quiltplayer.view.swing.panels.utility;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.util.MigProperties;

/**
 * Display the lyrics of the playing song.
 * 
 * @author Vlado Palczynski
 * 
 */
@Component
public class LyricsUtilityPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextArea lyricsArea;

    public LyricsUtilityPanel() {
        super(new MigLayout("ins 0, fill, center, w " + MigProperties.LYRICS_PANEL_WIDTH + "cm!"));
        setOpaque(true);
        setBackground(ColorConstantsDark.PLAYLIST_BACKGROUND);

        setupTextArea();
    }

    private void setupTextArea() {
        lyricsArea = new JTextArea();
        lyricsArea.setOpaque(false);
        lyricsArea.setForeground(ColorConstantsDark.PLAYLIST_LYRICS_COLOR);
        lyricsArea.setText("No lyrics...");
        lyricsArea.setCaretPosition(0);
        lyricsArea.setFont(FontFactory.getFont(13f));
        lyricsArea.setEditable(false);
        lyricsArea.setLineWrap(true);
        lyricsArea.setFocusable(false);
        lyricsArea.setWrapStyleWord(true);
        lyricsArea.setDoubleBuffered(Configuration.getInstance().getUiProperties().isDoubleBuffer());

        add(lyricsArea, "w 100%");
    }

    /**
     * @param lyrics
     *            the lyrics to set
     */
    public final void setLyrics(String lyrics) {
        lyricsArea.setText(lyrics);
        lyricsArea.setLocation(0, 0);

        updateUI();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

        super.paintComponent(g);
    }
}
