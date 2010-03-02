package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.view.swing.FontFactory;

/**
 * Default button implementation.
 * 
 * @author Vlado Palczynski.
 */
public class QButton extends JButton {
    private static final long serialVersionUID = 1L;

    protected static Color DEFAULT = new Color(60, 60, 60);

    protected static final Color HOOVER = new Color(80, 80, 80);

    protected static final Color PRESSED = new Color(184, 207, 229);

    protected Color color = DEFAULT;

    public QButton(final String label) {
        super(label);

        setDefaults();
    }

    protected void setDefaults() {
        setLayout(new MigLayout("fill"));
        setFocusable(false);

        setForeground(new Color(200, 200, 200));

        setContentAreaFilled(false);

        setFont(FontFactory.getFont(14));

        addMouseListener();
    }

    protected void addMouseListener() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                color = DEFAULT;

                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                color = HOOVER;

                repaint();
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
             */
            @Override
            public void mousePressed(MouseEvent e) {
                color = PRESSED;

                repaint();
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                color = HOOVER;

                repaint();
            }

        });
    }

    // Paint the round background and label.
    protected void paintComponent(Graphics g) {
        int w = getWidth();
        int h = getHeight();

        Graphics2D g2d = (Graphics2D) g;

        // RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
        // RenderingHints.VALUE_ANTIALIAS_ON);
        // renderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // renderHints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
        // RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // g2d.setRenderingHints(renderHints);
        g2d.setColor(color);
        g2d.fillRoundRect(0, 0, w, h, 15, 15);

        super.paintComponent(g);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.AbstractButton#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(DEFAULT);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15 - 1, 15 - 1);
    }
}
