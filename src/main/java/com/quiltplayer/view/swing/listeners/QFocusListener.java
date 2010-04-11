package com.quiltplayer.view.swing.listeners;

import java.awt.event.FocusEvent;

public class QFocusListener implements java.awt.event.FocusListener {
    @Override
    public void focusGained(FocusEvent e) {
        System.out.println(e);
    }

    @Override
    public void focusLost(FocusEvent e) {
        System.out.println(e);
    }
}
