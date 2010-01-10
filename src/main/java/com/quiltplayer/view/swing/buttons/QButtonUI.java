package com.quiltplayer.view.swing.buttons;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

public class QButtonUI extends BasicButtonUI
{

	public QButtonUI()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicButtonUI#paint(java.awt.Graphics,
	 * javax.swing.JComponent)
	 */
	@Override
	public void paint(Graphics g, JComponent c)
	{
		// TODO Auto-generated method stub
		System.out.println("paint");
		super.paint(g, c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.plaf.metal.MetalButtonUI#paintButtonPressed(java.awt.Graphics
	 * , javax.swing.AbstractButton)
	 */
	@Override
	protected void paintButtonPressed(Graphics g, AbstractButton b)
	{
		System.out.println("paintButtonPressed");
		// TODO Auto-generated method stub
		super.paintButtonPressed(g, b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.metal.MetalButtonUI#paintFocus(java.awt.Graphics,
	 * javax.swing.AbstractButton, java.awt.Rectangle, java.awt.Rectangle,
	 * java.awt.Rectangle)
	 */
	@Override
	protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect,
			Rectangle textRect, Rectangle iconRect)
	{
		System.out.println("paintFocus");
		// TODO Auto-generated method stub
		super.paintFocus(g, b, viewRect, textRect, iconRect);
	}

}
