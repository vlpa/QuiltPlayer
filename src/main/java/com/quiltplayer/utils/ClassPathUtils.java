package com.quiltplayer.utils;

import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.quiltplayer.view.swing.util.SizeHelper;

public class ClassPathUtils {

    public static ImageIcon getIconFromClasspath(final String classPathName) {
        Resource gearImage = new ClassPathResource(classPathName);
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(gearImage.getURL());
        }
        catch (IOException e) {
            return null;
        }

        Image img = icon.getImage();
        img = img.getScaledInstance(SizeHelper.getControlPanelIconSize(), SizeHelper
                .getControlPanelIconSize(), java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);

        return icon;
    }

    public static ImageIcon getIconFromClasspathWithoutScaling(final String classPathName) {
        Resource gearImage = new ClassPathResource(classPathName);
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(gearImage.getURL());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return icon;
    }
}
