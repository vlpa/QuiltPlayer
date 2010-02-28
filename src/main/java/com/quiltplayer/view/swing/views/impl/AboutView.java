package com.quiltplayer.view.swing.views.impl;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JPanel;

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
    public Component getUI() {

        panel = new JPanel();
        panel.setOpaque(false);

        return panel;
    }
}
