package com.quiltplayer.view.swing.util;

import java.awt.Color;

public class ColorUtils {

    public static Color brighten20(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        return new Color(red + 20, green + 20, blue + 20);
    }

    public static Color darken20(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        return new Color(red - 20, green - 20, blue - 20);
    }
}
