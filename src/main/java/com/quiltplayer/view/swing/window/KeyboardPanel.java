package com.quiltplayer.view.swing.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.panels.controlpanels.ControlPanel;

/**
 * 
 * Keyboard emulator component.
 * 
 * @author Vlado Palczynski
 * 
 */
@Component
public class KeyboardPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final String CAPS_LOCK = "Caps Lock";
    private static final String ERASE = "<--";

    private boolean toggler = false;

    @Autowired
    private ControlPanel controlPanel;

    private JTextField textField;

    String[][] noCaps = new String[][] {
            { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ERASE },
            { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", },
            { CAPS_LOCK, "A", "S", "D", "F", "G", "H", "J", "K", "L" },
            { "Z", "X", "C", "V", "B", "N", "M" }, { " " } };

    String[][] caps = new String[][] {
            { "!", "\"", "#", "¤", "%", "&", "/", "(", ")", "=", ERASE },
            { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", },
            { CAPS_LOCK, "a", "s", "d", "f", "g", "h", "j", "k", "l" },
            { "z", "x", "c", "v", "b", "n", "m" }, { " " } };

    public KeyboardPanel() {
        setLayout(new MigLayout("insets 25, center"));

        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2
                - ((int) this.getWidth() / 2), Toolkit.getDefaultToolkit().getScreenSize().height
                / 2 - ((int) this.getHeight() / 2));

        setup(toggler);

        setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));

        setVisible(false);
    }

    private void setup(final boolean capsLock) {
        removeAll();

        JPanel panel = new JPanel(new MigLayout("insets 0"));
        panel.setBackground(Color.DARK_GRAY);

        final String layout = "w 1.2cm, h 1.2cm";

        final String[][] sds;

        if (capsLock)
            sds = caps;
        else
            sds = noCaps;

        for (String[] s : sds) {

            panel = new JPanel(new MigLayout("insets 0"));

            for (String label : s) {
                JButton button = new JButton(label);

                if (label == CAPS_LOCK)
                    setupCapsLockButton(button);
                else if (label == ERASE)
                    setupEraseButton(button);
                else {
                    setupButton(button);
                }

                panel.add(button, layout);
            }

            this.add(panel, "center, newline");
        }

        updateUI();
    }

    private JButton setupOkButton(final JFrame frame) {
        JButton exitButton = new JButton("Ok");
        exitButton.addActionListener(new ActionListener() {
            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event. ActionEvent)
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        return exitButton;
    }

    private void setupEraseButton(final JButton button) {
        button.addActionListener(new ActionListener() {
            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event. ActionEvent)
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField.getText().length() > 0)
                    textField.setText(textField.getText().substring(0,
                            textField.getText().length() - 1));
            }
        });
    }

    private JButton setupCapsLockButton(final JButton button) {
        button.addActionListener(new ActionListener() {
            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event. ActionEvent)
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                toggler = !toggler;

                setup(toggler);
            }
        });

        return button;
    }

    private void setupButton(final JButton button) {
        button.setFont(FontFactory.getSansFont(16f).deriveFont(Font.ITALIC));

        button.addActionListener(new ActionListener() {
            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event. ActionEvent)
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField != null)
                    textField.setText(textField.getText() + button.getText());
            }
        });
    }

    public void setTextField(final JTextField textField) {
        this.textField = textField;

        controlPanel.flashKeyboard();
    }
}
