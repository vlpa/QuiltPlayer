package com.quiltplayer.view.swing.util;

import java.awt.Color;

/**
 * 
 * @author Vlado Palczynski
 * 
 */
public class ColorUtils {

    // TODO handle values outside of range...
    private static final int VALUE = 20;

    public static Color brighten(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        return new Color(red + VALUE, green + VALUE, blue + VALUE);
    }

    public static Color darken(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        return new Color(red - VALUE, green - VALUE, blue - VALUE);
    }
}
