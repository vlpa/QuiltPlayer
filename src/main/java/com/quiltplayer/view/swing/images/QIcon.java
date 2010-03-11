package com.quiltplayer.view.swing.images;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;

import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;

/**
 * Default button implementation.
 * 
 * @author Vlado Palczynski.
 */
public class QIcon extends ImageIcon {

    private static final long serialVersionUID = 1L;

    private int width;

    private String label = "No cover";

    private static QIcon mediumInstance;

    private Color[] gradient = { new Color(40, 40, 40), new Color(35, 35, 35),
            new Color(25, 25, 25), new Color(20, 20, 20) };

    private QIcon(QIcon icon, int width) {
        this.width = width;
    }

    public static ImageIcon getMedium() {
        if (mediumInstance == null)
            return new QIcon(mediumInstance, ImageSizes.MEDIUM.getSize());

        return mediumInstance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.ImageIcon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
     */
    @Override
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(0, width);
        float[] dist = { 0.0f, 0.50f, 0.53f, 1.0f };

        if (gradient == null) {
            gradient = Configuration.getInstance().getColorConstants()
                    .getControlPanelGradientColors();
        }

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist, gradient);

        g2d.setPaint(p);

        /**
         * Arcs must be uneven or is gets unsymmetrically.
         */
        g2d.fillRoundRect(0, 0, width, width, 0, 0);

        g2d.drawString("quiltplayer", 150, 150);

        Font font = FontFactory.getFont(12f);
        g.setFont(font);

        char[] carray = label.toCharArray();

        g.setColor(new Color(90, 90, 90));
        g.drawChars(carray, 0, carray.length, 10, 18);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.Icon#getIconHeight()
     */
    @Override
    public int getIconHeight() {
        return width;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.Icon#getIconWidth()
     */
    @Override
    public int getIconWidth() {
        return width;
    }

}
