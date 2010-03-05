package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.ToolTipManager;

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

    private Album album;

    private ChangeAlbumListener changeAlbumListener;

    public AlbumCoverButton(final Album album, final ChangeAlbumListener changeAlbumListener) {
        this.album = album;
        this.changeAlbumListener = changeAlbumListener;

        setLayout(new MigLayout("insets 0"));

        setToolTipText(album.getArtist().getArtistName().getName() + " - " + album.getTitle());

        setOpaque(true);

        if (album.getImages().size() > 0)
            icon = new ImageIcon(album.getImages().get(0).getSmallImage().getAbsolutePath());
        else
            icon = new ImageIcon("images/nocover.gif");

        iconLabel = new JLabel();
        iconLabel.setBackground(ColorConstantsDark.BACKGROUND);
        iconLabel.setIcon(icon);

        add(iconLabel, "w " + ImageSizes.SMALL.getSize() + "px!, h " + ImageSizes.SMALL.getSize()
                + "px + 0.15cm!");

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
     * @see javax.swing.AbstractButton#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
    }
}
