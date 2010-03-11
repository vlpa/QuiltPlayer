package com.quiltplayer.view.swing.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.FontFactory;

/**
 * Presents info of an album playing.
 * 
 * @author Vlado Palczynski
 */
@Component
public class AlbumPresentationPanel extends QPanel {

    private static final long serialVersionUID = 1L;

    private JLabel artistNameLabel;

    private JLabel albumTitleLabel;

    private JLabel yearAndLabelArea;

    public AlbumPresentationPanel() {
        super(new MigLayout("insets 0, fill"));

        setOpaque(false);

        artistNameLabel = setupArtistNameArea(" ");
        albumTitleLabel = setupAlbumTitleArea(" ");
        yearAndLabelArea = setupYearAndLabelArea(" ");

        add(this.artistNameLabel, "left, north, gapy 0 0.1cm, shrink");
        add(this.albumTitleLabel, "left, north, gapy 0.0cm 0.1cm, shrink");
        add(this.yearAndLabelArea, "left, north, newline");
    }

    private JLabel setupArtistNameArea(final String name) {
        final JLabel label = new JLabel();
        label.setText(name);
        label.setOpaque(false);
        label.setForeground(new Color(110, 110, 110));
        label.setFont(FontFactory.getSansFont(18f).deriveFont(Font.PLAIN));

        return label;
    }

    private JLabel setupAlbumTitleArea(final String title) {
        final JLabel label = new JLabel();
        label.setOpaque(false);
        label.setForeground(new Color(200, 200, 200));
        label.setFont(FontFactory.getSansFont(12f));

        return label;
    }

    private JLabel setupYearAndLabelArea(final String yearAndLabel) {
        final JLabel label = new JLabel();
        label.setOpaque(false);
        label.setForeground(new Color(110, 110, 110));
        label.setFont(FontFactory.getSansFont(12f));

        return label;
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
            else if (year.length() == 4)
                yearAndLabelArea.setText(year + " ");

        if (StringUtils.isNotBlank(label))
            yearAndLabelArea.setText(yearAndLabelArea.getText() + label);

    }

    public void update(final Album album) {
        setAlbumTitle(album.getTitle(), album.getYear());
        setArtistName(album.getArtist().getArtistName().getName());
        setYearAndLabel(album.getYear(), album.getLabel());
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
