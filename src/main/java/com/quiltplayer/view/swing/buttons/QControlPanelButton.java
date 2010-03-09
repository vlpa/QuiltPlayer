package com.quiltplayer.view.swing.buttons;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JButton;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import com.quiltplayer.view.swing.FontFactory;

/**
 * Default control panel button implementation.
 * 
 * @author Vlado Palczynski.
 */
public class QControlPanelButton extends JButton {

    private static final long serialVersionUID = 1L;

    private String label;

    private float defaultAlpha = 0.65f;

    private float currentAlpha = defaultAlpha;

    private float highlightAlpha = 1.0f;

    private boolean active;

    private Animator animator = new Animator(0);

    public QControlPanelButton(String label, Icon icon, int verticalTextPosition) {
        super(" ", icon);

        this.label = label;

        setDefaults(verticalTextPosition);
    }

    private void setDefaults(int verticalTextPosition) {

        setOpaque(true);

        setHorizontalTextPosition(AbstractButton.CENTER);
        setHorizontalAlignment(AbstractButton.CENTER);

        setVerticalTextPosition(verticalTextPosition);
        setVerticalAlignment(AbstractButton.CENTER);

        setFocusable(false);

        setForeground(new Color(255, 255, 255));

        setContentAreaFilled(false);

        setFont(FontFactory.getSansFont(11f));

        addMouseListener(listenener);
    }

    private MouseListener listenener = new MouseAdapter() {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            if (!active) {

                if (animator.isRunning())
                    animator.stop();

                setText(label);

                animate(currentAlpha, highlightAlpha);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseExited(MouseEvent e) {
            if (!active) {
                if (animator.isRunning())
                    animator.stop();

                animate(currentAlpha, defaultAlpha);

                setText(" ");

                repaint();
            }
        }
    };

    private void animate(final float fromAlpha, final float toAlpha) {
        if (animator.isRunning())
            animator.stop();

        PropertySetter setter = new PropertySetter(this, "alpha", fromAlpha, toAlpha);
        animator = new Animator(300, setter);
        animator.start();
    }

    // Paint the round background and label.
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (active) {
            g2d.setBackground(Color.BLACK);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 9, 9);
        }

        g2d = (Graphics2D) g;
        g2d.setComposite(makeComposite());

        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        // No border should be painted.
    }

    public void inactivate() {
        setText(" ");
        active = false;

        animate(currentAlpha, defaultAlpha);
    }

    public void activate() {
        setText(label);
        active = true;

        animate(currentAlpha, highlightAlpha);
    }

    /*
     * Set alpha composite. For example, pass in 1.0f to have 100% opacity pass in 0.25f to have 25%
     * opacity.
     */
    private AlphaComposite makeComposite() {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, currentAlpha));
    }

    /**
     * @return the alpha
     */
    public final float getAlpha() {
        return currentAlpha;
    }

    /**
     * @param alpha
     *            the alpha to set
     */
    public final void setAlpha(float alpha) {
        this.currentAlpha = alpha;

        repaint();
    }
}