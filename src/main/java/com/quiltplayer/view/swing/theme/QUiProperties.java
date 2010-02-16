package com.quiltplayer.view.swing.theme;

import java.awt.Color;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.borders.QBorder;

/**
 * 
 * My metal theme settings.
 * 
 * @author Vlado Palczynski
 * 
 */
public class QUiProperties {
    private static final Color BACKGROUND = ColorConstantsDark.BACKGROUND;

    public static void setProperties() {
        System.setProperty("swing.aatext", "true");
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        UIManager.put("Panel.background", ColorConstantsDark.BACKGROUND);
        UIManager.put("Panel.foreground", new Color(220, 220, 220));

        UIManager.put("List.background", new Color(30, 30, 30));
        UIManager.put("List.foreground", new Color(220, 220, 220));

        setupButton();

        setupComboBox();

        UIManager.put("CheckBox.opaque", false);

        UIManager.put("FileChooser.listViewBackground", Color.BLACK);
        UIManager.put("FileChooser.foreground", Color.GREEN);
        UIManager.put("FileChooser.listFont", FontFactory.getSansFont(14f));

        UIManager.put("Label.font", FontFactory.getSansFont(14f));
        UIManager.put("Label.foreground", new Color(135, 135, 135));

        UIManager.put("DesktopIcon.background", Color.pink);

        // setupTextField();

        UIManager.put("TextArea.background", new Color(15, 15, 15));
        UIManager.put("TextArea.foreground", new Color(220, 220, 220));
        UIManager.put("TextArea.font", FontFactory.getSansFont(13f));
    }

    private static void setupComboBox() {
        // UIManager.put("ComboBox.selectionBackground", Color.yellow);
        UIManager.put("ComboBox.selectionForeground", Color.BLACK);
        UIManager.put("ComboBox.buttonHighlight", Color.PINK);
        UIManager.put("ComboBox.buttonBackground", Color.PINK);
        UIManager.put("ComboBox.buttonShadow", Color.PINK);
        UIManager.put("ComboBox.foreground", new Color(220, 220, 220));
        UIManager.put("ComboBox.background", new Color(30, 30, 30));
        UIManager.put("ComboBox.control", new Color(30, 30, 30));
        UIManager.put("ComboBox.controlForeground", new Color(30, 30, 30));
        UIManager.put("ComboBox.font", FontFactory.getFont(14f));
        UIManager.put("ComboBox.showPopupOnNavigation", Boolean.FALSE);
    }

    private static void setupTextField() {
        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("PasswordField.background", BACKGROUND);

        UIManager.put("TextField.foreground", Configuration.getInstance().getColorConstants()
                .getArtistViewTextColor());
        UIManager.put("PasswordField.foreground", Configuration.getInstance().getColorConstants()
                .getArtistViewTextColor());

        UIManager.put("TextField.inactiveForeground", new Color(70, 70, 70));
        UIManager.put("PasswordField.inactiveForeground", new Color(70, 70, 70));

        UIManager.put("TextField.font", FontFactory.getSansFont(14f));
        UIManager.put("PasswordField.font", FontFactory.getSansFont(14f));

        UIManager.put("TextField.caretForeground", new Color(160, 160, 160));
        UIManager.put("PasswordField.caretForeground", new Color(160, 160, 160));

        Border bdButton = new QBorder(true);
        Insets insets = new Insets(7, 3, 7, 0);
        Border bdMargin = new EmptyBorder(insets.top + 1, insets.left + 1, insets.bottom + 1,
                insets.right + 1);

        UIManager.put("TextField.border", new CompoundBorder(bdButton, bdMargin));
        UIManager.put("PasswordField.border", new CompoundBorder(bdButton, bdMargin));
    }

    private static void setupButton() {
        UIManager.put("Button.font", FontFactory.getFont(14f));
        UIManager.put("Button.foreground", new Color(200, 200, 200));

        Border bdButton = new LineBorder(new Color(70, 70, 70), 1);
        Insets insets = new Insets(2, 10, 2, 10);
        Border bdMargin = new EmptyBorder(insets.top + 1, insets.left + 1, insets.bottom + 1,
                insets.right + 1);

        UIManager.put("Button.border", new CompoundBorder(bdButton, bdMargin));

        java.util.List<Object> gradients = new ArrayList<Object>();
        gradients.add(0.5f);
        gradients.add(1.0f);

        gradients.add(new Color(50, 50, 50));
        gradients.add(new Color(25, 25, 25));
        gradients.add(new Color(15, 15, 15));

        UIManager.put("Button.background", new Color(30, 30, 30));
        UIManager.put("Button.gradient", gradients);
        UIManager.put("Button.focus", new Color(40, 40, 40));
        UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);

        /* Disable roll over */
        UIManager.put("Button.rollover", Boolean.FALSE);
        /* Pressed */
        UIManager.put("Button.select", new Color(20, 20, 20));
        UIManager.put("Button.disabledForeground", Color.green);
        UIManager.put("Button.disabledBackground", Color.green);
        UIManager.put("Button.disabledText", new Color(100, 100, 100));

    }
}
