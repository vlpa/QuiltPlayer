package com.quiltplayer.view.swing.views.impl;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.cmc.shared.swing.FlowWrapLayout;
import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.core.repo.ArtistRepository;
import com.quiltplayer.model.Artist;
import com.quiltplayer.view.swing.listeners.ArtistListener;
import com.quiltplayer.view.swing.panels.AlfabeticArtistPane;
import com.quiltplayer.view.swing.views.AbstractView;
import com.quiltplayer.view.swing.views.ArtistView;

/**
 * My implementation of the artist view.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component
public class DefaultArtistView extends AbstractView implements ArtistView {

    private static final long serialVersionUID = 1L;

    private static final int VERTICAL_UNIT_INCRENET = 40;

    @Autowired
    private ArtistListener artistListener;

    @Autowired
    private ArtistRepository artistRepository;

    /*
     * @see org.coverok.gui.components.AlbumView#close()
     */
    @Override
    public void close() {
    }

    /*
     * @see org.coverok.gui.components.AlbumView#getUI()
     */
    @Override
    public Component getUI() {
        return getAlfabeticArtistComponent();
    }

    private Component getAlfabeticArtistComponent() {

        final JPanel panel = new JPanel();
        panel.setLayout(new FlowWrapLayout(20, 20, 20, 20));
        panel.setOpaque(true);

        if (artistRepository.getArtistsByChars() != null) {
            for (String character : artistRepository.getArtistsByChars().keySet()) {
                List<Artist> albumSet = artistRepository.getArtistsByChars().get(character);

                AlfabeticArtistPane p = new AlfabeticArtistPane();
                p.addActionListener(artistListener);
                p.setup(character, albumSet);

                panel.add(p, "aligny top, gapy 30, gapx 10");
            }
        }

        final JPanel wrapper = new JPanel(new MigLayout("insets 0, fill"));
        wrapper.add(panel, "w 90%, h 100%, alignx right, aligny center");

        return getScrollPane(wrapper, VERTICAL_UNIT_INCRENET);
    }
}
