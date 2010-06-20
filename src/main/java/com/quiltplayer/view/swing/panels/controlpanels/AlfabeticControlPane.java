package com.quiltplayer.view.swing.panels.controlpanels;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.jxlayer.JXLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.GridController;
import com.quiltplayer.controller.SelectionController;
import com.quiltplayer.view.swing.buttons.QPlaylistButton;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.listeners.GridListener;
import com.quiltplayer.view.swing.listeners.SelectionListener;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.panels.QScrollPane.ScrollDirection;

@Component
public class AlfabeticControlPane extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    public static final String ALL = "All";

    public static final String NUMERIC = "0-9";

    // private String[] strings = new String[] { ALL, NUMERIC, "A", "N", "B", "O", "C", "P", "D",
    // "R",
    // "E", "S", "F", "T", "G", "U", "H", "V", "I", "W", "J", "X", "K", "Y", "L", "Z", "M" };

    private String[] strings = new String[] { ALL, NUMERIC, "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    private QPlaylistButton albumsButton;

    private QPlaylistButton artistsButton;

    @Autowired
    private SelectionListener selectionListener;

    @Autowired
    private GridListener gridListener;

    private final List<String> list = new ArrayList<String>();

    private transient Animator animator = new Animator(0);

    private QPlaylistButton selectedButton;

    public AlfabeticControlPane() {
        super(new MigLayout("insets 0, center, fillx"));

        setOpaque(false);
    }

    @PostConstruct
    public void init() {
        setOpaque(false);

        JButton plusButton = new QPlaylistButton("+");
        plusButton.addActionListener(gridListener);
        plusButton.setActionCommand(GridController.EVENT_INCREASE_GRID);

        JButton minusButton = new QPlaylistButton("-");
        minusButton.addActionListener(gridListener);
        minusButton.setActionCommand(GridController.EVENT_DECREASE_GRID);

        albumsButton = new QPlaylistButton("Album");
        albumsButton.addActionListener(this);
        albumsButton.setActionCommand(SelectionController.ALBUMS);
        albumsButton.activate();

        list.add(SelectionController.ALBUMS);

        artistsButton = new QPlaylistButton("Artist");
        artistsButton.addActionListener(this);
        artistsButton.setActionCommand(SelectionController.ARTIST);

        final JPanel alfabeticPanel = new JPanel(new MigLayout("insets 0, flowx, fillx"));

        alfabeticPanel.add(albumsButton, "w 0.6cm, h 0.8cm!");
        alfabeticPanel.add(artistsButton, "w 0.6cm, h 0.8cm!");

        alfabeticPanel.setOpaque(true);

        for (final String s : strings) {
            final JButton button = new QPlaylistButton(s);
            button.setOpaque(false);
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

            alfabeticPanel.add(button, "w 0.6cm, h 1.0cm");
        }

        final QScrollPane pane = new QScrollPane(alfabeticPanel, ScrollDirection.HORIZONTAL);

        JXLayer<JScrollPane> jx = new JXLayer<JScrollPane>(pane, new JScrollPaneLayerUI());

        add(jx, "span 2");
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

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

        super.paintComponent(g);
    }

}
