package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.labels.Highlightable;
import com.quiltplayer.view.swing.listeners.HighlightableMouseListener;

/**
 * @author Vlado Palczynski
 */
public abstract class ScrollableAndHighlightableButton extends ScrollableButton implements
        Highlightable {

    private static final long serialVersionUID = 1L;

    protected Color background = ColorConstantsDark.BACKGROUND;

    private Color[] gradient = { Color.ORANGE, Color.ORANGE.darker(),
            Color.ORANGE.darker().darker() };

    private float[] dist = { 0.0f, 0.5f, 1.0f };

    public ScrollableAndHighlightableButton() {
        super();

        setDefaults();
    }

    private void setDefaults() {
        setLayout(new MigLayout(""));

        setBorder(BorderFactory.createEmptyBorder());

        setBackground(background);

        setOpaque(true);

        addMouseListener(new HighlightableMouseListener(background, this));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.labels.Highlightable#highlight()
     */
    @Override
    public void highlight(Color color) {
        background = color;

        repaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (background != ColorConstantsDark.BACKGROUND) {

            Point2D start = new Point2D.Float(0, 0);
            Point2D end = new Point2D.Float(0, getHeight());

            LinearGradientPaint p = new LinearGradientPaint(start, end, dist, gradient);
            g2d.setPaint(p);
        }
        else {
            g2d.setColor(background);
        }

        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
    }
}
