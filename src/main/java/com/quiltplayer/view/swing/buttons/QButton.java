package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.quiltplayer.view.swing.FontFactory;

/**
 * Default button implementation.
 * 
 * @author Vlado Palczynski.
 */
public class QButton extends JButton {
    private static final long serialVersionUID = 1L;

    private static final Color DEFAULT = new Color(40, 40, 40);

    private static final Color HOOVER = new Color(80, 80, 80);

    private Color color = DEFAULT;

    public QButton(String label) {
        super(label);

        setDefaults();
    }

    private void setDefaults() {
        setFocusable(false);

        setForeground(new Color(200, 200, 200));

        setContentAreaFilled(false);

        setFont(FontFactory.getFont(14));

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
        });
    }

    // Paint the round background and label.
    protected void paintComponent(Graphics g) {
        int w = getWidth();
        int h = getHeight();

        RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        renderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        renderHints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(renderHints);
        g2d.setColor(color);
        g2d.fillRoundRect(0, 0, w, h, 15, 15);

        g2d.setColor(new Color(70, 70, 70));
        g2d.drawRoundRect(0, 0, w - 1, h - 1, 15 - 1, 15 - 1);

        super.paintComponent(g);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.AbstractButton#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
        // Empty
    }
}
