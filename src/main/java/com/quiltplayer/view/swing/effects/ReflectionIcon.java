package com.quiltplayer.view.swing.effects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Paints an ImageIcon with reflection.
 * 
 * @author Vlado Palczynski
 */
public class ReflectionIcon extends ImageIcon {
    private static final long serialVersionUID = 1L;

    private BufferedImage image;

    private int height;

    private int width;

    public ReflectionIcon(String path) {
        try {
            File file = new File(path);
            image = ImageIO.read(file);

            height = image.getHeight();
            width = image.getWidth();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.ImageIcon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
     */
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        float opacity = 0.20f;
        float fadeHeight = 0.60f;
        int gap = 4; // The gap between image and reflection

        /* Paint cover */
        g2d.drawRenderedImage(image, null);

        /* Paint reflection */
        g2d.translate(0, 2 * imageHeight + gap);
        g2d.scale(1, -1);

        BufferedImage reflection = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D rg = reflection.createGraphics();

        rg.drawRenderedImage(image, null);
        rg.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));

        rg.setPaint(new GradientPaint(0, imageHeight * fadeHeight,
                new Color(0.0f, 0.0f, 0.0f, 0.0f), 0, imageHeight + 30, new Color(0.0f, 0.0f, 0.0f,
                        opacity)));
        rg.fillRect(0, 0, imageWidth, imageHeight);

        rg.dispose();

        g2d.drawRenderedImage(reflection, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.ImageIcon#getIconHeight()
     */
    @Override
    public int getIconHeight() {
        return height;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.ImageIcon#getIconWidth()
     */
    @Override
    public int getIconWidth() {
        return width;
    }
}
