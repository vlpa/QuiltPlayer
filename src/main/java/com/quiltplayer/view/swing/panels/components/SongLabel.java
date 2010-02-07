package com.quiltplayer.view.swing.panels.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import com.quiltplayer.controller.PlayerController;
import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Song;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.buttons.QSongButton;

/**
 * Represents a song line in the playlist view.
 * 
 * @author Vlado Palczynski
 */
public class SongLabel extends JPanel {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(SongLabel.class);

    public static final int STATUS_STOPPED = 0;

    public static final int STATUS_PLAYING = 1;

    private JLabel timeLabel;

    private JLabel pauseLabel;

    private JButton button;

    private Song song;

    private int status;

    private ActionListener actionListener;

    private SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");

    private float[] dist = { 0.0f, 1.0f };

    private QSongButton titleButton;

    private Color[] activeGradient = { new Color(60, 60, 60), new Color(40, 40, 40) };

    private Animator animator;

    private boolean isActive = false;

    public SongLabel(Song song) {
        setLayout(new MigLayout("insets 0, aligny center, fill", " [50px!|"
                + (ImageSizes.LARGE.getSize() - 50) + "]"));

        setOpaque(false);

        this.song = song;

        titleButton = new QSongButton(stripTitleLength(song.getTitle()),
                ImageSizes.LARGE.getSize() - 50 - 50);

        titleButton.addMouseListener(listener);
        titleButton.setSelected(false);

        titleButton.setFont(FontFactory.getFont(14f).deriveFont(Font.PLAIN));
        titleButton.setForeground(Configuration.getInstance().getColorConstants()
                .getPlaylistTitle());

        add(titleButton, "cell 1 0");

        timeLabel = new JLabel();
        timeLabel.setForeground(Color.white);
    }

    private transient MouseListener listener = new MouseAdapter() {

        /*
         * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            setSelected();
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseExited(MouseEvent e) {
            setInactive2();
        }

        /*
         * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
         */
        @Override
        public void mousePressed(MouseEvent e) {
            if (status == STATUS_PLAYING) {
                actionListener.actionPerformed(new ActionEvent(getSong(), 0,
                        PlayerController.EVENT_PAUSE_SONG));
            }
            else if (titleButton.isSelected()) {
                actionListener.actionPerformed(new ActionEvent(getSong(), 0,
                        PlayerController.EVENT_RESUME_SONG));
            }
            else {
                actionListener.actionPerformed(new ActionEvent(getSong(), 0,
                        PlayerController.EVENT_CHANGE_SONG));
            }
        }
    };

    public void setActive() {
        log.debug("Activating " + song.getTitle());

        setBackground(Configuration.getInstance().getColorConstants()
                .getPlaylistSongBackgroundCurrent());

        isActive = true;

        titleButton.setSelected(true);

        add(timeLabel, "cell 0 0, alignx left, aligny center, gapx 5px!");

        status = STATUS_PLAYING;

        PropertySetter setter = new PropertySetter(titleButton, "foreground", Color.GRAY,
                Color.WHITE);
        animator = new Animator(900, Animator.INFINITE, Animator.RepeatBehavior.REVERSE, setter);
        animator.start();

        repaint();
    }

    public void setPaused() {
        button.setText(">");
        pauseLabel.setText(">");
        setBackground(new Color(30, 30, 30));
    }

    public void setResumed() {
        button.setText("||");
        pauseLabel.setText("||");
        setBackground(Configuration.getInstance().getColorConstants()
                .getPlaylistSongBackgroundCurrent());
    }

    public void setSelected() {
        setForeground(Color.BLACK);
    }

    public void setInactive() {
        log.debug("Inactivating " + song.getTitle());

        status = STATUS_STOPPED;

        isActive = false;

        titleButton.setSelected(false);
        setBackground(Configuration.getInstance().getColorConstants().getPlaylistPanelBackground());

        remove(timeLabel);

        if (animator != null)
            animator.stop();

        repaint();
        updateUI();
    }

    public void setInactive2() {
        if (status != STATUS_PLAYING) {
            setBackground(Configuration.getInstance().getColorConstants()
                    .getPlaylistPanelBackground());
        }
    }

    /**
     * @return the song
     */
    public Song getSong() {
        return song;
    }

    /**
     * @param song
     *            the song to set
     */
    public void setSong(Song song) {
        this.song = song;
    }

    public void setProgress(long progress) {
        timeLabel.setText(formatter.format(progress / 1000));

        updateUI();
    }

    private String stripTitleLength(String title) {
        // TODO should be own object.
        if (title == null || title.length() == 0)
            return "N/A";

        if (title.length() > 35)
            title = title.substring(0, 35) + "...";

        return title;
    }

    /**
     * @param actionListener
     *            the actionListener to add
     */
    public void addActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
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
            g2d.fillRect(1, 1, getWidth() - 1, getHeight());
        }
    }
}
