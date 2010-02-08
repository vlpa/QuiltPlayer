package com.quiltplayer.view.swing.panels.controlpanels;

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

        setOpaque(false);
        add(previousButton, s + ", cell 1 0");
        add(playButton, s + ", cell 2 0");
        add(pauseButton, s + ", cell 3 0");
        add(stopButton, s + ", cell 4 0");
        add(nextButton, s + ", cell 5 0");
    }

    private void setupNextButton() {
        nextButton = new QControlPanelButton("Next", ClassPathUtils
                .getIconFromClasspath("white/Next.png"), SwingConstants.TOP);
        nextButton.addActionListener(playerListener);
        nextButton.setActionCommand(PlayerController.EVENT_NEXT_SONG);
    }

    private void setupPreviousButton() {
        previousButton = new QControlPanelButton("Previous", ClassPathUtils
                .getIconFromClasspath("white/Previous.png"), SwingConstants.TOP);
        previousButton.addActionListener(playerListener);
        previousButton.setActionCommand(PlayerController.EVENT_PREVIOUS_SONG);
    }

    private void setupPlayButton() {
        playButton = new QControlPanelButton("Play", ClassPathUtils
                .getIconFromClasspath("white/Play.png"), SwingConstants.TOP);
        playButton.addActionListener(playerListener);
        playButton.setActionCommand(PlayerController.EVENT_PLAY_SONG);
    }

    private void setupStopButton() {
        stopButton = new QControlPanelButton("Stop", ClassPathUtils
                .getIconFromClasspath("white/Stop.png"), SwingConstants.TOP);
        stopButton.addActionListener(playerListener);
        stopButton.setActionCommand(PlayerController.EVENT_STOP_SONG);
    }

    private void setupPauseButton() {
        pauseButton = new QControlPanelButton("Pause", ClassPathUtils
                .getIconFromClasspath("white/Pause.png"), SwingConstants.TOP);
        pauseButton.addActionListener(playerListener);
        pauseButton.setActionCommand(PlayerController.EVENT_PAUSE_SONG);
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

}
