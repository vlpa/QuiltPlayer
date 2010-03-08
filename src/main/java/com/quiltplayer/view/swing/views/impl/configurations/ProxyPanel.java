package com.quiltplayer.view.swing.views.impl.configurations;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.labels.QLabel;
import com.quiltplayer.view.swing.textfields.QPasswordField;
import com.quiltplayer.view.swing.textfields.QTextField;
import com.quiltplayer.view.swing.window.KeyboardPanel;

@Component
public class ProxyPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    @Autowired
    private KeyboardPanel keyboardPanel;

    /**
     * The proxy checkbox.
     */
    private JCheckBox proxyCheckBox;

    /**
     * The proxy port.
     */
    private static JTextField proxyPort;

    /**
     * The proxy url.
     */
    private static JTextField proxyUrl;

    /**
     * The proxy user name.
     */
    private static JTextField proxyUsername;

    /**
     * The proxy password.
     */
    private static JPasswordField proxyPassword;

    public ProxyPanel() {

        super(new MigLayout("ins 0, fill"));

        addProxySettings();
    }

    /**
     * 
     */
    private void addProxySettings() {
        proxyCheckBox = new JCheckBox("Proxy");
        proxyCheckBox.setOpaque(false);

        MouseListener l = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (proxyCheckBox.isSelected())
                    setVisible(true);
                else
                    setVisible(false);
            }
        };

        proxyCheckBox.addMouseListener(l);

        add(proxyCheckBox, "left");
        add(proxyCheckBox, "right");

        if (Configuration.getInstance().getProxyProperties().isUseProxy()) {
            setVisible(true);
            proxyCheckBox.setSelected(true);
        }
        else
            setVisible(false);

        JLabel proxyPortLabel = new JLabel("Proxy port");
        proxyPort = new QTextField(false, keyboardPanel);
        proxyPort.setText(Configuration.getInstance().getProxyProperties().getProxyPort() + "");
        add(proxyPortLabel, "right");
        add(proxyPort, "left");

        JLabel proxyUrlLabel = new JLabel("Proxy URL");
        proxyUrl = new QTextField(false, keyboardPanel);
        proxyUrl.setText(Configuration.getInstance().getProxyProperties().getProxyUrl() + "");
        add(proxyUrlLabel, "right");
        add(proxyUrl, "left");

        JLabel proxyUsernameLabel = new QLabel("Proxy username");
        proxyUsername = new QTextField(false, keyboardPanel);
        proxyUsername.setText(Configuration.getInstance().getProxyProperties().getProxyUsername()
                + "");
        add(proxyUsernameLabel, "right");
        add(proxyUsername, "left");

        JLabel proxyPasswordLabel = new QLabel("Proxy password (not saved)");
        proxyPassword = new QPasswordField(keyboardPanel);
        add(proxyPasswordLabel, "right");
        add(proxyPassword, "left");
    }
}
