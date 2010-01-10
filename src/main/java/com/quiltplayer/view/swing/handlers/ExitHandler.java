package com.quiltplayer.view.swing.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.quiltplayer.core.storage.neo.NeoSingelton;
import com.quiltplayer.properties.Configuration;

/**
 * Handles exit action.
 * 
 * @author Vlado Palczynski
 */
public class ExitHandler implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		Configuration.getInstance().storeConfiguration();

		NeoSingelton.getInstance().getNeoService().shutdown();

		Runtime.getRuntime().exit(0);
	}
}
