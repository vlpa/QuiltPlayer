package com.quiltplayer.view.swing.views.impl;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.utils.ClassPathUtils;
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

        panel = new JPanel(new MigLayout("ins 0, fill"));
        panel.setOpaque(false);

        ImageIcon icon = ClassPathUtils.getIconFromClasspathWithoutScaling("icon/quilticon.png");

        final JLabel label = new JLabel(icon);

        panel.add(label, "center");

        return panel;
    }
}
