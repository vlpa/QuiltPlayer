package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.controller.PlayerController.PlayEvents;
import com.quiltplayer.model.Song;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.textarea.ScrollableTextArea;

/**
 * Represents a song line in the playlist view.
 * 
 * @author Vlado Palczynski
 */
public class QSongButton extends ScrollableButton {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(QSongButton.class);

    private JTextArea titleLabel;

    private Song song;

    private PlayerListener playerListener;

    private QPlaylistButton numberButton;

    public QSongButton(final Song song, final PlayerListener playerListener, final int counter) {
        this.playerListener = playerListener;

        setLayout(new MigLayout("insets 0, left, fill, center"));

        setBorder(BorderFactory.createEmptyBorder());

        this.song = song;

        setOpaque(false);

        titleLabel = new ScrollableTextArea() {
            private static final long serialVersionUID = 1L;

            /*
             * (non-Javadoc)
             * 
             * @see com.quiltplayer.view.swing.textarea.ScrollableTextArea#triggerAction()
             */
            @Override
            protected void triggerAction() {
                playerListener.actionPerformed(new ActionEvent(song, 0, PlayEvents.CHANGE.toString()));
            }
        };

        addMouseListener(new MouseAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
             */
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("!");
                setOpaque(true);
                setBackground(Color.GRAY);

                updateUI();

                super.mousePressed(e);
            }
        });

        titleLabel.setText(counter + ". " + song.getTitle());
        titleLabel.setBorder(null);
        titleLabel.setOpaque(false);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(FontFactory.getFont(12f).deriveFont(Font.PLAIN));
        titleLabel.setWrapStyleWord(true);
        titleLabel.setLineWrap(true);
        titleLabel.setEditable(true);
        titleLabel.setFocusable(false);
        titleLabel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        titleLabel.setAlignmentY(SwingConstants.CENTER);

        add(titleLabel, "gapx 0.2cm, left, aligny center, push");
    }

    public void setActive() {
        log.debug("Activating " + song.getTitle());

        setOpaque(true);

        setBackground(Color.RED.darker().darker().darker());

        updateUI();
    }

    public void setInactive() {
        log.debug("Inactivating " + song.getTitle());

        setOpaque(false);

        updateUI();
    }

    /**
     * @return the song
     */
    public Song getSong() {
        return song;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        super.paintComponent(g2d);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.buttons.ScrollableButton#triggerAction()
     */
    @Override
    public void triggerAction() {
        playerListener.actionPerformed(new ActionEvent(song, 0, PlayEvents.CHANGE.toString()));
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
