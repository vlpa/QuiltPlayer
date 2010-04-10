package com.quiltplayer.view.swing.textfields;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;

import javax.swing.JPasswordField;

import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.window.Keyboard;

/**
 * 
 * @author Vlado Palczynski
 * 
 */
public class QPasswordField extends JPasswordField {
    private static final long serialVersionUID = -7358149534963586489L;

    private Keyboard keyboardPanel;

    public QPasswordField(final Keyboard keyboardPanel) {
        super();

        this.keyboardPanel = keyboardPanel;

        setDefaults();
    }

    private void setDefaults() {
        setCaretColor(Color.black);
        setFont(FontFactory.getFont(15));
        setEchoChar('*');
        setUI(new RoundTextUI());

    }

    protected void paintComponent(Graphics g) {
        final int w = getWidth();
        final int h = getHeight();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
        // RenderingHints.VALUE_ANTIALIAS_ON);
        // renderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // renderHints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
        // RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setColor(Color.white);
        g2d.fillRoundRect(0, 0, w, h, 11, 11);

        super.paintComponent(g);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Component#processFocusEvent(java.awt.event.FocusEvent)
     */
    @Override
    protected void processFocusEvent(FocusEvent e) {
        if (e.getID() == FocusEvent.FOCUS_GAINED)
            keyboardPanel.setTextField(this);

        super.processFocusEvent(e);
    }
}
