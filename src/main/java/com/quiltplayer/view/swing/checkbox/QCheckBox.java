package com.quiltplayer.view.swing.checkbox;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JCheckBox;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;

/**
 * Implementation of check box.
 * 
 * @author Vlado Palczynski
 * 
 */
public class QCheckBox extends JCheckBox {
    private static final long serialVersionUID = 1L;

    public QCheckBox(String label) {
        super(label);

        setDefaults();
    }

    private void setDefaults() {
        Font font = FontFactory.getFont(12f);

        setFont(font);

        setMaximumSize(new Dimension(200, 30));

        setForeground(Configuration.getInstance().getColorConstants()
                .getQLabelForegroundColor());

        setFocusable(false);

        setOpaque(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paint(g);
    }
}
