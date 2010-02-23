package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.controller.PlayerController.PlayerSongEvents;
import com.quiltplayer.model.Song;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;

/**
 * Represents a song line in the playlist view.
 * 
 * @author Vlado Palczynski
 */
public class QSongButton extends ScrollableButton {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(QSongButton.class);

    private JLabel timeLabel;

    private JLabel titleLabel;

    private Song song;

    private SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");

    private PlayerListener playerListener;

    private QPlaylistButton numberButton;

    public QSongButton(final Song song, final PlayerListener playerListener, final int counter) {
        this.playerListener = playerListener;

        setLayout(new MigLayout("insets 0, wrap 2, aligny center, fill"));

        setBorder(BorderFactory.createEmptyBorder());

        this.song = song;

        setAutoscrolls(true);

        setOpaque(false);

        titleLabel = new JLabel(song.getTitle());
        titleLabel.setOpaque(false);
        titleLabel
                .setForeground(Configuration.getInstance().getColorConstants().getPlaylistTitle());
        titleLabel.setFont(FontFactory.getFont(14f).deriveFont(Font.PLAIN));

        timeLabel = new JLabel();
        timeLabel.setText(formatter.format(0 / 1000));
        timeLabel.setVisible(false);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(FontFactory.getFont(15f).deriveFont(Font.PLAIN));

        final String layout = "w 0.8cm!, h 0.8cm!, gapx 0.1cm 0.1cm, gapy 0.1cm 0.1cm, aligny center";
        numberButton = new QPlaylistButton(counter + "");
        numberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerListener.actionPerformed(new ActionEvent(song, 0, PlayerSongEvents.CHANGE
                        .toString()));

            }
        });

        add(numberButton, layout);
        add(titleLabel, "cell 1 0, east, aligny bottom, h 0.8cm");
    }

    public void setActive() {
        log.debug("Activating " + song.getTitle());

        numberButton.activate();

        repaint();
    }

    public void setInactive() {
        log.debug("Inactivating " + song.getTitle());

        setBackground(Configuration.getInstance().getColorConstants().getPlaylistPanelBackground());

        timeLabel.setVisible(false);

        numberButton.inactivare();

        repaint();
        updateUI();
    }

    /**
     * @return the song
     */
    public Song getSong() {
        return song;
    }

    public void setProgress(long progress) {
        if (!timeLabel.isVisible())
            timeLabel.setVisible(true);

        if (progress < 0)
            progress = 0;

        timeLabel.setText(formatter.format(progress / 1000));

        updateUI();
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
     * @see com.quiltplayer.view.swing.buttons.ScrollableButton#triggerAction()
     */
    @Override
    public void triggerAction() {
        playerListener
                .actionPerformed(new ActionEvent(song, 0, PlayerSongEvents.CHANGE.toString()));
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
