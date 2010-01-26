package com.quiltplayer.view.swing.views;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.quiltplayer.view.swing.panels.QScrollPane;

/**
 * 
 * @author Vlado Palczynski
 * 
 */
public class AbstractView {

    protected JScrollPane getScrollPane(JPanel panel, int vertical) {
        // DebugRepaintingUI debugUI = new DebugRepaintingUI();
        // JXLayer<JComponent> layer = new JXLayer<JComponent>(panel,
        // debugUI);

        QScrollPane scroller = new QScrollPane(panel);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setWheelScrollingEnabled(true);
        scroller.getVerticalScrollBar().setUnitIncrement(vertical);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return scroller;
    }
}
