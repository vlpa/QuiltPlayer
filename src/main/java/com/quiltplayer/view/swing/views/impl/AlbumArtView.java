package com.quiltplayer.view.swing.views.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.Animator.RepeatBehavior;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.animation.timing.interpolation.SplineInterpolator;
import org.jdesktop.jxlayer.JXLayer;

import com.quiltplayer.external.covers.model.LocalImage;
import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.views.View;

@org.springframework.stereotype.Component
public class AlbumArtView implements Serializable, View {

    private static final long serialVersionUID = 1L;

    private JPanel panel;

    private Album album;

    private QScrollPane pane;

    private JXLayer<JScrollPane> jx;

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.view.components.View#getUI()
     */
    @Override
    public JComponent getUI() {

        panel = new JPanel(new MigLayout("flowx, fill, center")) {
            private static final long serialVersionUID = 1L;

            /*
             * (non-Javadoc)
             * 
             * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
             */
            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                final Color[] gradient = { ColorConstantsDark.BACKGROUND, new Color(90, 90, 90),
                        new Color(40, 40, 40), new Color(20, 20, 20) };

                final float[] dist = { 0.0f, 0.5f, 0.55f, 1.0f };

                Point2D start = new Point2D.Float(0, 0);
                Point2D end = new Point2D.Float(0, getHeight());

                LinearGradientPaint p = new LinearGradientPaint(start, end, dist, gradient);
                g2d.setPaint(p);

                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setOpaque(true);
        panel.setDoubleBuffered(true);

        if (album != null && album.getImages().size() > 0) {
            for (LocalImage localImage : album.getImages()) {
                ImageIcon icon = new ImageIcon(localImage.getLargeImage().getAbsolutePath());

                final JLabel label = new JLabel(icon);
                label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 20));

                panel.add(label, "center, gapx 0.3cm");
            }
        }

        pane = new QScrollPane(panel);

        jx = new JXLayer<JScrollPane>(pane, new JScrollPaneLayerUI());

        return jx;
    }

    public void animate() {
        PropertySetter setter = new PropertySetter(pane.getViewport(), "viewPosition", new Point(0,
                0), new Point(panel.getWidth(), 0));
        Animator animator = new Animator(25000, Animator.INFINITE, RepeatBehavior.REVERSE, setter);
        animator.setInterpolator(new SplineInterpolator(0f, .02f, 0f, 1f));
        animator.start();
    }

    /**
     * @param album
     *            the album to set
     */
    public final void setAlbum(Album album) {
        this.album = album;
    }
}
