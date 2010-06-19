package com.quiltplayer.view.swing.panels.controlpanels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.GridController;
import com.quiltplayer.controller.PlayerController;
import com.quiltplayer.controller.PlayerListener;
import com.quiltplayer.model.Song;
import com.quiltplayer.utils.ClassPathUtils;
import com.quiltplayer.view.swing.FontFactory;
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

    private Color[] gradient = { new Color(25, 25, 25), new Color(0, 0, 0) };

    private float[] dist = { 0.0f, 1.0f };

    private float currentAlpha = 1.0f;

    private transient Animator animator = new Animator(200);

    private JLabel titleLabel;

    private JSlider slider;

    private JPanel titleAndSliderPanel;

    private JLabel timeLabel;

    private long progress;

    private SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");

    @Autowired
    private PlayerListener playerListener;

    @Autowired
    private GridListener gridListener;

    public PlayerControlPanel() {
        super(new MigLayout("insets 0, fill, wrap 3, center"));
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

        setupTitleAndSlider();

        final String LAYOUT = "h 100%, w 1.3cm, h 1.3cm, center";

        add(stopButton, "cell 1 0, " + LAYOUT);
        add(previousButton, "cell 0 1, right, " + LAYOUT);
        add(playButton, "cell 1 1, " + LAYOUT);
        add(nextButton, "cell 2 1, left, " + LAYOUT);
        add(pauseButton, "cell 1 2, " + LAYOUT);

        // add(titleAndSliderPanel, "cell 1 4");

        // add(smallerButton, "cell 1 5");
        // add(biggerButton, "cell 2 5");

        setStopped();
    }

    private void setupTitleAndSlider() {
        titleLabel = new JLabel("No song loaded...");
        titleLabel.setFont(FontFactory.getFont(11f));

        slider = new JSlider(0, 10000);
        slider.setMinimum(0);
        slider.setOpaque(false);
        slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    final int value = (int) source.getValue();

                    if (value != progress) {
                        // playerListener.actionPerformed(new ActionEvent(value, 0, PlayerController.PlayEvents.SEEK
                        // .toString()));
                    }
                }
            }
        });

        timeLabel = new JLabel();
        timeLabel.setText(formatter.format(0 / 1000));
        timeLabel.setVisible(false);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(FontFactory.getFont(15f).deriveFont(Font.PLAIN));

        titleAndSliderPanel = new JPanel(new MigLayout("ins 0, wrap 2"));
        titleAndSliderPanel.setOpaque(false);
        titleAndSliderPanel.add(timeLabel);
        titleAndSliderPanel.add(titleLabel);
        titleAndSliderPanel.add(slider);
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
        nextButton = new QControlPanelButton("Next", ClassPathUtils.getIconFromClasspath("white/Next.png"),
                SwingConstants.TOP, SwingConstants.RIGHT);
        nextButton.addActionListener(playerListener);
        nextButton.setActionCommand(PlayerController.PlayEvents.NEXT.toString());
    }

    private void setupPreviousButton() {
        previousButton = new QControlPanelButton("Prev", ClassPathUtils.getIconFromClasspath("white/Previous.png"),
                SwingConstants.TOP, SwingConstants.RIGHT);
        previousButton.addActionListener(playerListener);
        previousButton.setActionCommand(PlayerController.PlayEvents.PREVIOUS.toString());
    }

    private void setupPlayButton() {
        playButton = new QControlPanelButton("Play", ClassPathUtils.getIconFromClasspath("white/Play.png"),
                SwingConstants.TOP, SwingConstants.RIGHT);
        playButton.addActionListener(playerListener);
        playButton.setActionCommand(PlayerController.PlayEvents.PLAY.toString());
    }

    private void setupStopButton() {
        stopButton = new QControlPanelButton("Stop", ClassPathUtils.getIconFromClasspath("white/Stop.png"),
                SwingConstants.TOP, SwingConstants.RIGHT);
        stopButton.addActionListener(playerListener);
        stopButton.setActionCommand(PlayerController.PlayEvents.STOP.toString());
    }

    private void setupPauseButton() {
        pauseButton = new QControlPanelButton("Pause", ClassPathUtils.getIconFromClasspath("white/Pause.png"),
                SwingConstants.TOP, SwingConstants.RIGHT);
        pauseButton.addActionListener(playerListener);
        pauseButton.setActionCommand(PlayerController.PlayEvents.PAUSE.toString());
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

    // @Override
    // protected void paintComponent(Graphics g) {
    // Graphics2D g2d = (Graphics2D) g;
    //
    // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    // g2d.setComposite(makeComposite());
    //
    // Point2D start = new Point2D.Float(0, 0);
    // Point2D end = new Point2D.Float(0, getHeight());
    //
    // LinearGradientPaint p = new LinearGradientPaint(start, end, dist, gradient);
    // g2d.setPaint(p);
    //
    // g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 9, 9);
    //
    // super.paintComponent(g2d);
    // }

    // TODO Make to a utility method, opacity animator.
    private void animate(final float fromAlpha, final float toAlpha) {
        if (animator.isRunning())
            animator.stop();

        PropertySetter setter = new PropertySetter(this, "alpha", fromAlpha, toAlpha);
        animator = new Animator(200, setter);
        animator.start();
    }

    /*
     * Set alpha composite. For example, pass in 1.0f to have 100% opacity pass
     * in 0.25f to have 25% opacity.
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

    public void setProgress(long progress) {
        if (!timeLabel.isVisible())
            timeLabel.setVisible(true);

        if (progress < 0)
            progress = 0;

        timeLabel.setText(formatter.format(progress / 1000));

        this.progress = progress;
        slider.setValue((int) progress);

        timeLabel.repaint();
    }

    public void changeSong(final Song song) {
        slider.setValue(0);
        slider.setMaximum(song.getDuration());
    }
}
