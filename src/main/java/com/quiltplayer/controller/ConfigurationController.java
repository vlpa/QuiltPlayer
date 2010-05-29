package com.quiltplayer.controller;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.listeners.ConfigurationListener;
import com.quiltplayer.view.swing.util.ScreenUtils;
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
        }
        else if (e.getActionCommand() == ConfigurationView.EVENT_TOGGLE_FULLSCREEN)
            ScreenUtils.toggleFullscreen(frame);
    }
}
