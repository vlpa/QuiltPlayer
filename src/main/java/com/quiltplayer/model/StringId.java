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
package com.quiltplayer.model;

/**
 * 
 * 
 * @author Vlado Palczynski
 */
public class StringId
{
	/**
	 * The id.
	 */
	private String id;

	/**
	 * @param id
	 *            the id to set.
	 */
	public StringId(final String id)
	{
		setId(id);
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id)
	{
		this.id = id.toLowerCase().replaceAll("[^\\w\\d]", "");
	}
}
