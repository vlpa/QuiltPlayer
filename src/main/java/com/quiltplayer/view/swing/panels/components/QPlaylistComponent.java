package com.quiltplayer.view.swing.panels.components;

import javax.swing.JPanel;
import javax.swing.JSeparator;

import net.miginfocom.swing.MigLayout;

/**
 * @author vlado
 */
public class QPlaylistComponent {
    protected JPanel mainPanel = new JPanel(new MigLayout("insets 0, wrap 1, top"));

    protected JSeparator separator;

    public QPlaylistComponent() {
        // mainPanel.setOpaque(true);
        // mainPanel.setBackground(ColorConstantsDark.ARTISTS_PANEL_BACKGROUND);
    }
}
