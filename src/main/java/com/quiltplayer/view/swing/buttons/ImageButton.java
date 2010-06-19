package com.quiltplayer.view.swing.buttons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.core.player.tasks.ImageLoaderTask;
import com.quiltplayer.external.covers.util.ImageUtils;
import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;

public class ImageButton extends ScrollableButton {

    private static final long serialVersionUID = 1L;

    private Album album;

    private ChangeAlbumListener changeAlbumListener;

    private boolean started;

    private ThreadPoolTaskExecutor executor;

    private ImageIcon icon;

    private ImageIcon pressedIcon;

    public ImageButton(final Album album, final ChangeAlbumListener changeAlbumListener, ThreadPoolTaskExecutor executor) {

        this.executor = executor;

        setLayout(new MigLayout("insets 0, fill"));
        setOpaque(false);

        add(new JLabel(), "");

        if (album != null) {
            this.album = album;
            this.changeAlbumListener = changeAlbumListener;

            setToolTipText(album.getArtist().getArtistName().getName() + " - " + album.getTitle());
        }

        addMouseListener(new MouseAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
             */
            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(pressedIcon);
                repaint();
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(icon);
                repaint();
            }

        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.buttons.ScrollableButton#triggerAction()
     */
    @Override
    public void triggerAction() {
        changeAlbumListener.actionPerformed(new ActionEvent(album, 0, ChangeAlbumController.EVENT_CHANGE_ALBUM));
    }

    public void setImage(ImageIcon icon) {
        this.icon = icon;

        setIcon(icon);

        repaint();

        pressedIcon = ImageUtils.scalePicture(icon, icon.getIconWidth() - 20);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {

        setMinimumSize(new Dimension(getWidth(), getWidth()));
        setMaximumSize(new Dimension(getWidth(), getWidth()));

        if (!started) {
            if (album != null && album.getImages().size() > 0)
                executor.execute(new ImageLoaderTask(album, getWidth(), this));

            started = true;
        }

        super.paintComponent(g);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.AbstractButton#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
        /* None */
    }

}
