package com.quiltplayer.view.swing.panels;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

/**
 * Scroller should be variable to time and distance. It should follow the fingers position and when
 * released it should decrease against the time and distance of the movement.
 * 
 * @author vlado
 * 
 */
public class QScrollPane extends JScrollPane implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;

    private Integer pressedXPosition;

    private Integer pressedHorizontalBar;

    private Integer releasedXPosition;

    private Integer pressedYPosition;

    private Integer pressedVerticalBar;

    private Integer releasedYPosition;

    private Animator animator;

    private Integer mouseX;

    private int steps;

    public QScrollPane() {
        super();

        setDefaults();
    }

    public QScrollPane(Component panel) {
        super(panel);

        setDefaults();
    }

    private void setDefaults() {

        setOpaque(false);

        addMouseListener(this);
        addMouseMotionListener(this);

        setBorder(BorderFactory.createEmptyBorder());

        setAutoscrolls(true);
        setDoubleBuffered(true);

        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void animate(int distance) {

        if (animator != null && animator.isRunning())
            animator.stop();

        PropertySetter setter = new PropertySetter(this.getHorizontalScrollBar(), "value",
                getHorizontalScrollBar().getValue(), getHorizontalScrollBar().getValue() + distance);
        animator = new Animator(600, setter);
        animator.setDeceleration(0.7f);
        animator.start();

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getX() > pressedXPosition)
            getHorizontalScrollBar().setValue(pressedHorizontalBar - (e.getX() - pressedXPosition));
        else
            getHorizontalScrollBar().setValue(pressedHorizontalBar + (pressedXPosition - e.getX()));

        if (e.getY() > pressedYPosition)
            getVerticalScrollBar().setValue(pressedVerticalBar - (e.getY() - pressedYPosition));
        else
            getVerticalScrollBar().setValue(pressedVerticalBar + (pressedYPosition - e.getY()));
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
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        pressedXPosition = e.getX();
        pressedYPosition = e.getY();

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
        // TODO Auto-generated method stub
    }

}
