package com.quiltplayer.view.swing.panels;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import com.quiltplayer.properties.Configuration;

/**
 * Scroller should be variable to time and distance. It should follow the fingers position and when
 * released it should decrease against the time and distance of the movement.
 * 
 * @author vlado
 * 
 */
public class QScrollPane extends JScrollPane implements MouseListener, MouseMotionListener {

    public enum ScrollDirection {
        HORIZONTAL, VERTICAL
    }

    private static final long serialVersionUID = 1L;

    private MouseEvent lastMouseEvent;

    private Integer movedX = 0;

    private Integer pressedXPosition;

    private Integer pressedHorizontalBar;

    private Integer pressedYPosition;

    private Integer pressedVerticalBar;

    private transient Animator animator;

    private ScrollDirection direction;

    private int movedY;

    public QScrollPane(Component panel, ScrollDirection direction) {
        super(panel);

        this.direction = direction;

        setDefaults();
    }

    private void setDefaults() {

        setOpaque(false);

        addMouseListener(this);
        addMouseMotionListener(this);

        setWheelScrollingEnabled(true);

        setBorder(BorderFactory.createEmptyBorder());

        // setAutoscrolls(true);
        setDoubleBuffered(Configuration.getInstance().getUiProperties().isDoubleBuffer());

        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void animate(int distance) {

        System.out.println("Distance: " + distance);

        if (animator != null && animator.isRunning())
            animator.stop();

        PropertySetter setter;
        if (direction == ScrollDirection.HORIZONTAL) {
            setter = new PropertySetter(this.getHorizontalScrollBar(), "value", getHorizontalScrollBar().getValue(),
                    getHorizontalScrollBar().getValue() + distance);
        }
        else {
            setter = new PropertySetter(this.getVerticalScrollBar(), "value", getVerticalScrollBar().getValue(),
                    getVerticalScrollBar().getValue() + distance);
        }

        animator = new Animator(600, setter);
        animator.setDeceleration(1f);
        animator.start();

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Click");
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getXOnScreen() > pressedXPosition)
            getHorizontalScrollBar().setValue(pressedHorizontalBar - (e.getXOnScreen() - pressedXPosition));
        else
            getHorizontalScrollBar().setValue(pressedHorizontalBar + (pressedXPosition - e.getXOnScreen()));

        if (e.getYOnScreen() > pressedYPosition)
            getVerticalScrollBar().setValue(pressedVerticalBar - (e.getYOnScreen() - pressedYPosition));
        else
            getVerticalScrollBar().setValue(pressedVerticalBar + (pressedYPosition - e.getYOnScreen()));

        int x = lastMouseEvent.getXOnScreen() - e.getXOnScreen();
        int y = lastMouseEvent.getYOnScreen() - e.getYOnScreen();

        if (x != 0)
            movedX = x;
        if (y != 0)
            movedY = y;

        lastMouseEvent = e;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        lastMouseEvent = e;
        movedX = 0;
        movedY = 0;

        pressedXPosition = e.getXOnScreen();
        pressedYPosition = e.getYOnScreen();

        pressedHorizontalBar = getHorizontalScrollBar().getValue();
        pressedVerticalBar = getVerticalScrollBar().getValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (direction == ScrollDirection.HORIZONTAL)
            animate(movedX * 5);
        else
            animate(movedY * 5);
    }
}
