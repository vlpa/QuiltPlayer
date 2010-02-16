package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

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

    private float[] dist = { 0.0f, 1.0f };

    private Color[] activeGradient = { new Color(60, 60, 60), new Color(40, 40, 40) };

    private boolean isActive = false;

    private PlayerListener playerListener;

    public QSongButton(Song song, PlayerListener playerListener) {
        this.playerListener = playerListener;

        setLayout(new MigLayout("insets 0, wrap 2, fill"));

        this.song = song;

        setHorizontalTextPosition(SwingConstants.LEFT);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.CENTER);

        setAutoscrolls(true);

        setOpaque(false);

        addMouseListener(listener);

        setFont(FontFactory.getFont(14f).deriveFont(Font.PLAIN));

        titleLabel = new JLabel(song.getTitle());
        titleLabel
                .setForeground(Configuration.getInstance().getColorConstants().getPlaylistTitle());
        titleLabel.setFont(FontFactory.getFont(14f).deriveFont(Font.PLAIN));

        timeLabel = new JLabel();
        timeLabel.setText(formatter.format(0 / 1000));
        timeLabel.setVisible(false);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(FontFactory.getFont(14f).deriveFont(Font.PLAIN));

        add(timeLabel, "cell 0 0, west, gapx 0.1cm");
        add(titleLabel, "cell 1 0, west, gapx 0.2cm");
    }

    private transient MouseListener listener = new MouseAdapter() {
    };

    public void setActive() {
        log.debug("Activating " + song.getTitle());

        isActive = true;

        repaint();
    }

    public void setInactive() {
        log.debug("Inactivating " + song.getTitle());

        isActive = false;

        setBackground(Configuration.getInstance().getColorConstants().getPlaylistPanelBackground());

        timeLabel.setVisible(false);

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

        super.paintComponent(g);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isActive) {
            Point2D start = new Point2D.Float(0, 0);
            Point2D end = new Point2D.Float(0, getHeight() - 1);

            LinearGradientPaint p = new LinearGradientPaint(start, end, dist, activeGradient);

            g2d.setPaint(p);
            g2d.fillRoundRect(1, 1, getWidth() - 1, getHeight(), 15, 15);
        }
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
