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
package com.quiltplayer.model.impl;

import java.io.File;

import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.external.covers.model.LocalImage;

/**
 * @author Vlado Palczynski
 */
public class LocalImageImpl implements LocalImage
{
	/**
	 * The height.
	 */
	private String height;

	/**
	 * The width.
	 */
	private String width;

	/**
	 * The path.
	 */
	private File path;

	/**
	 * The path 150.
	 */
	private File path150;

	/**
	 * The path 250.
	 */
	private File path250;

	/**
	 * The type.
	 */
	private String type;

	public LocalImageImpl(File largeImage)
	{
		this.path = largeImage;
		this.path150 = new File(largeImage.getParent(), ImageSizes.SMALL.name()
				+ "-" + largeImage.getName());
		this.path250 = new File(largeImage.getParent(), ImageSizes.MEDIUM
				.name()
				+ "-" + largeImage.getName());
	}

	/**
	 * @return the height
	 */
	public String getHeight()
	{
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(final String height)
	{
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public String getWidth()
	{
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(final String width)
	{
		this.width = width;
	}

	/**
	 * @return the path
	 */
	public File getLargeImage()
	{
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setLargeImage(final File path)
	{
		this.path = path;
	}

	/**
	 * @return the path150
	 */
	public File getSmallImage()
	{
		return path150;
	}

	/**
	 * @param path150
	 *            the path150 to set
	 */
	public void setSmallImage(final File path150)
	{
		this.path150 = path150;
	}

	/*
	 * @see com.quiltplayer.model.LocalImage#getPath250()
	 */
	@Override
	public File getMediumImage()
	{
		return path250;
	}

	/*
	 * @see com.quiltplayer.model.LocalImage#setPath250(java.lang.String)
	 */
	@Override
	public void setMediumImage(File path250)
	{
		this.path250 = path250;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(final String type)
	{
		this.type = type;
	}

}
