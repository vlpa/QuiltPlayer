package com.quiltplayer.view.swing.panels.controlpanels;

import java.awt.AlphaComposite;
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

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.GridController;
import com.quiltplayer.controller.PlayerController;
import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.utils.ClassPathUtils;
import com.quiltplayer.view.swing.buttons.QControlPanelButton;
import com.quiltplayer.view.swing.buttons.QTextButton;
import com.quiltplayer.view.swing.listeners.GridListener;

@Component
public class PlayerControlPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private enum FadeState {
        UP, DOWN
    }

    private FadeState fadeState = FadeState.DOWN;

    private QControlPanelButton playButton;

    private QControlPanelButton stopButton;

    private QControlPanelButton pauseButton;

    private QControlPanelButton nextButton;

    private QControlPanelButton previousButton;

    private QTextButton biggerButton;

    private QTextButton smallerButton;

    private Color[] gradient = { new Color(30, 30, 30), new Color(0, 0, 0) };

    private float[] dist = { 0.0f, 1.0f };

    private static final String LAYOUT = "h 100%, w 1.8cm";

    private float currentAlpha = 0.0f;

    private transient Animator animator = new Animator(200);

    @Autowired
    private PlayerListener playerListener;

    @Autowired
    private GridListener gridListener;

    public PlayerControlPanel() {
        super(new MigLayout("insets 0, flowx, fill"));
    }

    @PostConstruct
    public void init() {
        setOpaque(false);

        setupPlayButton();

        setupStopButton();

        setupPauseButton();

        setupNextButton();

        setupPreviousButton();

        setupBiggerButton();

        setupSmallerButton();

        add(previousButton, LAYOUT);
        add(playButton, LAYOUT);
        add(pauseButton, LAYOUT);
        add(stopButton, LAYOUT);
        add(nextButton, LAYOUT);

        add(smallerButton, "east");
        add(biggerButton, "east");

        setStopped();

        fadeAllButtons();
    }

    private void setupSmallerButton() {
        smallerButton = new QTextButton("[ - ]");
        smallerButton.addActionListener(gridListener);
        smallerButton.setActionCommand(GridController.EVENT_INCREASE_GRID);
        smallerButton.setToolTipText("Smaller");
        smallerButton.setBorderPainted(false);

    }

    private void setupBiggerButton() {
        biggerButton = new QTextButton("[ + ]");
        biggerButton.addActionListener(gridListener);
        biggerButton.setActionCommand(GridController.EVENT_DECREASE_GRID);
        biggerButton.setToolTipText("Bigger");
        biggerButton.setBorderPainted(false);

    }

    private void setupNextButton() {
        nextButton = new QControlPanelButton("Next", ClassPathUtils
                .getIconFromClasspath("white/Next.png"), SwingConstants.TOP, SwingConstants.RIGHT);
        nextButton.addActionListener(playerListener);
        nextButton.setActionCommand(PlayerController.PlayerSongEvents.NEXT.toString());
    }

    private void setupPreviousButton() {
        previousButton = new QControlPanelButton("Prev", ClassPathUtils
                .getIconFromClasspath("white/Previous.png"), SwingConstants.TOP,
                SwingConstants.RIGHT);
        previousButton.addActionListener(playerListener);
        previousButton.setActionCommand(PlayerController.PlayerSongEvents.PREVIOUS.toString());
    }

    private void setupPlayButton() {
        playButton = new QControlPanelButton("Play", ClassPathUtils
                .getIconFromClasspath("white/Play.png"), SwingConstants.TOP, SwingConstants.RIGHT);
        playButton.addActionListener(playerListener);
        playButton.setActionCommand(PlayerController.PlayerSongEvents.PLAY.toString());
    }

    private void setupStopButton() {
        stopButton = new QControlPanelButton("Stop", ClassPathUtils
                .getIconFromClasspath("white/Stop.png"), SwingConstants.TOP, SwingConstants.RIGHT);
        stopButton.addActionListener(playerListener);
        stopButton.setActionCommand(PlayerController.PlayerSongEvents.STOP.toString());
    }

    private void setupPauseButton() {
        pauseButton = new QControlPanelButton("Pause", ClassPathUtils
                .getIconFromClasspath("white/Pause.png"), SwingConstants.TOP, SwingConstants.RIGHT);
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

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(makeComposite());

        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(0, getHeight());

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist, gradient);
        g2d.setPaint(p);

        g2d.fillRect(0, 0, getWidth(), getHeight());

        super.paintComponent(g2d);
    }

    /**
     * Show the component.
     **/
    public void show() {
        if (currentAlpha != 1 && !animator.isRunning() && fadeState != FadeState.UP) {
            fadeAllButtons();

            animate(currentAlpha, 0.8f);

            fadeState = FadeState.UP;
        }
    }

    private void fadeAllButtons() {
        playButton.fade();
        pauseButton.fade();
        nextButton.fade();
        previousButton.fade();
        stopButton.fade();
    }

    // TODO Make to a utility method, opacity animator.
    private void animate(final float fromAlpha, final float toAlpha) {
        if (animator.isRunning())
            animator.stop();

        PropertySetter setter = new PropertySetter(this, "alpha", fromAlpha, toAlpha);
        animator = new Animator(300, setter);
        animator.start();
    }

    /*
     * Set alpha composite. For example, pass in 1.0f to have 100% opacity pass in 0.25f to have 25%
     * opacity.
     */
    private AlphaComposite makeComposite() {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, currentAlpha));
    }

    /**
     * @return the alpha
     */
    public final float getAlpha() {
        return currentAlpha;
    }

    /**
     * @param alpha
     *            the alpha to set
     */
    public final void setAlpha(float alpha) {
        this.currentAlpha = alpha;

        repaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Component#hide()
     */
    public void hide() {
        if (currentAlpha != 0f && fadeState != FadeState.DOWN) {
            fadeAllButtons();

            animate(currentAlpha, 0f);

            fadeState = FadeState.DOWN;
        }

    }
}
