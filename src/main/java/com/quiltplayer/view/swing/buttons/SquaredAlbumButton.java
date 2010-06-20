package com.quiltplayer.view.swing.buttons;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextArea;
import javax.swing.Timer;

import net.miginfocom.swing.MigLayout;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.panels.AlbumView;
import com.quiltplayer.view.swing.textarea.ScrollableTextArea;

/**
 * Implementation with squared albums.
 * 
 * @author Vlado Palczynski
 */
public class SquaredAlbumButton extends ScrollableAndHighlightableButton implements AlbumView {
    private static final long serialVersionUID = 1L;

    public Timer currentTimer;

    protected Album album;

    protected ChangeAlbumListener changeAlbumListener;

    protected ImageButton albumCoverButton;

    public SquaredAlbumButton(final Album album, ChangeAlbumListener changeAlbumListener,
            ThreadPoolTaskExecutor executor) {
        super();

        this.album = album;
        this.changeAlbumListener = changeAlbumListener;

        setLayout(new MigLayout("fill, wrap 1, center"));

        setOpaque(false);

        albumCoverButton = new ImageButton(album, changeAlbumListener, executor);
        final JTextArea title = setupTitleLabelToPanel();
        final JTextArea year = setupYearLabel();

        add(albumCoverButton, "north");
        add(title, "w 100%, north");
        add(year, "w 100%, north");

        // addMouseListener(new HighlightableMouseListener(background, this));
        addMouseListener(new MouseAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                // trackLabel.setVisible(true);
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseExited(MouseEvent e) {
                // trackLabel.setVisible(false);
            }
        });

    }

    private JTextArea setupTitleLabelToPanel() {
        JTextArea titleLabel = new ScrollableTextArea() {

            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            /*
             * (non-Javadoc)
             * 
             * @see com.quiltplayer.view.swing.textarea.ScrollableTextArea#triggerAction()
             */
            @Override
            protected void triggerAction() {
                changeAlbumListener
                        .actionPerformed(new ActionEvent(album, 0, ChangeAlbumController.EVENT_CHANGE_ALBUM));
            }
        };

        titleLabel.setOpaque(false);
        titleLabel.setEditable(true);
        titleLabel.setFocusable(false);
        titleLabel.setLineWrap(true);
        titleLabel.setWrapStyleWord(true);
        titleLabel.setForeground(Configuration.getInstance().getColorConstants().getAlbumViewTitleColor());
        titleLabel.setText(album.getTitle());
        titleLabel.setFont(FontFactory.getFont(14f));

        return titleLabel;
    }

    private JTextArea setupYearLabel() {
        JTextArea yearLabel = new ScrollableTextArea() {

            private static final long serialVersionUID = 1L;

            @Override
            protected void triggerAction() {
                changeAlbumListener
                        .actionPerformed(new ActionEvent(album, 0, ChangeAlbumController.EVENT_CHANGE_ALBUM));
            }

            /*
             * (non-Javadoc)
             * 
             * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
             */
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                super.paintComponent(g);
            }
        };

        yearLabel.setOpaque(false);
        yearLabel.setEditable(true);
        yearLabel.setFocusable(false);
        yearLabel.setWrapStyleWord(true);
        yearLabel.setForeground(Configuration.getInstance().getColorConstants().getAlbumViewYearColor());
        yearLabel.setText(album.getYear());
        yearLabel.setFont(FontFactory.getFont(12f));

        return yearLabel;
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
     * @see com.quiltplayer.view.swing.buttons.ScrollableButton#triggerAction()
     */
    @Override
    protected void triggerAction() {
        changeAlbumListener.actionPerformed(new ActionEvent(album, 0, ChangeAlbumController.EVENT_CHANGE_ALBUM));
    }
}
