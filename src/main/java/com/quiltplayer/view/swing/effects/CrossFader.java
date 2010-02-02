package com.quiltplayer.view.swing.effects;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.external.covers.model.LocalImage;
import com.quiltplayer.external.covers.util.ImageUtils;

public class CrossFader extends JComponent implements ActionListener {

    private static final long serialVersionUID = 1L;

    private javax.swing.Timer animator;

    private List<ImageIcon> icons;

    private int alpha = 0;

    private int counter = 0;

    private ImageIcon[] icon = new ImageIcon[2];

    public CrossFader() {
        animator = new javax.swing.Timer(100, this);
        animator.start();
    }

    public void startAnimation() {
        alpha = 0;
        counter = 0;
        nextIcons();

        animator.setInitialDelay(4000);
        animator.restart();
    }

    public void setImages(List<LocalImage> images) {
        icons = new ArrayList<ImageIcon>();

        for (LocalImage image : images) {
            icons.add(new ImageIcon(ImageUtils.scalePicture(new ImageIcon(image.getLargeImage()
                    .getAbsolutePath()).getImage(), ImageSizes.LARGE.getSize())));
        }
    }

    public void paintComponent(Graphics g) {
        if (icons != null) {

            Graphics2D g2d = (Graphics2D) g;

            if (icons.size() > 1) {

                g2d.setPaint(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());

                if (alpha < 10) {
                    alpha = alpha + 1;
                }
                else {
                    animator.stop();
                    nextIcons();
                    alpha = 0;
                    animator.restart();
                }

                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                        1.0f - alpha * 0.1f));
                g2d.drawImage(icon[0].getImage(), 0, 0, (int) icon[0].getIconWidth(), (int) icon[0]
                        .getIconHeight(), this);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha * 0.1f));
                g2d.drawImage(icon[1].getImage(), 0, 0, (int) icon[1].getIconWidth(), (int) icon[1]
                        .getIconHeight(), this);
            }
            else {
                g2d.drawImage(icon[0].getImage(), 0, 0, (int) icon[0].getIconWidth(), (int) icon[0]
                        .getIconHeight(), this);
            }
        }

    }

    private void nextIcons() {
        if (icons != null) {
            icon[0] = icons.get(counter);
            icon[1] = icons.get(getCounter());
        }
    }

    private int getCounter() {
        counter++;

        if (icons.size() > counter)
            return counter;

        return counter = 0;
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}