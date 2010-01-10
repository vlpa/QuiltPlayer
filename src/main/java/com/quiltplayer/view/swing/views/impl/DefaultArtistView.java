package com.quiltplayer.view.swing.views.impl;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.core.repo.ArtistRepository;
import com.quiltplayer.model.Artist;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.listeners.ArtistListener;
import com.quiltplayer.view.swing.panels.AlfabeticArtistPane;
import com.quiltplayer.view.swing.views.AbstractView;
import com.quiltplayer.view.swing.views.ArtistView;

/**
 * My implementation of the album view.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component
public class DefaultArtistView extends AbstractView implements ArtistView {

    private static final long serialVersionUID = 1L;

    private static final int VERTICAL_UNIT_INCRENET = 40;

    @Autowired
    private ArtistListener listener;

    @Autowired
    private ArtistRepository artistRepository;

    private JPanel panel;

    /*
     * @see org.coverok.gui.components.AlbumView#close()
     */
    @Override
    public void close() {
        panel = null;
    }

    /*
     * @see org.coverok.gui.components.AlbumView#getUI()
     */
    @Override
    public Component getUI() {
        return getAlfabeticArtistComponent();
    }

    private Component getAlfabeticArtistComponent() {
        final MigLayout layout = new MigLayout("wrap "
                + Configuration.getInstance().getArtistColumns()
                + ", alignx center, flowx");

        panel = new JPanel();
        panel.setLayout(layout);
        panel.setOpaque(true);
        panel.setAlignmentX(Component.BOTTOM_ALIGNMENT);
        panel.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        if (artistRepository.getArtistsByChars() != null) {
            for (String character : artistRepository.getArtistsByChars().keySet()) {
                List<Artist> albumSet = artistRepository.getArtistsByChars().get(character);

                AlfabeticArtistPane p = new AlfabeticArtistPane();
                p.addActionListener(listener);
                p.setup(character, albumSet);

                panel.add(p, "aligny top, gapy 30, gapx 10");
            }
        }

        return getScrollPane(panel, VERTICAL_UNIT_INCRENET);
    }
}
