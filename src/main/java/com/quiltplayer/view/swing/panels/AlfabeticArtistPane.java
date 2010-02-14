package com.quiltplayer.view.swing.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.model.Artist;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.labels.ArtistLabel;
import com.quiltplayer.view.swing.labels.StringOrCharLabel;
import com.quiltplayer.view.swing.listeners.ArtistListener;

public class AlfabeticArtistPane extends JPanel {
    private static final long serialVersionUID = 1L;

    private String character;

    private ArtistListener artistListener;

    public AlfabeticArtistPane() {
        super(new MigLayout("insets 0, wrap 1"));

        setOpaque(false);
    }

    public void setup(final String character, final List<Artist> artists) {
        this.character = character;

        add(new StringOrCharLabel(character), "w 4cm!, h 0.75cm");

        for (final Artist artist : artists) {
            final ArtistLabel label = new ArtistLabel(artist);
            label.addActionListener(artistListener);
            label.setFont(FontFactory.getSansFont(14f));

            add(label, "wmax 4cm, h 0.4cm");
        }
    }

    public void setActive() {
        setForeground(Color.WHITE);
    }

    public void setSelected() {
        if (getForeground() != Color.WHITE) {
            setForeground(Color.LIGHT_GRAY);
        }
    }

    public void setInactive() {
        setForeground(Color.DARK_GRAY);
    }

    public void setInactive2() {
        if (getForeground() != Color.WHITE) {
            setForeground(Color.DARK_GRAY);
        }
    }

    public String getCharacter() {
        return character;
    }

    public void addActionListener(ArtistListener artistListener) {
        this.artistListener = artistListener;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(g2d);
    }
}
