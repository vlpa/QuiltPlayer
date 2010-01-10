package com.quiltplayer.view.swing.labels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.model.Song;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.panels.QPanel;
import com.quiltplayer.view.swing.util.ColorUtils;
import com.quiltplayer.view.swing.util.HighlightColorUtils;

/**
 * Represents a track in lists.
 * 
 * @author Vlado Palczynski
 */
public class TrackSearchLabel extends QPanel {

    public static final String ACTION_GET_ARTIST_ALBUMS = "get.artist";

    private static final long serialVersionUID = 1L;

    private Color background = Configuration.getInstance().getColorConstants().getBackground();

    // Hit detection.
    Shape shape;

    protected ActionListener listener;

    protected Song song;

    public TrackSearchLabel(Song song) {
        super(new MigLayout("insets 7, wrap 1"));

        this.song = song;

        Font font = FontFactory.getFont(14f);

        setFont(font);
        setBackground(background);

        JLabel artistLabel = new JLabel(song.getAlbum().getArtist().getArtistName().getName());
        artistLabel.setFont(FontFactory.getFont(12f));
        artistLabel.setBackground(Configuration.getInstance().getColorConstants().getBackground());
        artistLabel.setForeground(Color.gray);
        artistLabel.setMaximumSize(new Dimension((int) getMaximumSize().getWidth(), 12));

        JLabel albumLabel = new JLabel(song.getAlbum().getTitle());
        albumLabel.setFont(FontFactory.getFont(12f));
        albumLabel.setBackground(Configuration.getInstance().getColorConstants().getBackground());
        albumLabel.setForeground(Color.gray);
        albumLabel.setMaximumSize(new Dimension((int) getMaximumSize().getWidth(), 12));

        JLabel songLabel = new JLabel(song.getTitle());
        songLabel.setFont(font);
        songLabel.setBackground(Configuration.getInstance().getColorConstants().getBackground());
        songLabel.setForeground(Configuration.getInstance().getColorConstants()
                .getArtistViewTextColor());

        add(artistLabel);
        add(albumLabel);
        add(songLabel);

        this.addMouseListener(mouseListener);
    }

    private transient MouseListener mouseListener = new MouseAdapter() {
        /*
         * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            background = ColorUtils.brighten20(background);

            repaint();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseExited(MouseEvent e) {
            background = ColorUtils.darken20(background);

            repaint();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            if (contains(e.getX(), e.getY())) {
                Object[] objects = new Object[2];
                objects[0] = song.getAlbum();
                objects[1] = song;

                listener.actionPerformed(new ActionEvent(objects, 0,
                        ChangeAlbumController.EVENT_CHANGE_ALBUM_AND_PLAY_SONG));
            }
        }

    };

    public void setSelected() {
        HighlightColorUtils.setSelected(this);
    }

    public void setInactive() {
        HighlightColorUtils.setInactive(this);
    }

    public void addActionListener(ActionListener listener) {
        this.listener = listener;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paint(g);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(background);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
    }
}
