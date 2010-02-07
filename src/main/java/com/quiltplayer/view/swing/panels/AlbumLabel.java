package com.quiltplayer.view.swing.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;

public class AlbumLabel extends QPanel {

    private static final long serialVersionUID = 1L;

    public Icon icon;

    public JLabel iconLabel;

    public Timer currentTimer;

    private Album album;

    public AlbumLabel(final Album album) {

        super(new MigLayout("insets 0, w " + ImageSizes.SMALL.getSize() + "px!, h "
                + ImageSizes.SMALL.getSize() + "px!"));

        setAutoscrolls(true);
        setToolTipText(album.getArtist().getArtistName().getName() + " - " + album.getTitle());

        setOpaque(false);

        this.album = album;

        if (album.getImages().size() > 0)
            icon = new ImageIcon(album.getImages().get(0).getSmallImage().getAbsolutePath());
        else
            icon = new ImageIcon("images/nocover.gif");

        iconLabel = new JLabel();
        iconLabel.setIcon(icon);

        add(iconLabel, "w " + ImageSizes.SMALL.getSize() + ", h " + ImageSizes.SMALL.getSize()
                + "lp");
    }

    /**
     * @return the album
     */
    public Album getAlbum() {
        return album;
    }

    /**
     * @param album
     *            the album to set
     */
    public void setAlbum(Album album) {
        this.album = album;
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
}
