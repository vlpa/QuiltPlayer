package com.quiltplayer.view.swing.util;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.view.swing.frame.QuiltPlayerFrame;

@Component
public class SizeHelper {

    @Autowired
    private QuiltPlayerFrame frame;

    private static Dimension d;

    public static final double SCALE = Toolkit.getDefaultToolkit().getScreenResolution() / 143d;

    public void init() {
        if (frame == null)
            throw new IllegalArgumentException("Property 'frame' must be set.");

        d = frame.getSize();
    }

    public static int getMaxHeight() {
        return ((Double) d.getHeight()).intValue();
    }

    public static int getMaxWidth() {
        return ((Double) d.getWidth()).intValue();
    }

    public static int getControlPanelIconSize() {
        return ((Double) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 30)).intValue();
    }
}
