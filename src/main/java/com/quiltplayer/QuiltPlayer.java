package com.quiltplayer;

import javax.swing.plaf.metal.MetalLookAndFeel;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.quiltplayer.view.swing.theme.QMetalTheme;

/**
 * Main class.
 * 
 * @author Vlado Palczynski
 */
public class QuiltPlayer {
    /**
     * Starts the show.
     * 
     * @param args
     *            the arguments.
     * @throws Exception
     *             the exceptions.
     */
    public static final void main(final String[] args) throws Exception {

        /*
         * Initialize look and feel.
         */
        MetalLookAndFeel.setCurrentTheme(new QMetalTheme());

        /*
         * Initialize log4j
         */
        final Logger log = Logger.getLogger(QuiltPlayer.class);
        log.debug("Log4j has been initialized.");

        /*
         * Initialize application through Spring Framework.
         */
        new ClassPathXmlApplicationContext("app-context.xml");
    }
}
