package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.labels.Highlightable;
import com.quiltplayer.view.swing.listeners.HighlightableMouseListener;

/**
 * @author Vlado Palczynski
 */
public abstract class ScrollableAndHighlightableButton extends ScrollableButton implements Highlightable {

    private static final long serialVersionUID = 1L;

    protected Color background = ColorConstantsDark.BACKGROUND;

    private Color[] gradient = { Color.ORANGE, Color.ORANGE.darker(), Color.ORANGE.darker().darker() };

    private float[] dist = { 0.0f, 0.5f, 1.0f };

    public ScrollableAndHighlightableButton() {
        super();

        setDefaults();
    }

    private void setDefaults() {
        setLayout(new MigLayout(""));

        setBorder(BorderFactory.createEmptyBorder());

        setOpaque(false);

        addMouseListener(new HighlightableMouseListener(background, this));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.labels.Highlightable#highlight()
     */
    @Override
    public void highlight(Color color) {
        background = color;

        repaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(background);

        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 11, 11);
    }
}
