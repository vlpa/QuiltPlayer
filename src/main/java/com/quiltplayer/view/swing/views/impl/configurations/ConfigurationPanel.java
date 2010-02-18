package com.quiltplayer.view.swing.views.impl.configurations;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.designcomponents.TextFieldComponents;
import com.quiltplayer.view.swing.labels.QLabel;
import com.quiltplayer.view.swing.listeners.ConfigurationListener;
import com.quiltplayer.view.swing.views.impl.ConfigurationView;

@Component
public class ConfigurationPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Enable/disable fullscreen button
     */
    private JButton fullscreenButton;

    public JComboBox fontSelectBox;

    @Autowired
    private ConfigurationListener configurationListener;

    public ConfigurationPanel() {
        super(new MigLayout("ins 0, w 100%"));
    }

    @PostConstruct
    public void init() {
        add(TextFieldComponents.textFieldComponentForForms("Root folder", Configuration.ROOT_PATH,
                false), "left, w 40%, newline");
        add(TextFieldComponents.textFieldComponentForForms("Storage folder",
                Configuration.STORAGE_PATH, false), "left, w 70%, newline");
        add(TextFieldComponents.textFieldComponentForForms("Album covers folder",
                Configuration.ALBUM_COVERS_PATH, false), "left, w 70%, newline");

        addFontSize();

        addToggleFullscreenButton();
    }

    private void addToggleFullscreenButton() {
        fullscreenButton = new QButton("Toggle fullscreen");
        fullscreenButton.addActionListener(configurationListener);
        fullscreenButton.setActionCommand(ConfigurationView.EVENT_TOGGLE_FULLSCREEN);

        add(fullscreenButton, "gapy 10, w 2.7cm");
    }

    private void addFontSize() {
        fontSelectBox = new JComboBox(new String[] { "-3", "-2", "-1", "0", "+1", "+2", "+3" });
        fontSelectBox.setOpaque(true);

        int currentValue = ((Integer) ((Float) Configuration.getInstance().getFontBalancer())
                .intValue());

        String currentValueAsString = null;
        if (currentValue > 0)
            currentValueAsString = "+" + currentValue;
        else
            currentValueAsString = currentValue + "";

        fontSelectBox.setSelectedItem(currentValueAsString);

        add(new QLabel("Font adjust"), "left, newline");
        add(fontSelectBox, "left, w 2cm, newline");
    }
}