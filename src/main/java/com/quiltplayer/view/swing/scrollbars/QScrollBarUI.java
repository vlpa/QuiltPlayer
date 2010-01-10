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
public class QScrollBarUI extends BasicScrollBarUI {
    // this draws scroller
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        int w = thumbBounds.width;
        int h = thumbBounds.height;

        g.translate(thumbBounds.x, thumbBounds.y);

        g.setColor(new Color(180, 180, 180));
        g.drawRect(0, 0, w - 1, h - 1);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w - 1, h - 1);

        g.setColor(new Color(180, 180, 180));
        g.drawLine(1, 1, 1, h - 2);
        g.drawLine(2, 1, w - 3, 1);

        g.translate(-thumbBounds.x, -thumbBounds.y);
    }

    // this draws scroller background
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(new Color(220, 220, 220));
        g.fillRect((int) trackBounds.getX(), (int) trackBounds.getY(),
                (int) trackBounds.getWidth(), (int) trackBounds.getHeight());
    }

    // and methods creating scrollbar buttons
    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = super.createIncreaseButton(orientation);
        button.setBackground(Color.white);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        return button;
    }

    /*
     * (non-Javadoc)
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
        button.setBackground(Color.white);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        return button;
    }
}
