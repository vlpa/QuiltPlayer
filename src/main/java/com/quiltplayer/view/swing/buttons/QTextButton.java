package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.FontFactory;

/**
 * Default text button implementation.
 * 
 * @author Vlado Palczynski.
 */
public class QTextButton extends JButton {
    private static final long serialVersionUID = 1L;

    public QTextButton(String label) {
        super(label);

        setDefaults();
    }

    private void setDefaults() {
        setFocusable(false);

        setForeground(new Color(220, 220, 220));
        setBackground(ColorConstantsDark.BACKGROUND);

        setContentAreaFilled(false);

        setFont(FontFactory.getFont(16f));
    }

    // Hit detection.
    Shape shape;

    public boolean contains(int x, int y) {
        // If the button has changed size,
        // make a new shape object.
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paint(g);
    }
}
