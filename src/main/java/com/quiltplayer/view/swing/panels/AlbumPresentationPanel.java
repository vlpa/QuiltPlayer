package com.quiltplayer.view.swing.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.AddAlbumController;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.jotify.JotifyAlbum;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.listeners.AddAlbumListener;

/**
 * Presents info of an album playing.
 * 
 * @author Vlado Palczynski
 */
@Component
public class AlbumPresentationPanel extends QPanel {

    private static final long serialVersionUID = 1L;

    private JTextArea artistNameLabel;

    private JTextArea albumTitleLabel;

    private JLabel addAlbumIcon;

    private JTextArea yearAndLabelArea;

    @Autowired
    private AddAlbumListener addAlbumListener;

    private Album album;

    public AlbumPresentationPanel() {
        super(new MigLayout("insets 0, wrap 2"));

        setOpaque(false);

        artistNameLabel = setupArtistNameArea("");
        albumTitleLabel = setupAlbumTitleArea("");
        addAlbumIcon = setupAddAlbumIcon();
        yearAndLabelArea = setupYearAndLabelArea("");

        add(this.artistNameLabel, "left, w 100%, newline");
        add(this.albumTitleLabel, "left, w 80%, newline");
        add(this.addAlbumIcon, "right");
        add(this.yearAndLabelArea, "left, w 100%, newline");

    }

    private JLabel setupAddAlbumIcon() {
        final JLabel label = new JLabel();

        Resource image = new ClassPathResource("1.png");

        ImageIcon icon = null;
        try {
            icon = new ImageIcon(image.getURL());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        label.setIcon(icon);
        label.setVisible(false);
        label.setToolTipText("Add album to your collection.");

        label.addMouseListener(new MouseAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                addAlbumListener.actionPerformed(new ActionEvent(album, 0,
                        AddAlbumController.EVENT_ADD_ALBUM));
            }

        });

        return label;
    }

    private JTextArea setupArtistNameArea(final String name) {
        final JTextArea textArea = new JTextArea();
        textArea.setText(name);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setForeground(new Color(110, 110, 110));
        textArea.setFont(FontFactory.getSansFont(18f).deriveFont(Font.PLAIN));

        return textArea;
    }

    private JTextArea setupAlbumTitleArea(final String title) {
        final JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setForeground(new Color(200, 200, 200));
        textArea.setFont(FontFactory.getSansFont(12f));

        return textArea;
    }

    private JTextArea setupYearAndLabelArea(final String yearAndLabel) {
        final JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setForeground(new Color(110, 110, 110));
        textArea.setFont(FontFactory.getSansFont(12f));

        return textArea;
    }

    /**
     * @param albumTitle
     *            the albumTitle to set
     */
    private final void setAlbumTitle(final String albumTitle, final String year) {
        albumTitleLabel.setText(albumTitle);
    }

    /**
     * @param artistName
     *            the artistName to set
     */
    private final void setArtistName(final String artistName) {
        artistNameLabel.setText(artistName);
    }

    /**
     * @param artistName
     *            the artistName to set
     */
    private final void setYearAndLabel(final String year, final String label) {
        yearAndLabelArea.setText("");

        if (StringUtils.isNotBlank(year))
            if (year.length() > 4)
                yearAndLabelArea.setText(year.substring(0, 4) + " ");
            else
                yearAndLabelArea.setText(year);

        if (StringUtils.isNotBlank(label))
            yearAndLabelArea.setText(yearAndLabelArea.getText() + " " + label);

    }

    public void update(final Album album) {
        this.album = album;

        setAlbumTitle(album.getTitle(), album.getYear());
        setArtistName(album.getArtist().getArtistName().getName());
        setYearAndLabel(album.getYear(), album.getLabel());

        if (album instanceof JotifyAlbum)
            addAlbumIcon.setVisible(true);
        else
            addAlbumIcon.setVisible(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(g);
    }
}
