package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Default button implementation.
 * 
 * @author Vlado Palczynski.
 */
public class QSongButton extends JButton {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Object source;

    private MouseEvent lastPressedEvent;

    public QSongButton(String s, int i) {
        super(s);

        setUI(ui);

        setDefaults(i);
    }

    private void setDefaults(int width) {
        setFocusable(false);

        setContentAreaFilled(false);
        setForeground(Color.WHITE);

        setHorizontalAlignment(SwingConstants.LEFT);

        setBorder(BorderFactory.createEmptyBorder());
    }

    /*
     * @see javax.swing.JComponent#processMouseEvent(java.awt.event.MouseEvent)
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.getID() == MouseEvent.MOUSE_ENTERED)
            mouseEnteredCursor();
        else if (e.getID() == MouseEvent.MOUSE_PRESSED)
            lastPressedEvent = e;
        else if (e.getID() == MouseEvent.MOUSE_RELEASED)
            mouseReleasedWorkAround(lastPressedEvent, e);
    }

    private void mouseEnteredCursor() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void mouseReleasedWorkAround(MouseEvent lastPressedEvent, MouseEvent e) {
        // Check so that the positions arn't exactly the same as then the mouse
        // listener will fire an Clicked-event
        if (lastPressedEvent.getX() != e.getX() || lastPressedEvent.getY() != e.getY()) {

            // Check if inside boundries
            double xmin = getLocationOnScreen().getX();
            double xmax = getLocationOnScreen().getX() + getBounds().getWidth();

            double ymin = getLocationOnScreen().getY();
            double ymax = getLocationOnScreen().getY() + getBounds().getHeight();

            double x = e.getLocationOnScreen().getX();
            double y = e.getLocationOnScreen().getY();

            if (x >= xmin && x <= xmax && y >= ymin && y <= ymax) {
                MouseEvent event = new MouseEvent(this, MouseEvent.MOUSE_CLICKED, e.getWhen(), e
                        .getModifiers(), e.getX(), e.getY(), e.getClickCount(), true);

                processEvent(event);
            }
        }
    }

    /*
     * @see javax.swing.JButton#updateUI()
     */
    @Override
    public void updateUI() {
        super.updateUI();

        setUI(ui);
    }

    /**
     * @return the source
     */
    public Object getSource() {
        return source;
    }

    /**
     * @param source
     *            the source to set
     */
    public void setSource(Object source) {
        this.source = source;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paint(g);
    }
}
