package com.quiltplayer.view.swing.textarea;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTextArea;

/**
 *TODO This is almost the same class as ScrollableButton
 * 
 * @author Vlado Palczynski
 * 
 */
public abstract class ScrollableTextArea extends JTextArea {

    protected static final long serialVersionUID = 1L;

    protected boolean moved;

    private int clickedYPosition;

    public ScrollableTextArea() {
        super();

        setDefaults();
    }

    private void setDefaults() {
        setOpaque(false);

        addMouseListener(new MouseAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!moved) {
                    triggerAction();
                }
                else
                    moved = false;
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
             */
            @Override
            public void mousePressed(MouseEvent e) {
                moved = false;

                clickedYPosition = e.getYOnScreen();
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                /* No other mouse pointer */
                setCursor(Cursor.getDefaultCursor());
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
                int movement;

                if (e.getYOnScreen() <= clickedYPosition)
                    movement = clickedYPosition - e.getYOnScreen();
                else
                    movement = e.getYOnScreen() - clickedYPosition;

                if (movement >= 20)
                    moved = true;
            }
        });
    }

    protected abstract void triggerAction();
}
