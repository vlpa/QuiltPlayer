package com.quiltplayer.view.swing.textfields;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalTextFieldUI;

/*
 * A custom TextField UI based on Metal that paints
 * text fields with a round border.
 */
public class RoundTextUI extends MetalTextFieldUI {

    public static ComponentUI createUI(JComponent c) {
        return new RoundTextUI();
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        c.setBorder(new RoundBorder());
        c.setOpaque(false);
    }

    protected void paintSafely(Graphics g) {
        JComponent c = getComponent();
        if (!c.isOpaque()) {
            g.setColor(Color.WHITE);
            g.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 20, 20);
        }
        super.paintSafely(g);
    }

    private static class RoundBorder extends AbstractBorder {

        private static final long serialVersionUID = 1L;

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Color oldColor = g.getColor();

            g.setColor(Color.GRAY);
            g.drawRoundRect(x, y, width - 1, height - 1, 10, 10);

            g.setColor(oldColor);
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(4, 4, 4, 4);
        }

        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = 4;
            return insets;
        }

    }

}