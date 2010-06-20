package com.quiltplayer.view.swing.buttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;

/**
 * 
 * @author Vlado Palczynski
 * 
 */
public abstract class ScrollableButton extends JButton {

    protected static final long serialVersionUID = 1L;

    protected boolean moved;

    private int clickedYPosition;

    private int clickedXPosition;

    public ScrollableButton() {
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
                clickedXPosition = e.getXOnScreen();
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
                movement = clickedYPosition - e.getYOnScreen();

                if (movement >= 25 || movement <= -25) {
                    moved = true;

                    return;
                }

                movement = clickedXPosition - e.getXOnScreen();

                if (movement >= 25 || movement <= -25)
                    moved = true;
            }
        });
    }

    protected abstract void triggerAction();
}
