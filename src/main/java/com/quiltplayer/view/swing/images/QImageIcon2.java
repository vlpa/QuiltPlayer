package com.quiltplayer.view.swing.images;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;

import com.quiltplayer.view.swing.FontFactory;

/**
 * Default button implementation.
 * 
 * @author Vlado Palczynski.
 */
public class QImageIcon2 extends ImageIcon
{
	private static final long serialVersionUID = 1L;

	public QImageIcon2(String path)
	{
		super(path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ImageIcon#paintIcon(java.awt.Component,
	 * java.awt.Graphics, int, int)
	 */
	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y)
	{
		super.paintIcon(c, g, x, y);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Font font = FontFactory.getSansFont(6f).deriveFont(Font.BOLD)
				.deriveFont(6f);
		g.setFont(font);

		g.setColor(new Color(131, 191, 32));
		g.fillRect(getIconWidth() - 30, getIconHeight() - 30
				+ ((200 - getIconHeight()) / 2), 23, 25);

		char[] carray = "Spotify".toCharArray();
		g.setColor(new Color(255, 255, 255));
		g.drawChars(carray, 0, carray.length, getIconWidth() - 28,
				getIconHeight() - 9 + ((200 - getIconHeight()) / 2));
	}
}
