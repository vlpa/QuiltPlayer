package com.quiltplayer.view.swing.views.impl.configurations;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.annotation.PostConstruct;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.checkbox.QCheckBox;
import com.quiltplayer.view.swing.designcomponents.TextFieldComponents;
import com.quiltplayer.view.swing.textfields.QPasswordField;
import com.quiltplayer.view.swing.textfields.QTextField;
import com.quiltplayer.view.swing.window.Keyboard;

@Component
public class SpotifyConfigurationPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * The spotify check box.
     */
    public JCheckBox spotifyCheckBox;

    @Autowired
    private Keyboard keyboardPanel;

    private JComponent userNameComponent;

    private JComponent passwordComponent;

    /**
     * The spotify user name.
     */
    public JTextField spotifyUserName;

    /**
     * The spotify password.
     */
    public JPasswordField spotifyPassword;

    public SpotifyConfigurationPanel() {
        super(new MigLayout("ins 0, fill, wrap 3"));
    }

    @PostConstruct
    public void init() {
        spotifyUserName = new QTextField(keyboardPanel);

        spotifyPassword = new QPasswordField(keyboardPanel);

        spotifyCheckBox = new QCheckBox("Spotify account");

        MouseListener l = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (spotifyCheckBox.isSelected()) {
                    userNameComponent.setVisible(true);
                    passwordComponent.setVisible(true);
                }
                else {
                    userNameComponent.setVisible(false);
                    passwordComponent.setVisible(false);
                }
            }
        };

        spotifyCheckBox.addMouseListener(l);

        add(spotifyCheckBox, "");

        userNameComponent = TextFieldComponents.textFieldComponentForForms("Spotify user name",
                spotifyUserName, Configuration.getInstance().getSpotifyProperties()
                        .getSpotifyUserName(), true);

        add(userNameComponent, "left, w 60%, newline");

        passwordComponent = TextFieldComponents.textFieldComponentForForms("Spotify password",
                spotifyPassword, Configuration.getInstance().getSpotifyProperties()
                        .getSpotifyPassword(), true);

        /* Needed as it shows the password in clear text otherwise */
        SwingUtilities.updateComponentTreeUI(passwordComponent);

        add(passwordComponent, "left, w 60%, newline");

        if (Configuration.getInstance().getSpotifyProperties().isUseSpotify()) {
            userNameComponent.setVisible(true);
            passwordComponent.setVisible(true);
            spotifyCheckBox.setSelected(true);
        }
        else {
            userNameComponent.setVisible(false);
            passwordComponent.setVisible(false);
        }
    }
}
