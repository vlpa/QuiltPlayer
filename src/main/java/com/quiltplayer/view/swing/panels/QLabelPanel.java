/**
 * QuiltPlayer v1.0 Copyright (C) 2008-2009 Vlado Palczynski
 * vlado.palczynski@quiltplayer.com http://www.quiltplayer.com This program is
 * free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or (at your option) any later version. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package com.quiltplayer.view.swing.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.properties.Configuration;

/**
 * @author vlado
 */
public class QLabelPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Color textColor = Configuration.getInstance()
			.getColorConstants().getArtistViewTextColor();

	public QLabelPanel()
	{
		super();
	}

	public QLabelPanel(MigLayout layout)
	{
		super(layout);
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
			setInactive2();
		}
	};

	public void setActive()
	{
		setForeground(Color.WHITE);
	}

	public void setSelected()
	{
		setForeground(Configuration.getInstance().getColorConstants()
				.getArtistViewTextHighlightColor());

	}

	public void setInactive()
	{
		setForeground(textColor);
	}

	public void setInactive2()
	{
		if (getForeground() != textColor)
		{
			setForeground(textColor);
		}
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
