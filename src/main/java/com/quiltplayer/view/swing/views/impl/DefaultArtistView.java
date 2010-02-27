package com.quiltplayer.view.swing.views.impl;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.cmc.shared.swing.FlowWrapLayout;
import org.jdesktop.jxlayer.JXLayer;
import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.core.repo.ArtistRepository;
import com.quiltplayer.model.Artist;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.listeners.ArtistListener;
import com.quiltplayer.view.swing.panels.AlfabeticArtistPane;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.views.ArtistView;

/**
 * My implementation of the artist view.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component
public class DefaultArtistView implements ArtistView {

    private static final long serialVersionUID = 1L;

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
        panel.setLayout(new FlowWrapLayout(10, 10, 10, 10));
        panel.setOpaque(true);

        if (artistRepository.getArtistsByChars() != null) {
            for (String character : artistRepository.getArtistsByChars().keySet()) {
                List<Artist> albumSet = artistRepository.getArtistsByChars().get(character);

                AlfabeticArtistPane p = new AlfabeticArtistPane();
                p.addActionListener(artistListener);
                p.setup(character, albumSet);

                panel.add(p);
            }
        }

        final JXLayer<JScrollPane> jx = new JXLayer<JScrollPane>(new QScrollPane(panel),
                new JScrollPaneLayerUI());

        return jx;
    }
}
