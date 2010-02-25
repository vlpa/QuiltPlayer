package com.quiltplayer.view.swing.labels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.controller.ArtistController;
import com.quiltplayer.model.Artist;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.buttons.ScrollableButton;
import com.quiltplayer.view.swing.listeners.ArtistListener;
import com.quiltplayer.view.swing.util.HighlightColorUtils;

/**
 * Represents a artist in lists.
 * 
 * @author Vlado Palczynski
 */
public class ArtistNameButton extends ScrollableButton {

    private static final long serialVersionUID = 1L;

    protected Artist artist;

    private ArtistListener artistListener;

    public ArtistNameButton(final Artist artist, final ArtistListener artistListener) {
        this.artist = artist;
        this.artistListener = artistListener;

        setLayout(new MigLayout("fill, w 100%,  alignx left"));

        setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder());

        setText(artist.getArtistName().getName());

        setHorizontalTextPosition(SwingConstants.LEFT);
        setHorizontalAlignment(SwingConstants.LEFT);

        setForeground(Configuration.getInstance().getColorConstants().getArtistViewTextColor());

        this.addMouseListener(mouseListener);
    }

    protected transient MouseListener mouseListener = new MouseAdapter() {

        /*
         * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            setSelected();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseExited(MouseEvent e) {
            setInactive();
        }
    };

    public void setSelected() {
        HighlightColorUtils.setSelected(this);

        setBackground(ColorConstantsDark.BACKGROUND);
    }

    public void setInactive() {
        HighlightColorUtils.setInactive(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.buttons.ScrollableButton#triggerAction()
     */
    @Override
    protected void triggerAction() {
        artistListener.actionPerformed(new ActionEvent(artist, 0,
                ArtistController.ACTION_GET_ARTIST_ALBUMS));
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
}
