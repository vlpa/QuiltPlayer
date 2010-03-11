package com.quiltplayer.view.swing.labels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import com.quiltplayer.view.swing.FontFactory;

/**
 * Represents a artist in lists.
 * 
 * @author Vlado Palczynski
 */
public class ImageControlLabel extends JLabel
{
	private static final long serialVersionUID = 1L;

	private Color foregroundColor = new Color(150, 150, 150);

	private Color backgroundColor = Color.BLACK;

	public ImageControlLabel(String text)
	{
		setText(text);

		setHorizontalTextPosition(LEFT);

		setForeground(foregroundColor);

		setBackground(backgroundColor);

		setFont(FontFactory.getFont(16f));

		setBorder(null);

		addMouseListener(mouseListener);
	}

	private transient MouseListener mouseListener = new MouseAdapter()
	{
		/*
		 * @see
		 * java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseEntered(MouseEvent e)
		{
			setForeground(Color.white);
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
			setForeground(foregroundColor);
		}
	};

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
