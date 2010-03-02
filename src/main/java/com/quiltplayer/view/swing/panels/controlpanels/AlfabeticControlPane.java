package com.quiltplayer.view.swing.panels.controlpanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.jxlayer.JXLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.SelectionController;
import com.quiltplayer.view.swing.buttons.QPlaylistButton;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.listeners.SelectionListener;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.views.View;

@Component
public class AlfabeticControlPane implements View, ActionListener {
    private static final long serialVersionUID = 1L;

    public static final String ALL = "All";

    public static final String NUMERIC = "0-9";

    private String[] strings = new String[] { ALL, NUMERIC, "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
            "Z" };

    private QPlaylistButton albumsButton;

    private QPlaylistButton artistsButton;

    private JPanel panel;

    @Autowired
    private SelectionListener selectionListener;

    private final List<String> list = new ArrayList<String>();

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.views.View#getUI()
     */
    @Override
    public JComponent getUI() {
        if (panel == null) {
            panel = new JPanel(new MigLayout("insets 0, wrap 1, fill"));

            albumsButton = new QPlaylistButton("Albums");
            albumsButton.addActionListener(this);
            albumsButton.setActionCommand(SelectionController.ALBUMS);
            albumsButton.activate();

            list.add(SelectionController.ALBUMS);

            artistsButton = new QPlaylistButton("Artist");
            artistsButton.addActionListener(this);
            artistsButton.setActionCommand(SelectionController.ARTIST);

            panel.add(albumsButton, "alignx center");
            panel.add(artistsButton, "alignx center");

            final JPanel alfabeticPanel = new JPanel(new MigLayout("insets 0, wrap 1, fill"));

            alfabeticPanel.setOpaque(true);

            for (final String s : strings) {
                JButton button = new QPlaylistButton(s);
                button.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectionListener.actionPerformed(new ActionEvent(list, 0, s));

                    }
                });

                alfabeticPanel.add(button, "alignx center, w 0.75cm, h 0.75cm");
            }

            final QScrollPane pane = new QScrollPane(alfabeticPanel);

            panel.add(new JXLayer<JScrollPane>(pane, new JScrollPaneLayerUI()));
        }

        return panel;
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
}
