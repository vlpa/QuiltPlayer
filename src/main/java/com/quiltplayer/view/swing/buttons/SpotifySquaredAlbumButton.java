package com.quiltplayer.view.swing.buttons;

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
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;

/**
 * Implementation with squared albums.
 * 
 * @author Vlado Palczynski
 */
public class SpotifySquaredAlbumButton extends SquaredAlbumButton {
    private static final long serialVersionUID = 1L;

    public Icon icon;

    public JTextArea titleLabel;

    public JLabel artistLabel;

    private boolean fetched;

    private JLabel iconLabel;

    public SpotifySquaredAlbumButton(final Album album,
            final ChangeAlbumListener changeAlbumListener) {
        super(album, changeAlbumListener);

        iconLabel = new JLabel();

        invoker.start();
    }

    private transient Thread invoker = new Thread() {
        public void run() {
            try {
                if (((JotifyAlbum) album).getSpotifyAlbum().getCover() != null) {

                    final Image image = JotifyRepository.getInstance().image(
                            ((JotifyAlbum) album).getSpotifyAlbum().getCover());

                    icon = new ImageIcon(ImageUtils
                            .scalePicture(image, ImageSizes.MEDIUM.getSize()));

                    iconLabel.setIcon(icon);

                    fetched = true;

                    repaint();
                    updateUI();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.panels.SquaredAlbumPanel#paint(java.awt.Graphics )
     */
    @Override
    public void paintComponent(Graphics g) {
        if (fetched) {
            fetched = false;
            SwingUtilities.updateComponentTreeUI(this);
        }

        super.paintComponent(g);
    }
}
