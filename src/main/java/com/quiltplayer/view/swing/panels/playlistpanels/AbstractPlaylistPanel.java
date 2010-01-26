package com.quiltplayer.view.swing.panels.playlistpanels;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.properties.Configuration;

public class AbstractPlaylistPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public AbstractPlaylistPanel() {
        super(new MigLayout("insets 0, wrap 1, alignx center, aligny center"));

        setBackground(Configuration.getInstance().getColorConstants().getPlaylistPanelBackground());
        setOpaque(false);
    }

    public AbstractPlaylistPanel(MigLayout layout) {
        super(layout);

        setBackground(Configuration.getInstance().getColorConstants().getPlaylistPanelBackground());
        setOpaque(false);
    }
}
