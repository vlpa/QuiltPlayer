package com.quiltplayer.view.swing.labels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.view.swing.FontFactory;

public class StringOrCharLabel extends JLabel {

    private static final long serialVersionUID = 1L;

    private float[] dist = new float[] { 0.0f, 1.0f };

    private Color[] gradient = { new Color(80, 80, 80), new Color(60, 60, 60) };

    public StringOrCharLabel(String title) {
        super(" " + title);

        setDefaults();
    }

    public StringOrCharLabel(char character) {
        super(" " + character);

        setDefaults();
    }

    private void setDefaults() {
        setLayout(new MigLayout("h 1cm"));

        setOpaque(false);

        setForeground(Color.WHITE);

        setFont(FontFactory.getFont(15f));

        setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(0, getHeight());

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist, gradient);

        g2d.setPaint(p);

        g2d.fillRect(0, 0, getWidth(), getHeight());

        super.paintComponent(g2d);
    }
}
