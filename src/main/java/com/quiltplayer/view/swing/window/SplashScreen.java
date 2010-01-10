package com.quiltplayer.view.swing.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

import com.quiltplayer.properties.ImageProperties;

/**
 * Quiltplayer splash screen.
 * 
 * @author Vlado Palczynski
 */
public class SplashScreen extends JWindow {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Create splash screen.
     */
    public SplashScreen() {
        JLabel l = new JLabel(new ImageIcon(ImageProperties.SPLASH));
        getContentPane().add(l, BorderLayout.CENTER);
        pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();
        setLocation(screenSize.width / 2 - (labelSize.width / 2),
                screenSize.height / 2 - (labelSize.height / 2));

        setVisible(true);
    }
}