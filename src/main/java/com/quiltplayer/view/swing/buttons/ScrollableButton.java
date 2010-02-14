package com.quiltplayer.view.swing.buttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;

public abstract class ScrollableButton extends JButton {

    protected static final long serialVersionUID = 1L;

    protected boolean moved;

    public ScrollableButton() {
        super();

        setDefaults();
    }

    private void setDefaults() {
        addMouseListener(new MouseAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!moved)
                    triggerAction();
                else
                    moved = false;
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                moved = false;
            }
        });

        /*
         * If dragged, a release shouldn't render a click.
         */
        addMouseMotionListener(new MouseMotionAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseMotionAdapter#mouseDragged(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                moved = true;
            }
        });
    }

    abstract void triggerAction();
}
