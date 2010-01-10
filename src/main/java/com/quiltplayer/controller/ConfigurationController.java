package com.quiltplayer.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.listeners.ConfigurationListener;
import com.quiltplayer.view.swing.views.impl.ConfigurationView;

/**
 * Controller for the configuration view.
 * 
 * @author Vlado Palczynski
 */
@Controller
public class ConfigurationController implements ConfigurationListener {
    /**
     * Event to start updating configuration.
     */
    public static final String EVENT_UPDATE_CONFIGURATION = "update.configuration";

    @Autowired
    private QuiltPlayerFrame frame;

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (e.getActionCommand().equals(EVENT_UPDATE_CONFIGURATION)) {
            Configuration.getInstance().storeConfiguration();

            SwingUtilities.updateComponentTreeUI(frame);
            frame.repaint();
            frame.updateUI();
        }
        else if (e.getActionCommand() == ConfigurationView.EVENT_TOGGLE_FULLSCREEN) {
            if (Configuration.getInstance().isFullScreen()) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                frame.dispose();
                frame.setResizable(true);
                frame.setUndecorated(false);
                frame.updateUI();
                Dimension frameDimension = Configuration.getInstance()
                        .getSavedDimensionOnFrame();
                frame.setSize(frameDimension);
                frame.setLocation(screenSize.width / 2 - ((int) frameDimension.getWidth() / 2),
                        screenSize.height / 2 - ((int) frameDimension.getHeight() / 2));
                frame.setVisible(true);

                Configuration.getInstance().setFullScreen(false);
                Configuration.getInstance().storeConfiguration();

            }
            else {
                Configuration.getInstance().setSavedDimensionOnFrame(frame.getSize());

                frame.setLocation(0, 0); // Otherwise wrong position
                frame.dispose();
                frame.setResizable(false);
                frame.setUndecorated(true);
                frame.updateUI();
                frame.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
                frame.setVisible(true);

                Configuration.getInstance().setFullScreen(true);
                Configuration.getInstance().storeConfiguration();
            }
        }
    }
}
