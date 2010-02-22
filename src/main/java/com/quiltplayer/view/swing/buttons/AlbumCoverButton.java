package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;

public class AlbumCoverButton extends ScrollableButton {

    private static final long serialVersionUID = 1L;

    private Icon icon;

    private JLabel iconLabel;

    private Timer currentTimer;

    private Album album;

    private ChangeAlbumListener changeAlbumListener;

    public AlbumCoverButton(final Album album, final ChangeAlbumListener changeAlbumListener) {

        this.album = album;
        this.changeAlbumListener = changeAlbumListener;

        setLayout(new MigLayout("insets 0, w " + ImageSizes.SMALL.getSize() + "px!, h "
                + ImageSizes.SMALL.getSize() + "px!"));

        setToolTipText(album.getArtist().getArtistName().getName() + " - " + album.getTitle());

        setOpaque(true);

        // setBorder(BorderFactory.createLineBorder(ColorConstantsDark.BACKGROUND, 3));

        if (album.getImages().size() > 0)
            icon = new ImageIcon(album.getImages().get(0).getSmallImage().getAbsolutePath());
        else
            icon = new ImageIcon("images/nocover.gif");

        iconLabel = new JLabel();
        iconLabel.setBackground(ColorConstantsDark.BACKGROUND);
        iconLabel.setIcon(icon);

        add(iconLabel, "w " + ImageSizes.SMALL.getSize() + "px!, h " + ImageSizes.SMALL.getSize()
                + "px!");

        addMouseListener(new MouseAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.ORANGE);

                repaint();
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(ColorConstantsDark.BACKGROUND);

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
        changeAlbumListener.actionPerformed(new ActionEvent(album, 0,
                ChangeAlbumController.EVENT_CHANGE_ALBUM));
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(g);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.AbstractButton#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
    }
}
