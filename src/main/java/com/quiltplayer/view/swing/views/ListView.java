package com.quiltplayer.view.swing.views;

import java.util.List;

/**
 * 
 * 
 * @author Vlado Palczynski
 */
public interface ListView<T> extends View
{
	void setList(List<T> list);
}