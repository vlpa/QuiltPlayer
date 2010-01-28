package com.quiltplayer.view.swing.panels;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class QScrollPane extends JScrollPane implements ActionListener {

    private enum State {
        RUNNING, WAITING, STOPPING
    }

    private State state = State.WAITING;

    private static final long serialVersionUID = 1L;

    public Integer pressedPosition;

    private Timer timer;

    private Integer mouseY;

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

        addMouseListener(getMouseListener());
        addMouseMotionListener(getMouseMotionListener());

        // setAutoscrolls(true);
        // setDoubleBuffered(true);

        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    }

    private MouseListener getMouseListener() {
        return new MouseAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
             */
            @Override
            public void mousePressed(MouseEvent e) {
                pressedPosition = e.getY();

                super.mousePressed(e);
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                if (timer != null) {
                    timer.stop();
                    timer = getTimer();
                    state = State.STOPPING;
                    timer.start();
                    steps = 1;
                }

                super.mouseReleased(e);

            }
        };
    }

    private MouseMotionListener getMouseMotionListener() {

        return new MouseMotionAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseY = e.getY();

                if (state != State.RUNNING) {
                    timer = getTimer();
                    timer.start();

                    state = State.RUNNING;
                }

                super.mouseDragged(e);
            }
        };
    }

    private Timer getTimer() {

        if (timer == null || !timer.isRunning()) {
            timer = new Timer(0, this);
            timer.setInitialDelay(0);
        }

        return timer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        final JScrollBar verticalScrollBar = getVerticalScrollBar();

        boolean b = pressedPosition < mouseY;

        if (state == State.RUNNING) {
            if (b) {
                int move = (mouseY - pressedPosition) / 3;
                verticalScrollBar.setValue(verticalScrollBar.getValue() + move);
            }
            else {
                /* Downwards */
                int move = (pressedPosition - mouseY) / 3;
                verticalScrollBar.setValue(verticalScrollBar.getValue() - move);
            }
        }
        else if (state == State.STOPPING) {
            if (b) {
                int distance = (mouseY - pressedPosition);
                verticalScrollBar.setValue(verticalScrollBar.getValue() + (distance / steps) / 3);
            }
            else {
                int distance = (pressedPosition - mouseY);
                verticalScrollBar.setValue(verticalScrollBar.getValue() - (distance / steps) / 3);
            }

            steps++;

            if (steps == 5) {
                timer.stop();
                state = State.WAITING;
            }
        }

        repaint();
    }
}
