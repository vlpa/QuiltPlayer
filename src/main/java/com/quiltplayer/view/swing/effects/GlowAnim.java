package com.quiltplayer.view.swing.effects;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GlowAnim implements ActionListener
{
	private Component comp;

	private int steps;

	private Timer timer;

	private JPanel glassPane;

	public GlowAnim(JPanel glassPane, Component comp, int delay)
	{
		this.glassPane = glassPane;
		this.comp = comp;
		this.timer = new Timer(delay, this);

		steps = 15;
	}

	public synchronized void reset(int size, int steps)
	{
		timer.stop();
		timer.setInitialDelay(0);

		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (steps <= 0)
		{

			timer.stop();
			glassPane.remove(comp);
			// glassPane.setVisible(false);

			return;
		}

		steps--;

		comp.setSize(((Double) comp.getSize().getWidth()).intValue() - 2,
				((Double) comp.getSize().getHeight()).intValue() - 2);
	}
}
