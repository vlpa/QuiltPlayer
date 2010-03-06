package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;

import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.effects.ReflectionIcon;
import com.quiltplayer.view.swing.images.QIcon;
import com.quiltplayer.view.swing.listeners.ChangeAlbumListener;
import com.quiltplayer.view.swing.listeners.HighlightableMouseListener;
import com.quiltplayer.view.swing.panels.AlbumView;

/**
 * Implementation with squared albums.
 * 
 * @author Vlado Palczynski
 */
public class SquaredAlbumButton extends ScrollableAndHighlightableButton implements AlbumView {
    private static final long serialVersionUID = 1L;

    public Icon icon;

    public Timer currentTimer;

    protected Album album;

    protected ChangeAlbumListener changeAlbumListener;

    public SquaredAlbumButton(final Album album, ChangeAlbumListener changeAlbumListener) {
        super();

        this.album = album;
        this.changeAlbumListener = changeAlbumListener;

        setLayout(new MigLayout("insets 0.3cm 0.3cm 0.3cm 0.3cm, fill"));

        final JLabel icon = setupImage(album);
        final JTextArea title = setupTitleLabelToPanel();
        final JLabel artist = setupYearLabel();
        final JLabel trackLabel = setupTracksLabel();

        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new MigLayout("filly, fillx"));
        panel.add(title, "cell 0 0");
        panel.add(artist, "cell 0 1");
        panel.add(trackLabel, "cell 0 2, bottom");

        add(icon, "cell 0 0, gapx 0cm  0.05cm");
        add(panel, "cell 1 0, aligny top");

        addMouseListener(new HighlightableMouseListener(background, this));
        addMouseListener(new MouseAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                trackLabel.setVisible(true);
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseExited(MouseEvent e) {
                trackLabel.setVisible(false);
            }
        });

    }

    protected JLabel setupImage(Album album) {

        if (album.getImages() != null && album.getImages().size() > 0) {
            icon = new ReflectionIcon(album.getImages().get(0).getMediumImage().getAbsolutePath());
        }
        else
            icon = QIcon.getMedium();

        JLabel iconLabel = new JLabel();
        iconLabel.setOpaque(false);
        iconLabel.setIcon(icon);

        return iconLabel;
    }

    private JTextArea setupTitleLabelToPanel() {
        JTextArea titleLabel = new JTextArea() {

            /*
             * (non-Javadoc)
             * 
             * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
             */
            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                super.paintComponent(g);
            }
        };
        titleLabel.setOpaque(false);
        titleLabel.setEditable(false);
        titleLabel.setLineWrap(true);
        titleLabel.setWrapStyleWord(true);
        titleLabel.setForeground(Configuration.getInstance().getColorConstants()
                .getAlbumViewTitleColor());
        titleLabel.setText(album.getTitle());
        titleLabel.setFont(FontFactory.getSansFont(12f));

        return titleLabel;
    }

    protected JLabel setupYearLabel() {
        final JLabel artistNameLabel = new JLabel();

        if (StringUtils.isNotBlank(album.getYear()) && album.getYear().length() > 3) {
            artistNameLabel.setFont(FontFactory.getFont(12f));
            artistNameLabel.setForeground(new Color(110, 110, 110));
            artistNameLabel.setOpaque(false);

            artistNameLabel.setText(album.getYear().substring(0, 4));
        }

        return artistNameLabel;
    }

    protected JLabel setupTracksLabel() {
        final JLabel tracksLabel = new JLabel();
        tracksLabel.setFont(FontFactory.getSansFont(12f));
        tracksLabel.setText(album.getSongCollection().getSongs().size() + " tracks");
        tracksLabel.setForeground(new Color(200, 200, 200));
        tracksLabel.setVisible(false);

        return tracksLabel;
    }

    /**
     * @return the album
     */
    public Album getAlbum() {
        return album;
    }

    /**
     * @param album
     *            the album to set
     */
    public void setAlbum(Album album) {
        this.album = album;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.buttons.ScrollableButton#triggerAction()
     */
    @Override
    protected void triggerAction() {
        changeAlbumListener.actionPerformed(new ActionEvent(album, 0,
                ChangeAlbumController.EVENT_CHANGE_ALBUM));
    }
}
