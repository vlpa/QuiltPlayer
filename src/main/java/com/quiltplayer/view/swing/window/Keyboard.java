package com.quiltplayer.view.swing.window;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.ControlPanelController;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.listeners.ControlPanelListener;
import com.quiltplayer.view.swing.panels.controlpanels.ControlPanel;

/**
 * 
 * Keyboard emulator component.
 * 
 * @author Vlado Palczynski
 * 
 */
@Component
public class Keyboard extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final String CAPS_LOCK = "Caps Lock";
    private static final String ERASE = "<--";
    private static final String CLEAR = "Clear";
    private static final String CLOSE = "Exit";

    private boolean toggler = true;

    @Autowired
    private ControlPanel controlPanel;

    @Autowired
    private ControlPanelListener controlPanelListener;

    private JTextField textField;

    String[][] noCaps = new String[][] { { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" },
            { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", },
            { CAPS_LOCK, "A", "S", "D", "F", "G", "H", "J", "K", "L" }, { "Z", "X", "C", "V", "B", "N", "M" },
            { CLEAR, " ", CLOSE } };

    String[][] caps = new String[][] { { "!", "\"", "#", "¤", "%", "&", "/", "(", ")", "=", ERASE },
            { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", },
            { CAPS_LOCK, "a", "s", "d", "f", "g", "h", "j", "k", "l" }, { "z", "x", "c", "v", "b", "n", "m" },
            { CLEAR, " ", CLOSE } };

    public Keyboard() {
        setLayout(new MigLayout("insets 25, center"));

        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - ((int) this.getWidth() / 2), Toolkit
                .getDefaultToolkit().getScreenSize().height
                / 2 - ((int) this.getHeight() / 2));

        setOpaque(false);
    }

    @PostConstruct
    public void init() {
        setup(toggler);

        setVisible(false);
    }

    private void setup(final boolean capsLock) {
        removeAll();

        final String layout = "w 1.2cm, h 1.2cm";

        final String[][] sds;

        if (capsLock)
            sds = caps;
        else
            sds = noCaps;

        for (String[] s : sds) {
            final JPanel panel = new JPanel(new MigLayout("insets 0"));
            panel.setOpaque(false);

            for (String label : s) {
                final JButton button = new JButton(label) {

                    private static final long serialVersionUID = 1L;

                    /*
                     * (non-Javadoc)
                     * 
                     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
                     */
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2d = (Graphics2D) g;

                        g2d.setPaint(new Color(25, 25, 25));
                        g2d.fillRect(0, 0, getWidth(), getHeight());

                        super.paintComponent(g2d);
                    }
                };
                button.setOpaque(false);
                button.setHorizontalTextPosition(SwingConstants.RIGHT);
                button.setVerticalAlignment(SwingConstants.BOTTOM);
                button.setForeground(Color.WHITE);

                if (label == CAPS_LOCK)
                    setupCapsLockButton(button);
                else if (label == ERASE)
                    setupEraseButton(button);
                else if (label == CLEAR)
                    setupClearButton(button);
                else if (label == CLOSE)
                    setupCloseButton(button);
                else {
                    setupButton(button);
                }

                if (button.getText().equals(" "))
                    panel.add(button, layout + ", w 5cm");
                else
                    panel.add(button, layout);
            }

            add(panel, "center, newline");
        }

        updateUI();
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
                    textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
            }
        });
    }

    private void setupCloseButton(final JButton button) {
        button.addActionListener(controlPanelListener);
        button.setActionCommand(ControlPanelController.EVENT_VIEW_KEYBOARD);
    }

    private void setupClearButton(final JButton button) {
        button.addActionListener(new ActionListener() {
            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event. ActionEvent)
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
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
        button.setFont(FontFactory.getFont(16f).deriveFont(Font.ITALIC));

        button.addActionListener(new ActionListener() {
            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event. ActionEvent)
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField != null)
                    textField.setText(new StringBuilder(textField.getText()).insert(textField.getCaretPosition(),
                            button.getText()).toString());

                // TODO Focus is probably lost as this does not work.
                // textField.setCaretPosition(textField.getCaretPosition() + 1);
            }
        });
    }

    public void setTextField(final JTextField textField) {
        this.textField = textField;

        controlPanel.flashKeyboard();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

        g2d.setPaint(new Color(50, 50, 50));

        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
