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

import org.springframework.stereotype.Component;

import com.quiltplayer.external.covers.model.LocalImage;
import com.quiltplayer.external.covers.util.ImageUtils;
import com.quiltplayer.view.swing.ColorConstantsDark;

@Component
public class CrossFader extends JComponent implements ActionListener {

    private static final long serialVersionUID = 1L;

    private javax.swing.Timer animator;

    private List<ImageIcon> icons;

    private ImageIcon[] icon;

    private float alpha = 0;

    private int counter = 0;

    private int width;

    public CrossFader() {
    }

    public void startAnimation() {
        if (animator == null)
            animator = new javax.swing.Timer(100, this);

        alpha = 0;
        counter = 0;
        nextIcons();

        animator.setInitialDelay(4000);
        animator.restart();
    }

    public void setImages(final List<LocalImage> images) {
        counter = 0;
        icon = new ImageIcon[2];

        icons = new ArrayList<ImageIcon>();
        new Thread() {

            /*
             * (non-Javadoc)
             * 
             * @see java.lang.Thread#run()
             */
            @Override
            public void run() {
                for (LocalImage image : images) {
                    ImageIcon tmp = new ImageIcon(image.getLargeImage().getAbsolutePath());
                    icons.add(ImageUtils.scalePicture(tmp, width));
                }

                startAnimation();
                repaint();
            }
        }.start();
    }

    public void paintComponent(Graphics g) {

        if (width != getWidth())
            width = getWidth();

        if (width > 0) {
            if (icons != null && icons.size() > 0) {

                Graphics2D g2d = (Graphics2D) g;

                if (icons.size() > 1) {

                    g2d.setPaint(ColorConstantsDark.PLAYLIST_BACKGROUND);
                    g2d.fillRect(0, 0, getWidth(), getHeight());

                    if (alpha < 10) {
                        alpha = alpha + 0.5f;
                    }
                    else {
                        animator.stop();
                        nextIcons();
                        alpha = 0;
                        animator.restart();
                    }

                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                            1.0f - alpha * 0.1f));
                    g2d.drawImage(icon[0].getImage(), 0, 0, (int) icon[0].getIconWidth(), icon[0]
                            .getIconHeight(), this);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                            alpha * 0.1f));
                    g2d.drawImage(icon[1].getImage(), 0, 0, (int) icon[1].getIconWidth(),
                            (int) icon[1].getIconHeight(), this);
                }
                else {
                    g2d.drawImage(icon[0].getImage(), 0, 0, (int) icon[0].getIconWidth(),
                            (int) icon[0].getIconHeight(), this);
                }
            }
        }
    }

    private void nextIcons() {
        if (icons != null && icons.size() > 0) {
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