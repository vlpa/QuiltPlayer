package com.quiltplayer.view.swing;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.plaf.BorderUIResource;

public class Border extends BorderUIResource
{

	public Border(Border delegate)
	{
		super(delegate);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see javax.swing.plaf.BorderUIResource#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
	 */
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height)
	{
		// TODO Auto-generated method stub
		super.paintBorder(c, g, x, y, width, height);
	}

	
	
}
