package com.quiltplayer.view.swing.views.impl.configurations;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.ColorConstantsLight;
import com.quiltplayer.view.swing.labels.QLabel;

@Component
public class ColorPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * The light color profile JRadioButton.
     */
    private JRadioButton lightColorProfile, darkColorProfile;

    /**
     * Color profile light.
     */
    private static final String COLOR_PROFILE_LIGHT = "light";

    /**
     * Color profile dark.
     */
    private static final String COLOR_PROFILE_DARK = "dark";

    public ColorPanel() {

        super(new MigLayout("ins 0, fill"));

        addColorProfileButtons();
    }

    /**
     * Setup the color profile selector.
     * 
     * @return Color profile label
     */
    private JLabel addColorProfileButtons() {
        JLabel colorProfileLabel = new QLabel("Color profile");
        lightColorProfile = new JRadioButton("Light");
        lightColorProfile.setOpaque(false);
        lightColorProfile.setActionCommand(COLOR_PROFILE_LIGHT);
        // lightColorProfile.addActionListener(this);

        darkColorProfile = new JRadioButton("Dark");
        darkColorProfile.setOpaque(false);
        darkColorProfile.setActionCommand(COLOR_PROFILE_DARK);
        // darkColorProfile.addActionListener(this);

        if (Configuration.getInstance().getColorConstants() instanceof ColorConstantsLight) {
            lightColorProfile.setSelected(true);
        }
        else {
            darkColorProfile.setSelected(true);
        }

        return colorProfileLabel;
    }
}
