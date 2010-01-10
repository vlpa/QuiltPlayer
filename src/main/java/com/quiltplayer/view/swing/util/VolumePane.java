package com.quiltplayer.view.swing.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class VolumePane extends ValuePane {
    private static final long serialVersionUID = 1L;

    public static final String ACTION_CMD = "volume.change";

    private JSlider volumeSlider;

    private int volume;

    private ActionListener listener;

    public VolumePane() {
        volume = 1;

        volumeSlider = new JSlider(SwingConstants.HORIZONTAL);
        volumeSlider.setValue(1);

        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int newValue = volumeSlider.getValue();
                setValue(newValue);

                listener.actionPerformed(new ActionEvent(new Object(), 0,
                        ACTION_CMD));
            }

        });
        // volume.setActionCommand(ACTION_CMD);

        add(volumeSlider);
    }

    /*
     * @see org.coverok.view.ValuePane#getValue()
     */
    @Override
    public int getValue() {
        return volume;
    }

    /*
     * @see org.coverok.view.ValuePane#setValue(int)
     */
    @Override
    public void setValue(int value) {
        volume = value;

    }

    public void addActionListener(ActionListener l) {
        this.listener = l;
    }

    public void removeActionListener(ActionListener listener) {
        this.listener = null;
    }
}
