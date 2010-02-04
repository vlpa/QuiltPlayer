package com.quiltplayer.view.swing.panels;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.quiltplayer.core.repo.spotify.JotifyRepository;
import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.external.covers.util.ImageUtils;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.jotify.JotifyAlbum;
import com.quiltplayer.view.swing.images.QIcon;

/**
 * Implementation with squared albums.
 * 
 * @author Vlado Palczynski
 */
public class SpotifySquaredAlbumPanel extends SquaredAlbumPanel {
    private static final long serialVersionUID = 1L;

    public Icon icon;

    public JTextArea titleLabel;

    public JLabel artistLabel;

    private boolean fetched;

    private JLabel iconLabel;

    private Thread invoker;

    private JotifyRepository jotifyRepository;

    public SpotifySquaredAlbumPanel(final Album album, JotifyRepository jotifyRepository) {
        super(album);

        this.jotifyRepository = jotifyRepository;
    }

    @Override
    protected JLabel setupImage(final Album album) {

        iconLabel = new JLabel(QIcon.getMedium());

        add(iconLabel, "cell 0 0, aligny center");

        /*
         * Update the time counter
         */
        invoker = new Thread() {
            public void run() {
                try {
                    final Image image = jotifyRepository.getInstance().image(
                            ((JotifyAlbum) album).getSpotifyAlbum().getCover());

                    icon = new ImageIcon(ImageUtils
                            .scalePicture(image, ImageSizes.MEDIUM.getSize()));

                    iconLabel.setIcon(icon);

                    repaint();
                }
                catch (Exception e) {
                }
            }
        };

        return iconLabel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.panels.SquaredAlbumPanel#paint(java.awt.Graphics )
     */
    @Override
    public void paintComponent(Graphics g) {

        if (!fetched) {
            SwingUtilities.invokeLater(invoker);
            fetched = true;
        }

        super.paintComponent(g);

    }
}
