package com.quiltplayer.view.swing.views.impl.configurations;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.checkbox.QCheckBox;
import com.quiltplayer.view.swing.designcomponents.TextFieldComponents;
import com.quiltplayer.view.swing.textfields.QPasswordField;
import com.quiltplayer.view.swing.textfields.QTextField;

@Component
public class SpotifyPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * The spotify check box.
     */
    public JCheckBox spotifyCheckBox;

    /**
     * The spotify user name.
     */
    public JTextField spotifyUserName = new QTextField();

    /**
     * The spotify password.
     */
    public JPasswordField spotifyPassword = new QPasswordField();

    public SpotifyPanel() {
        super(new MigLayout("ins 0, fill"));

        spotifyCheckBox = new QCheckBox("Spotify account");

        if (Configuration.getInstance().isUseSpotify()) {
            spotifyUserName.setVisible(true);
            spotifyPassword.setVisible(true);
            spotifyCheckBox.setSelected(true);
        }
        else {
            spotifyUserName.setVisible(false);
            spotifyPassword.setVisible(false);
        }

        MouseListener l = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // if (spotifyCheckBox.isSelected())
                // spotifySettings.setVisible(true);
                // else
                // spotifySettings.setVisible(false);
            }
        };

        spotifyCheckBox.addMouseListener(l);

        add(TextFieldComponents.textFieldComponentForForms("Spotify user name", spotifyUserName,
                Configuration.getInstance().getSpotifyUserName() + "", true),
                "left, w 100%, newline");
        add(TextFieldComponents.textFieldComponentForForms("Spotify password", spotifyPassword,
                Configuration.getInstance().getSpotifyPassword() + "", true),
                "left, w 100%, newline");

    }
}
