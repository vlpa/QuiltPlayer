package com.quiltplayer.view.swing.util;

import java.awt.Color;

import javax.swing.JComponent;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.ColorConstantsDark;

public class HighlightColorUtils {
    private static Color textColor = Configuration.getInstance().getColorConstants()
            .getArtistViewTextColor();

    public static void setSelected(JComponent c) {
        c.setForeground(Configuration.getInstance().getColorConstants()
                .getArtistViewTextHighlightColor());

        c.setBackground(Configuration.getInstance().getColorConstants().getSearchBackground());
    }

    public static void setInactive(JComponent c) {
        c.setBackground(ColorConstantsDark.BACKGROUND);

        if (c.getForeground() != textColor) {
            c.setForeground(textColor);
        }
    }

}
