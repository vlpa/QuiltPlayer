package com.quiltplayer.view.swing.panels.controlpanels;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.jxlayer.JXLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.SelectionController;
import com.quiltplayer.view.swing.buttons.QPlaylistButton;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.listeners.SelectionListener;
import com.quiltplayer.view.swing.panels.QScrollPane;

@Component
public class AlfabeticControlPane extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    public static final String ALL = "All";

    public static final String NUMERIC = "0-9";

    private String[] strings = new String[] { ALL, NUMERIC, "A", "N", "B", "O", "C", "P", "D", "R",
            "E", "S", "F", "T", "G", "U", "H", "V", "I", "W", "J", "X", "K", "Y", "L", "Z", "M" };

    private QPlaylistButton albumsButton;

    private QPlaylistButton artistsButton;

    @Autowired
    private SelectionListener selectionListener;

    private final List<String> list = new ArrayList<String>();

    private Animator animator = new Animator(0);

    private float defaultAlpha = 0.10f;

    private float currentAlpha = defaultAlpha;

    private float highlightAlpha = 1.0f;

    private QPlaylistButton selectedButton;

    public AlfabeticControlPane() {
        super(new MigLayout("insets 0, wrap 1, fill"));

        setOpaque(false);
    }

    @PostConstruct
    public void init() {
        albumsButton = new QPlaylistButton("Albums");
        albumsButton.addActionListener(this);
        albumsButton.setActionCommand(SelectionController.ALBUMS);
        albumsButton.activate();

        list.add(SelectionController.ALBUMS);

        artistsButton = new QPlaylistButton("Artists");
        artistsButton.addActionListener(this);
        artistsButton.setActionCommand(SelectionController.ARTIST);

        add(albumsButton, "alignx left");
        add(artistsButton, "alignx left");

        final JPanel alfabeticPanel = new JPanel(new MigLayout("insets 0, wrap 2, fill"));

        alfabeticPanel.setOpaque(true);

        for (final String s : strings) {
            final JButton button = new QPlaylistButton(s);
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    selectionListener.actionPerformed(new ActionEvent(list, 0, s));

                    if (selectedButton != null)
                        selectedButton.inactivare();

                    selectedButton = (QPlaylistButton) button;
                    selectedButton.activate();
                }
            });

            alfabeticPanel.add(button, "alignx center, w 0.8cm, h 0.8cm");
        }

        final QScrollPane pane = new QScrollPane(alfabeticPanel);

        JXLayer<JScrollPane> jx = new JXLayer<JScrollPane>(pane, new JScrollPaneLayerUI());
        jx.addMouseListener(new MouseAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                if (animator.isRunning())
                    animator.stop();

                animate(currentAlpha, highlightAlpha);
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseExited(MouseEvent e) {
                animate(currentAlpha, defaultAlpha);
            }

        });

        add(jx);

        animate(currentAlpha, highlightAlpha);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == SelectionController.ALBUMS) {
            albumsButton.activate();
            artistsButton.inactivare();
            list.remove(0);
            list.add(SelectionController.ALBUMS);

        }
        else if (e.getActionCommand() == SelectionController.ARTIST) {
            albumsButton.inactivare();
            artistsButton.activate();
            list.remove(0);
            list.add(SelectionController.ARTIST);
        }
    }

    private void animate(final float fromAlpha, final float toAlpha) {
        PropertySetter setter = new PropertySetter(this, "alpha", fromAlpha, toAlpha);
        animator = new Animator(500, setter);
        animator.start();
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

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d = (Graphics2D) g;
        g2d.setComposite(makeComposite());

        super.paintComponent(g);
    }

}
