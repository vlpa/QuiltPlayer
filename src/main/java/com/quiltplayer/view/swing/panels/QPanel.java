/**
 * QuiltPlayer v1.0 Copyright (C) 2008-2009 Vlado Palczynski
 * vlado.palczynski@quiltplayer.com http://www.quiltplayer.com This program is
 * free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or (at your option) any later version. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package com.quiltplayer.view.swing.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * @author Vlado Palczynski
 */
public class QPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    // Hit detection.
    Shape shape;

    public QPanel() {
        super();

        setDefaults();
    }

    public QPanel(MigLayout layout) {
        super(layout);

        setDefaults();
    }

    private void setDefaults() {
        setOpaque(true);
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
