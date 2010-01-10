package com.quiltplayer.external.covers.model;

import java.awt.Toolkit;

/**
 * The width and height is at this moment same.
 * 
 * @author Vlado Palczynski
 * 
 */
public enum ImageSizes
{
	// TODO these should be according to resolution
	SMALL(
			((Double) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.078))
					.intValue()), MEDIUM(((Double) (Toolkit.getDefaultToolkit()
			.getScreenSize().getWidth() * 0.104)).intValue()), LARGE(
			((Double) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.26))
					.intValue());

	private Integer size;

	ImageSizes(Integer size)
	{
		this.size = size;
	}

	public Integer getSize()
	{
		return size;
	}
}
