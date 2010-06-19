package com.quiltplayer.view.swing.util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.annotation.PostConstruct;

import net.miginfocom.layout.PlatformDefaults;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;

@Component
public class ResolutionUtils {

    private static final Logger log = Logger.getLogger(ResolutionUtils.class);

    public static int dpi;

    public static Dimension screenSize;

    public static Dimension frameSize;
    
    @Autowired
    private QuiltPlayerFrame frame;

    @PostConstruct
    public void init() {
        dpi = Toolkit.getDefaultToolkit().getScreenResolution();
        log.info("The dpi of the screen is " + dpi + ".");
        log.info("The miglayout thinks the dpi of the screen is " + PlatformDefaults.getDefaultDPI() + ".");

        System.out.println(PlatformDefaults.getCurrentPlatform());
        System.out.println(PlatformDefaults.getPlatform());

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        log.info("The screen size is " + screenSize.toString() + ".");

        frameSize = frame.getSize();
        log.info("The current frame size is " + frameSize + ".");
    }
}
