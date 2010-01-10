package com.quiltplayer.view.swing.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.model.Artist;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.labels.ArtistLabel;

public class AlfabeticArtistPane extends JPanel
{
	private static final long serialVersionUID = 1L;

	private String character;

	private ActionListener listener;

	public AlfabeticArtistPane()
	{
		super(new MigLayout("insets 0, wrap 1"));

		setOpaque(false);
	}

	public void setup(final String character, final List<Artist> artists)
	{
		this.character = character;

		final JLabel charLabel = new JLabel();

		charLabel.setFont(FontFactory.getFont(15f));
		charLabel.setForeground(Configuration.getInstance()
				.getColorConstants().getArtistViewCharColor());
		charLabel.setText(" " + character);
		charLabel.setBackground(Configuration.getInstance()
				.getColorConstants().getArtistViewCharBackground());
		charLabel.setBorder(BorderFactory
				.createLineBorder(new Color(50, 50, 50)));
		charLabel.setOpaque(true);

		add(charLabel, "w 4cm!, h 0.75cm");

		for (final Artist artist : artists)
		{
			final ArtistLabel label = new ArtistLabel(artist);
			label.addActionListener(listener);
			label.setFont(FontFactory.getSansFont(14f));

			add(label, "wmax 4cm, h 0.4cm");
		}
	}

	public void setActive()
	{
		setForeground(Color.WHITE);
	}

	public void setSelected()
	{
		if (getForeground() != Color.WHITE)
		{
			setForeground(Color.LIGHT_GRAY);
		}
	}

	public void setInactive()
	{
		setForeground(Color.DARK_GRAY);
	}

	public void setInactive2()
	{
		if (getForeground() != Color.WHITE)
		{
			setForeground(Color.DARK_GRAY);
		}
	}

	public String getCharacter()
	{
		return character;
	}

	public void addActionListener(ActionListener listener)
	{
		this.listener = listener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		super.paintComponent(g2d);
	}
}
