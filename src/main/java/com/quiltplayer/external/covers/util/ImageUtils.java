package com.quiltplayer.external.covers.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * Utility methods regarding images.
 * 
 * @author Vlado Palczynski
 */
public class ImageUtils {
    /**
     * Size of the large cover.
     */
    // public static int MAX_IMAGE_WIDTH = ((Double)
    // (Toolkit.getDefaultToolkit()
    // .getScreenResolution() * 3.4)).intValue();
    //
    // public static int MEDIUM_IMAGE_WIDTH = ((Double) (Toolkit
    // .getDefaultToolkit().getScreenResolution() * 1.365)).intValue();
    //
    // public static int SMALL_IMAGE_WIDTH = ((Double) (Toolkit
    // .getDefaultToolkit().getScreenResolution() * 1.021)).intValue();
    public static int getMaxWidth() {
        return ((Double) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth())
                .intValue();
    }

    public static int getMaxHeight() {
        return ((Double) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight())
                .intValue();
    }

    public static ImageIcon scalePicture(ImageIcon icon, int width) {
        ImageIcon imageIcon = new ImageIcon(icon.getImage().getScaledInstance(width, -1,
                Image.SCALE_SMOOTH));
        int borderWidth = 1;
        int spaceAroundIcon = 0;
        Color borderColor = new Color(40, 40, 40);

        BufferedImage bi = new BufferedImage(imageIcon.getIconWidth()
                + (2 * borderWidth + 2 * spaceAroundIcon), imageIcon.getIconHeight()
                + (2 * borderWidth + 2 * spaceAroundIcon), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bi.createGraphics();
        g.setColor(borderColor);
        g.drawImage(imageIcon.getImage(), borderWidth + spaceAroundIcon, borderWidth
                + spaceAroundIcon, null);
        g.drawRect(0, 0, bi.getWidth() - 1, bi.getHeight() - 1);
        g.dispose();

        return new ImageIcon(bi);
    }

    public static BufferedImage scalePicture(Image image, int width) {
        ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(width, -1, Image.SCALE_SMOOTH));
        int borderWidth = 1;
        int spaceAroundIcon = 0;
        Color borderColor = new Color(40, 40, 40);

        BufferedImage bi = new BufferedImage(imageIcon.getIconWidth()
                + (2 * borderWidth + 2 * spaceAroundIcon), imageIcon.getIconHeight()
                + (2 * borderWidth + 2 * spaceAroundIcon), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bi.createGraphics();
        g.setColor(borderColor);
        g.drawImage(imageIcon.getImage(), borderWidth + spaceAroundIcon, borderWidth
                + spaceAroundIcon, null);
        g.drawRect(0, 0, bi.getWidth() - 1, bi.getHeight() - 1);
        g.dispose();

        return bi;
    }
}
