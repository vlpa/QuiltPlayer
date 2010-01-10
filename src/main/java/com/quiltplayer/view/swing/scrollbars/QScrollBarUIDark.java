package com.quiltplayer.view.swing.scrollbars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * @author Vlado Palczynski
 */
public class QScrollBarUIDark extends BasicScrollBarUI {
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        int w = thumbBounds.width;
        int h = thumbBounds.height;

        g.translate(thumbBounds.x, thumbBounds.y);

        g.setColor(Color.DARK_GRAY);
        g.drawRect(0, 0, w - 1, h - 1);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w - 1, h - 1);

        g.setColor(Color.DARK_GRAY);
        g.drawLine(1, 1, 1, h - 2);
        g.drawLine(2, 1, w - 3, 1);

        g.translate(-thumbBounds.x, -thumbBounds.y);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(new Color(40, 40, 40));
        g.fillRect((int) trackBounds.getX(), (int) trackBounds.getY(),
                (int) trackBounds.getWidth(), (int) trackBounds.getHeight());
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = super.createIncreaseButton(orientation);
        button.setBackground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        return button;
    }

    /*
     * @see javax.swing.plaf.basic.BasicScrollBarUI#paint(java.awt.Graphics,
     * javax.swing.JComponent)
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton button = super.createIncreaseButton(orientation);
        button.setBackground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        return button;
    }
}
