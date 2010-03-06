package com.quiltplayer.view.swing.listeners;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.quiltplayer.view.swing.labels.Highlightable;
import com.quiltplayer.view.swing.util.ColorUtils;

public class HighlightableMouseListener extends MouseAdapter {

    private Color color;

    private Highlightable component;

    public HighlightableMouseListener(final Color color, final Highlightable component) {
        super();

        this.color = color;
        this.component = component;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        color = ColorUtils.brighten(color);

        component.highlight(color);

    }

    @Override
    public void mouseExited(MouseEvent e) {
        color = ColorUtils.darken(color);

        component.highlight(color);
    }
}
