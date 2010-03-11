package com.quiltplayer.view.swing.buttons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import org.springframework.core.task.TaskExecutor;

import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.external.covers.util.ImageUtils;
import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.images.QImageIcon;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;

public class AlbumCoverButton extends ScrollableButton {

    private static final long serialVersionUID = 1L;

    private Album album;

    private ChangeAlbumListener changeAlbumListener;

    private boolean fetched;

    private boolean started;

    private TaskExecutor taskExecutor;

    public AlbumCoverButton(final Album album, final ChangeAlbumListener changeAlbumListener) {

        setLayout(new MigLayout("insets 0, fill"));
        setOpaque(true);

        add(new JLabel(), "");

        if (album != null) {
            this.album = album;
            this.changeAlbumListener = changeAlbumListener;

            setToolTipText(album.getArtist().getArtistName().getName() + " - " + album.getTitle());

            addMouseListener(new MouseAdapter() {
                /*
                 * (non-Javadoc)
                 * 
                 * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    // setBackground(Color.ORANGE);

                    // repaint();
                }

                /*
                 * (non-Javadoc)
                 * 
                 * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    // setBackground(ColorConstantsDark.BACKGROUND);

                    // repaint();

                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.buttons.ScrollableButton#triggerAction()
     */
    @Override
    public void triggerAction() {
        changeAlbumListener.actionPerformed(new ActionEvent(album, 0,
                ChangeAlbumController.EVENT_CHANGE_ALBUM));
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.AbstractButton#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
    }

    private Thread invoker = new Thread() {
        public void run() {
            try {

                final Icon icon;

                if (album.getImages().size() > 0)
                    icon = ImageUtils.scalePicture(new ImageIcon(album.getImages().get(0)
                            .getLargeImage().getAbsolutePath()), getWidth());
                else
                    icon = new QImageIcon("No cover", getWidth());

                setIcon(icon);

                fetched = true;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {

        setMinimumSize(new Dimension(getWidth(), getWidth()));
        setMaximumSize(new Dimension(getWidth(), getWidth()));

        if (album != null) {
            if (started == false) {
                invoker.start();
                started = true;
            }

            if (fetched) {
                fetched = false;

                SwingUtilities.updateComponentTreeUI(this);
            }
        }

        super.paintComponent(g);
    }
}
