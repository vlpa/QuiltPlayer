package com.quiltplayer.view.swing.panels;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.model.Artist;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.labels.ArtistNameButton;
import com.quiltplayer.view.swing.labels.StringOrCharLabel;
import com.quiltplayer.view.swing.listeners.ArtistListener;

public class AlfabeticArtistPane extends JPanel {
    private static final long serialVersionUID = 1L;

    private String character;

    private ArtistListener artistListener;

    public AlfabeticArtistPane() {
        super(new MigLayout("insets 0, wrap 1, fill"));

        setOpaque(false);
    }

    public void setup(final String character, final List<Artist> artists) {
        this.character = character;

        add(new StringOrCharLabel(character), "h 1cm, push, grow, north");

        for (final Artist artist : artists) {
            final ArtistNameButton button = new ArtistNameButton(artist, artistListener);
            button.setFont(FontFactory.getFont(16f));

            add(button, "h 0.65cm, dock north, left, gapy 1px");
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
}
