package com.quiltplayer.view.swing.views;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.quiltplayer.view.swing.scrollbars.QScrollBar;

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

        JScrollPane scroller = new JScrollPane(panel);
        scroller.setVerticalScrollBar(new QScrollBar(JScrollBar.VERTICAL));
        scroller.setHorizontalScrollBar(new QScrollBar(JScrollBar.HORIZONTAL));
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setWheelScrollingEnabled(true);
        scroller.getVerticalScrollBar().setUnitIncrement(vertical);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return scroller;
    }
}
