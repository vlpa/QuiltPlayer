package com.quiltplayer.view.swing.util;

import javax.swing.JPanel;

/**
 * @author Mario Boikov
 */
public abstract class ValuePane extends JPanel {
    private static final long serialVersionUID = 1L;

    public abstract void setValue(int value);

    public abstract int getValue();
}
