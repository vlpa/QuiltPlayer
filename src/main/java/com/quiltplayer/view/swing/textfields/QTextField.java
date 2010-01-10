package com.quiltplayer.view.swing.textfields;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;

import com.quiltplayer.view.swing.FontFactory;

/**
 * Quilt implementation of a text field.
 * 
 * @author Vlado Palczynski
 * 
 */
public class QTextField extends JTextField {
    private static final long serialVersionUID = -7358149534963586489L;

    private Color borderColor = null;

    private boolean callFocus = false;

    public QTextField() {
        super();

        setDefaults();
    }

    public QTextField(boolean focus) {
        super();

        callFocus = focus;

        setDefaults();
    }

    /**
     * Also used from QPasswordField.
     */
    public void setDefaults() {

        setBackground(new Color(200, 200, 200));
        setCaretColor(Color.black);
        setFont(FontFactory.getFont(16));
        setUI(new RoundTextUI());
    }

    protected void paintComponent(Graphics g) {
        final int w = getWidth();
        final int h = getHeight();

        if (callFocus) {
            requestFocus();
            callFocus = false;
        }

        RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        renderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        renderHints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(renderHints);
        g2d.setColor(Color.white);
        g2d.fillRoundRect(0, 0, w, h, 11, 11);

        if (borderColor != null) {
            g2d.setColor(borderColor);
            g2d.drawRoundRect(0, 0, w - 1, h - 1, 11 - 1, 11 - 1);
        }

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
}