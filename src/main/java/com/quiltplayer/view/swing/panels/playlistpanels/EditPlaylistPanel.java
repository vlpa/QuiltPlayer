package com.quiltplayer.view.swing.panels.playlistpanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.EditAlbumController;
import com.quiltplayer.controller.ScanningController;
import com.quiltplayer.internal.id3.model.Id3DataModel;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.neo.NeoAlbum;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.designcomponents.TextFieldComponents;
import com.quiltplayer.view.swing.labels.QLabel;
import com.quiltplayer.view.swing.listeners.EditAlbumListener;
import com.quiltplayer.view.swing.listeners.ScanningListener;
import com.quiltplayer.view.swing.textfields.QTextField;
import com.quiltplayer.view.swing.window.Keyboard;

/**
 * Display the lyrics of the playing song.
 * 
 * @author Vlado Palczynski
 * 
 */
@Component
public class EditPlaylistPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    /**
     * Save property.
     */
    public static final String SAVE = "save ";

    /**
     * Event update configuration.
     */
    public static final String EVENT_UPDATE_CONFIGURATION = "update.configuration";

    private JButton rescanButton;

    private JButton deleteButton;

    private JButton saveButton;

    private Album album;

    @Autowired
    private EditAlbumListener editAlbumListener;

    @Autowired
    private ScanningListener scanningListener;

    @Autowired
    private Keyboard keyboardPanel;

    private JTextField artistName;

    private JTextField albumTitle;

    public EditPlaylistPanel() {
        super();

        setBackground(ColorConstantsDark.PLAYLIST_BACKGROUND);
        setOpaque(true);
    }

    private void init() {
        artistName = new QTextField(keyboardPanel);
        albumTitle = new QTextField(keyboardPanel);

        removeAll();

        setLayout(new MigLayout("wrap 1, ins 0.0cm 0.2cm 0.0cm 0.2cm, fillx, aligny center"));

        final JTextArea infoArea = setupInfoArea();

        boolean bool = false;

        if (album instanceof NeoAlbum)
            bool = true;

        setupDeleteButton();
        setupRescanButton();
        setupSaveButton();

        saveButton.setEnabled(bool);

        add(new QLabel("Update album"), "left, growx");
        add(infoArea, "left, pushx, growx");
        add(TextFieldComponents.textFieldComponentForForms("Artist", artistName, album.getArtist().getArtistName()
                .getNameForSearches(), bool), "left, growx");
        add(TextFieldComponents.textFieldComponentForForms("Album title", albumTitle, album.getTitle(), bool),
                "left, growx");

        JPanel buttonPanel = new JPanel(new MigLayout("insets 0, wrap 3"));
        buttonPanel.setBackground(ColorConstantsDark.BACKGROUND);
        buttonPanel.add(saveButton, "growx, " + QButton.MIG_HEIGHT);
        buttonPanel.add(rescanButton, "growx, " + QButton.MIG_HEIGHT);
        buttonPanel.add(deleteButton, "growx, " + QButton.MIG_HEIGHT);

        add(buttonPanel, "gapy 20");
    }

    private JTextArea setupInfoArea() {

        final JTextArea infoArea = new JTextArea(
                "Changing the 'Artist' or 'Album title' fields will update all the ID3 tags in all tracks of this album when pressing 'Save'."
                        + Configuration.lineBreak
                        + Configuration.lineBreak
                        + "When deleating an album the album will only get deleted from the QuiltPlayer database, not the disc.") {

            private static final long serialVersionUID = 1L;

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.Container#paintComponent(java.awt.Graphics)
             */
            @Override
            public void paintComponent(Graphics g) {
                int w = getWidth();
                int h = getHeight();

                // RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                // RenderingHints.VALUE_ANTIALIAS_ON);
                // renderHints.put(RenderingHints.KEY_RENDERING,
                // RenderingHints.VALUE_RENDER_QUALITY);
                // renderHints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                // RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                Graphics2D g2d = (Graphics2D) g;
                // g2d.setRenderingHints(renderHints);
                g2d.setColor(new Color(40, 40, 40));
                g2d.fillRoundRect(0, 0, w, h, 15, 15);

                g2d.setColor(new Color(70, 70, 70));
                g2d.drawRoundRect(0, 0, w - 1, h - 1, 15 - 1, 15 - 1);

                super.paintComponent(g);
            }

        };
        infoArea.setEditable(false);
        infoArea.setWrapStyleWord(true);
        infoArea.setLineWrap(true);
        infoArea.setOpaque(false);
        infoArea.setFont(FontFactory.getFont(14));
        infoArea.setMargin(new Insets(10, 10, 10, 10));
        infoArea.setForeground(new Color(200, 200, 200));

        return infoArea;
    }

    private void setupSaveButton() {
        saveButton = new QButton("Save");
        saveButton.addActionListener(this);
        saveButton.setActionCommand(SAVE);
    }

    private void setupRescanButton() {
        rescanButton = new QButton("Rescan album");
        rescanButton.setToolTipText("Rescans the id3 tags and tries to get the covers for this album.");

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                scanningListener.actionPerformed(new ActionEvent(album, 0, ScanningController.EVENT_RESCAN_ALBUM));
            }
        };

        rescanButton.addActionListener(actionListener);
    }

    private void setupDeleteButton() {
        deleteButton = new QButton("Delete album");
        deleteButton.setToolTipText("Removes this album from the QuiltPlayer storage, not on disc.");

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                editAlbumListener.actionPerformed(new ActionEvent(album, 0, EditAlbumController.EVENT_DELETE_ALBUM));
            }
        };

        deleteButton.addActionListener(actionListener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == SAVE) {

            Id3DataModel model = new Id3DataModel();
            model.setArtistName(artistName.getText());
            model.setAlbumTitle(albumTitle.getText());

            List<Object> l = new ArrayList<Object>();
            l.add(album);
            l.add(model);

            editAlbumListener.actionPerformed(new ActionEvent(l, 0, SAVE));
        }

    }

    public void disableRescanButton() {
        rescanButton.setEnabled(false);
    }

    public void enableRescanButton() {
        rescanButton.setEnabled(true);
    }

    public void changeAlbum(final Album album) {
        this.album = album;

        init();
    }

}
