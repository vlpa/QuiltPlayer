package com.quiltplayer.view.swing.listeners;

import java.awt.event.ActionListener;

import com.quiltplayer.core.scanner.ScanningEvent;

/**
 * @author Vlado Palczynski
 */
public interface ScanningListener extends ActionListener {
    public void scannerEvent(ScanningEvent e);
}
