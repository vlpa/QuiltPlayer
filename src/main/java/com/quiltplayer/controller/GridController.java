package com.quiltplayer.controller;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;
import com.quiltplayer.view.swing.listeners.GridListener;

/**
 * @author Vlado Palczynski
 */

@Controller()
public class GridController implements GridListener {

    private Logger log = Logger.getLogger(GridController.class);

    public enum GridView {
        QUILT
    };

    @Autowired
    private QuiltPlayerFrame frame;

    public static final String EVENT_CHANGE_GRID = "change.grid";

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public final void actionPerformed(final ActionEvent e) {

        if (EVENT_CHANGE_GRID == e.getActionCommand()) {
            int i = e.getID();
            GridView gv = (GridView) e.getSource();

            if (gv == GridView.QUILT)
                Configuration.getInstance().getGridProperties().setQuiltGrid(i);

            Configuration.getInstance().storeConfiguration();

            frame.updateUI();
        }
    }
}
