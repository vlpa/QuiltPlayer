package com.quiltplayer.view.swing.views;

import java.awt.Component;

/**
 * @author Mario Boikov
 */
public interface View
{
	static final String EVENT_VIEW_ARTIST = "view.artist";

	/**
	 * Returns the graphical component.
	 */
	Component getUI();

	/**
	 * Release resources used by this album view.
	 */
	void close();
}