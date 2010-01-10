package com.quiltplayer.view.swing.util;

import java.awt.Color;

import javax.swing.JComponent;

import com.quiltplayer.properties.Configuration;

public class HighlightColorUtils
{
	private static Color textColor = Configuration.getInstance()
			.getColorConstants().getArtistViewTextColor();

	public static void setSelected(JComponent c)
	{
		c.setForeground(Configuration.getInstance()
				.getColorConstants().getArtistViewTextHighlightColor());

		c.setBackground(Configuration.getInstance()
				.getColorConstants().getSearchBackground());
	}

	public static void setInactive(JComponent c)
	{
		c.setBackground(Configuration.getInstance()
				.getColorConstants().getBackground());

		if (c.getForeground() != textColor)
		{
			c.setForeground(textColor);
		}
	}

}
