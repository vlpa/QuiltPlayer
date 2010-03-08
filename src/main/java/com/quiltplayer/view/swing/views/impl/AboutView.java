package com.quiltplayer.view.swing.views.impl;

import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.view.swing.views.View;

@org.springframework.stereotype.Component
public class AboutView implements Serializable, View {

    private static final long serialVersionUID = 1L;

    private JPanel panel;

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.view.components.View#getUI()
     */
    @Override
    public JComponent getUI() {

        panel = new JPanel(new MigLayout(""));
        panel.setOpaque(false);

        return panel;
    }
}
