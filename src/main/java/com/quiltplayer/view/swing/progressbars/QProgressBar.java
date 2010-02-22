package com.quiltplayer.view.swing.progressbars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;

public class QProgressBar extends JProgressBar {

    private static final long serialVersionUID = 1L;

    public QProgressBar(int i1, int i2) {
        super(i1, i2);

        setDefaults();
    }

    private void setDefaults() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder());
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int w = getWidth();
        final int h = getHeight();

        RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        renderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        renderHints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setRenderingHints(renderHints);
        g2d.fillRoundRect(0, 0, w, h, 11, 11);

        super.paintComponent(g2d);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JProgressBar#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
    }
}
