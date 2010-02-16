package com.quiltplayer.view.swing.labels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.buttons.ScrollableButton;
import com.quiltplayer.view.swing.listeners.HighlightableMouseListener;

/**
 * @author Vlado Palczynski
 */
public abstract class ScrollableAndHighlightableSearchResultButton extends ScrollableButton
        implements Highlightable {

    private static final long serialVersionUID = 1L;

    protected Color background = ColorConstantsDark.BACKGROUND;

    public ScrollableAndHighlightableSearchResultButton() {
        super();

        setDefaults();
    }

    private void setDefaults() {
        setLayout(new MigLayout("insets 5, wrap 1"));

        setBorder(BorderFactory.createEmptyBorder());

        setBackground(background);

        setOpaque(true);

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

        g2d.setColor(background);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
    }
}
