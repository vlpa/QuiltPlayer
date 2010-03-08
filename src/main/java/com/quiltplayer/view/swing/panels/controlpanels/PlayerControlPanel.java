package com.quiltplayer.view.swing.panels.controlpanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.PlayerController;
import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.utils.ClassPathUtils;
import com.quiltplayer.view.swing.buttons.QControlPanelButton;

@Component
public class PlayerControlPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private QControlPanelButton playButton;

    private QControlPanelButton stopButton;

    private QControlPanelButton pauseButton;

    private QControlPanelButton nextButton;

    private QControlPanelButton previousButton;

    private Color[] gradient = { new Color(80, 80, 80), new Color(50, 50, 50),
            new Color(20, 20, 20), new Color(00, 00, 00) };

    private float[] dist = { 0.0f, 0.60f, 0.64f, 1.0f };

    @Autowired
    private PlayerListener playerListener;

    public PlayerControlPanel() {
        super(new MigLayout("insets 0, wrap 6"));
    }

    @PostConstruct
    public void init() {
        setupPlayButton();

        setupStopButton();

        setupPauseButton();

        setupNextButton();

        setupPreviousButton();

        final String s = "h 100%, w 3cm";

        setOpaque(true);

        add(previousButton, s + ", cell 1 0");
        add(playButton, s + ", cell 2 0");
        add(pauseButton, s + ", cell 3 0");
        add(stopButton, s + ", cell 4 0");
        add(nextButton, s + ", cell 5 0");

        setStopped();
    }

    private void setupNextButton() {
        nextButton = new QControlPanelButton("Next", ClassPathUtils
                .getIconFromClasspath("white/Next.png"), SwingConstants.TOP);
        nextButton.addActionListener(playerListener);
        nextButton.setActionCommand(PlayerController.PlayerSongEvents.NEXT.toString());
    }

    private void setupPreviousButton() {
        previousButton = new QControlPanelButton("Prev", ClassPathUtils
                .getIconFromClasspath("white/Previous.png"), SwingConstants.TOP);
        previousButton.addActionListener(playerListener);
        previousButton.setActionCommand(PlayerController.PlayerSongEvents.PREVIOUS.toString());
    }

    private void setupPlayButton() {
        playButton = new QControlPanelButton("Play", ClassPathUtils
                .getIconFromClasspath("white/Play.png"), SwingConstants.TOP);
        playButton.addActionListener(playerListener);
        playButton.setActionCommand(PlayerController.PlayerSongEvents.PLAY.toString());
    }

    private void setupStopButton() {
        stopButton = new QControlPanelButton("Stop", ClassPathUtils
                .getIconFromClasspath("white/Stop.png"), SwingConstants.TOP);
        stopButton.addActionListener(playerListener);
        stopButton.setActionCommand(PlayerController.PlayerSongEvents.STOP.toString());
    }

    private void setupPauseButton() {
        pauseButton = new QControlPanelButton("Pause", ClassPathUtils
                .getIconFromClasspath("white/Pause.png"), SwingConstants.TOP);
        pauseButton.addActionListener(playerListener);
        pauseButton.setActionCommand(PlayerController.PlayerSongEvents.PAUSE.toString());
    }

    public void setPlaying() {
        pauseButton.inactivate();
        stopButton.inactivate();
        playButton.activate();
    }

    public void setStopped() {
        pauseButton.inactivate();
        stopButton.activate();
        playButton.inactivate();
    }

    public void setPaused() {
        pauseButton.activate();
        stopButton.inactivate();
        playButton.inactivate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(0, getHeight());

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist, gradient);

        g2d.setPaint(p);

        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
