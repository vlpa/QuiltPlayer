package com.quiltplayer.view.swing.labels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.controller.ChangeAlbumController;
import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.util.HighlightColorUtils;

/**
 * Represents a artist in lists.
 * 
 * @author Vlado Palczynski
 */
public class AlbumSearchLabel extends JPanel
{
	public static final String ACTION_GET_ARTIST_ALBUMS = "get.artist";

	private static final long serialVersionUID = 1L;

	protected ActionListener listener;

	protected Album album;

	public AlbumSearchLabel(Album album)
	{
		super(new MigLayout("insets 0, wrap 1"));
		this.album = album;

		Font font = FontFactory.getFont(14f);

		setFont(font);
		setBackground(Configuration.getInstance().getColorConstants()
				.getBackground());

		JLabel artistLabel = new JLabel(album.getArtist().getArtistName()
				.getName());
		artistLabel.setFont(FontFactory.getFont(12f));
		artistLabel.setBackground(Configuration.getInstance()
				.getColorConstants().getBackground());
		artistLabel.setForeground(Color.gray);
		artistLabel.setMaximumSize(new Dimension((int) getMaximumSize()
				.getWidth(), 12));

		JLabel albumLabel = new JLabel(album.getTitle());
		albumLabel.setFont(font);
		albumLabel.setBackground(Configuration.getInstance()
				.getColorConstants().getBackground());
		albumLabel.setForeground(Configuration.getInstance()
				.getColorConstants().getArtistViewTextColor());

		add(artistLabel);
		add(albumLabel);

		this.addMouseListener(mouseListener);
	}

	protected transient MouseListener mouseListener = new MouseAdapter()
	{
		/*
		 * @see
		 * java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseEntered(MouseEvent e)
		{
			setSelected();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseExited(MouseEvent e)
		{
			setInactive();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent e)
		{
			listener.actionPerformed(new ActionEvent(album, 0,
					ChangeAlbumController.EVENT_CHANGE_ALBUM));
		}

	};

	public void setSelected()
	{
		HighlightColorUtils.setSelected(this);
	}

	public void setInactive()
	{
		HighlightColorUtils.setInactive(this);
	}

	public void addActionListener(ActionListener listener)
	{
		this.listener = listener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		super.paint(g);
	}
}
