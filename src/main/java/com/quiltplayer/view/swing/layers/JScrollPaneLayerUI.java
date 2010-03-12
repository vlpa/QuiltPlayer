package com.quiltplayer.view.swing.layers;

import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;

import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.AbstractLayerUI;

import com.quiltplayer.view.swing.panels.QScrollPane;

public class JScrollPaneLayerUI extends AbstractLayerUI<JScrollPane> {

    private static final long serialVersionUID = 1L;

    public JScrollPaneLayerUI() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jdesktop.jxlayer.plaf.AbstractLayerUI#processMouseEvent(java.awt.event.MouseEvent,
     * org.jdesktop.jxlayer.JXLayer)
     */
    @Override
    protected void processMouseEvent(MouseEvent e, JXLayer<JScrollPane> l) {

        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            ((QScrollPane) l.getView()).mousePressed(e);
        }
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent e, JXLayer<JScrollPane> l) {

        if (e.getID() == MouseEvent.MOUSE_DRAGGED) {
            ((QScrollPane) l.getView()).mouseDragged(e);
        }
    }
}
