package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.quiltplayer.view.swing.FontFactory;

/**
 * Default tab implementation.
 * 
 * @author Vlado Palczynski.
 */
public class QTab extends JButton {
    private static final long serialVersionUID = 1L;

    private Color[] activeGradient = { new Color(100, 100, 100), new Color(65, 65, 65),
            new Color(20, 20, 20), new Color(30, 30, 30) };

    private Color[] passiveGradient = { new Color(60, 60, 60), new Color(40, 40, 40),
            new Color(10, 10, 10), new Color(30, 30, 30) };

    private Color[] pressedGradient = { new Color(50, 50, 50), new Color(35, 35, 35),
            new Color(10, 10, 10), new Color(20, 20, 20) };

    private Color[] gradient = passiveGradient;

    private float[] dist = { 0.0f, 0.65f, 0.75f, 1.0f };

    public QTab(String label) {
        super(label);

        setDefaults();
    }

    public QTab(String label, Color[] gradient) {
        super(label);

        this.gradient = gradient;

        setDefaults();
    }

    private void setDefaults() {
        setFocusable(false);

        setVerticalAlignment(SwingConstants.BOTTOM);
        setHorizontalAlignment(SwingConstants.LEFT);

        setForeground(new Color(220, 220, 220));

        setContentAreaFilled(false);

        setFont(FontFactory.getSansFont(17f));
    }

    // Paint the round background and label.
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed())
            gradient = pressedGradient;

        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(0, getHeight() - 1);

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist, gradient);

        g2d.setPaint(p);

        /**
         * Arcs must be uneven or is gets unsymmetrically.
         */
        g2d.fillRoundRect(1, 1, getWidth() - 1, getHeight(), 11, 11);

        super.paintComponent(g);
    }

    // Paint the border of the button using a simple stroke.
    protected void paintBorder(Graphics g) {
        g.setColor(new Color(20, 20, 20));
        g.drawRect(0, 0, getWidth(), getHeight());
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

    public void inactivate() {
        gradient = passiveGradient;
        repaint();
    }

    public void activate() {
        gradient = activeGradient;
        repaint();
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