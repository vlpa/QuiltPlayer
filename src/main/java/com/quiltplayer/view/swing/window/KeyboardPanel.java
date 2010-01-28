package com.quiltplayer.view.swing.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

import com.quiltplayer.view.swing.FontFactory;

/**
 * 
 * Keyboard emulator component. Will always be on top.
 * 
 * @author Vlado Palczynski
 * 
 */
@Component
public class KeyboardPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField textField;

    String[] row1 = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
    String[] row2 = new String[] { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", };
    String[] row3 = new String[] { "A", "S", "D", "F", "G", "H", "J", "K", "L" };
    String[] row4 = new String[] { "Z", "X", "C", "V", "B", "N", "M" };
    String[] row5 = new String[] { " " };

    public KeyboardPanel() {
        setLayout(new MigLayout("insets 25, center"));

        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2
                - ((int) this.getWidth() / 2), Toolkit.getDefaultToolkit().getScreenSize().height
                / 2 - ((int) this.getHeight() / 2));

        setup();

        setVisible(false);
    }

    private void setup() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        panel.setBackground(Color.DARK_GRAY);

        for (String label : row1) {
            panel.add(getButton(label), "w 1.0cm!, h 1.0cm!");
        }

        this.add(panel, "center,newline");

        panel = new JPanel(new MigLayout("insets 0"));
        for (String label : row2) {
            panel.add(getButton(label), "w 1.0cm!, h 1.0cm!");
        }

        panel.add(setupEraseButton(), "w 1.5cm!, h 1.0cm!");

        this.add(panel, "center, newline");

        panel = new JPanel(new MigLayout("insets 0"));
        for (String label : row3) {
            panel.add(getButton(label), "w 1.0cm!, h 1.0cm!");
        }

        this.add(panel, "center, newline");

        panel = new JPanel(new MigLayout("insets 0"));
        for (String label : row4) {
            panel.add(getButton(label), "w 1.0cm!, h 1.0cm!");
        }

        this.add(panel, "center, newline");

        panel = new JPanel(new MigLayout("insets 0"));
        for (String label : row5) {
            panel.add(getButton(label), "w 5.0cm!, h 1.0cm!");
        }

        this.add(panel, "center, newline");

        // JButton exitButton = setupOkButton(frame);
        // this.add(exitButton, "newline, right, w 2cm");

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

    private JButton setupEraseButton() {
        JButton eraseButton = new JButton("<--");
        eraseButton.addActionListener(new ActionListener() {
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
        return eraseButton;
    }

    private JButton getButton(final String label) {
        final JButton button = new JButton(label);
        button.setFont(FontFactory.getSansFont(16f).deriveFont(Font.ITALIC));

        button.addActionListener(new ActionListener() {
            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event. ActionEvent)
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText(textField.getText() + label.toLowerCase());
            }
        });

        return button;
    }
}
