package com.quiltplayer.view.swing.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.util.ColorUtils;

/**
 * @author Vlado Palczynski
 */
public class HighlightableQPanel extends QPanel {

    private static final long serialVersionUID = 1L;

    protected Color background = ColorConstantsDark.BACKGROUND;

    // Hit detection.
    Shape shape;

    public HighlightableQPanel() {
        super(new MigLayout("insets 7, wrap 1"));

        setDefaults();
    }

    private void setDefaults() {
        setOpaque(true);
        setBackground(background);
        addMouseListener(mouseListener);
    }

    protected MouseListener mouseListener = new MouseAdapter() {

        @Override
        public void mouseEntered(MouseEvent e) {
            background = ColorUtils.brighten(background);

            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            background = ColorUtils.darken(background);

            repaint();
        }
    };

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(background);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
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
