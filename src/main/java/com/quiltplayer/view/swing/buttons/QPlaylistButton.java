package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Default button implementation.
 * 
 * @author Vlado Palczynski.
 */
public class QPlaylistButton extends QButton {
    private static final long serialVersionUID = 1L;

    private boolean active;

    public QPlaylistButton(final String label) {
        super(label);

        setDefaults();
    }

    protected void addMouseListener() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                if (!active) {
                    color = DEFAULT;

                    repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!active) {
                    color = HOOVER;

                    repaint();
                }
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if (!active) {
                    color = PRESSED;

                    repaint();
                }
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!active) {
                    color = HOOVER;

                    repaint();
                }
            }
        });
    }

    public void activate() {
        color = Color.ORANGE;
        setForeground(Color.BLACK);
        active = true;

        repaint();
    }

    public void inactivare() {
        color = DEFAULT;
        setForeground(new Color(200, 200, 200));

        active = false;

        repaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.AbstractButton#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
    }
}
