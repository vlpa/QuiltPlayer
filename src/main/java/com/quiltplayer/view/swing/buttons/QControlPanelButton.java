package com.quiltplayer.view.swing.buttons;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.quiltplayer.view.swing.FontFactory;

/**
 * Default tab implementation.
 * 
 * @author Vlado Palczynski.
 */
public class QControlPanelButton extends JButton {
    private static final long serialVersionUID = 1L;

    private Icon icon;

    private String label;

    private float alpha = 0.6f;

    private boolean active;

    public QControlPanelButton(String label, Icon icon) {
        super(" ", icon);

        this.icon = icon;
        this.label = label;

        setVerticalTextPosition(AbstractButton.BOTTOM);
        setHorizontalTextPosition(AbstractButton.CENTER);

        setDefaults();
    }

    private void setDefaults() {
        setFocusable(false);

        setForeground(new Color(220, 220, 220));

        setVerticalAlignment(SwingConstants.BOTTOM);

        setHorizontalAlignment(SwingConstants.LEFT);

        setContentAreaFilled(false);

        setFont(FontFactory.getSansFont(11f));

        addMouseListener(listenener);
    }

    private MouseListener listenener = new MouseAdapter() {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            alpha = 1f;
            setText(label);

            repaint();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseExited(MouseEvent e) {
            if (!active) {
                alpha = 0.6f;
                setText(" ");

                repaint();
            }
        }
    };

    // Paint the round background and label.
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d = (Graphics2D) g;
        g2d.setComposite(makeComposite());

        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        // No border should be painted.
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
        alpha = 0.6f;
        setText(" ");
        active = false;

        repaint();
    }

    public void activate() {
        alpha = 1f;
        setText(label);
        active = true;

        repaint();
    }

    /*
     * Set alpha composite. For example, pass in 1.0f to have 100% opacity pass in 0.25f to have 25%
     * opacity.
     */
    private AlphaComposite makeComposite() {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }
}