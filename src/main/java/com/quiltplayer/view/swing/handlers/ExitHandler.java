package com.quiltplayer.view.swing.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.neo4j.graphdb.GraphDatabaseService;

import com.quiltplayer.properties.Configuration;

/**
 * Handles exit action.
 * 
 * @author Vlado Palczynski
 */
public class ExitHandler implements ActionListener {
    GraphDatabaseService neoService;

    public ExitHandler(final GraphDatabaseService neoService) {
        this.neoService = neoService;
    }

    public void actionPerformed(ActionEvent e) {
        Configuration.getInstance().storeConfiguration();

        neoService.shutdown();

        Runtime.getRuntime().exit(0);
    }
}
