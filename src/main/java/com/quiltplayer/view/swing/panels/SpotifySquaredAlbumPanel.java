package com.quiltplayer.view.swing.panels;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

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

    public Timer currentTimer;

    private Runnable invoker;

    private boolean fetched;

    private JotifyRepository jotifyRepository;

    public SpotifySquaredAlbumPanel(final Album album,
            JotifyRepository jotifyRepository) {
        super(album);

        this.jotifyRepository = jotifyRepository;
    }

    @Override
    protected JLabel setupImage(final Album album) {
        final Icon icon = QIcon.getMedium();

        final JLabel iconLabel = new JLabel();
        iconLabel.setIcon(icon);

        add(iconLabel, "cell 0 0, aligny center");

        /*
         * Update the time counter
         */
        invoker = new Runnable() {
            public void run() {

                try {
                    Image image = jotifyRepository.getInstance().image(
                            ((JotifyAlbum) album).getSpotifyAlbum().getCover());

                    remove(iconLabel);

                    final Icon icon = new ImageIcon(ImageUtils.scalePicture(
                            image, ImageSizes.MEDIUM.getSize()));

                    iconLabel.setIcon(icon);

                    add(iconLabel, "cell 0 0, gapx 20 5, gapy 20 20");
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
     * @see
     * com.quiltplayer.view.swing.panels.SquaredAlbumPanel#paint(java.awt.Graphics
     * )
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!fetched) {
            SwingUtilities.invokeLater(invoker);
            fetched = true;
        }
    }
}
