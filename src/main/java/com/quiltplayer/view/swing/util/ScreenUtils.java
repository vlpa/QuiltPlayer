package com.quiltplayer.view.swing.util;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.quiltplayer.properties.Configuration;

public class ScreenUtils {

    public static void toggleFullscreen(final JFrame frame) {
        if (Configuration.getInstance().isFullScreen())
            setHalfScreen(frame);
        else
            setFullscreen(frame);

        frame.setVisible(true);
    }

    public static void setScreensize(final JFrame frame) {
        if (Configuration.getInstance().isFullScreen()) {
            setFullscreen(frame);
        }
        else {
            setHalfScreen(frame);
        }

        frame.setVisible(true);
    }

    private static void setHalfScreen(final JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.dispose();
        frame.setResizable(true);
        frame.setUndecorated(false);
        Dimension frameDimension = Configuration.getInstance().getSavedDimensionOnFrame();
        frame.setSize(frameDimension);
        frame.setLocation(screenSize.width / 2 - ((int) frameDimension.getWidth() / 2), screenSize.height / 2
                - ((int) frameDimension.getHeight() / 2));

        Configuration.getInstance().setFullScreen(false);
        Configuration.getInstance().storeConfiguration();
    }

    private static void setFullscreen(final JFrame frame) {
        Configuration.getInstance().setSavedDimensionOnFrame(frame.getSize());

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        if (gd.isFullScreenSupported()) {
            frame.dispose();
            frame.setLocation(0, 0);
            frame.setUndecorated(true);
            gd.setFullScreenWindow(frame);
        }
        else {
            System.err.println("Full screen not supported");
            frame.setSize(100, 100); // just something to let you see the window
            frame.setVisible(true);
        }

        Configuration.getInstance().setFullScreen(true);
        Configuration.getInstance().storeConfiguration();

        SwingUtilities.updateComponentTreeUI(frame);
    }
}
