package com.quiltplayer.view.swing.views.impl.configurations;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.designcomponents.TextFieldComponents;
import com.quiltplayer.view.swing.listeners.ScanningListener;
import com.quiltplayer.view.swing.textfields.QTextField;
import com.quiltplayer.view.swing.views.impl.ConfigurationView;

@Component
public class ScannerPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public JButton fileChooserButton;

    /**
     * The path to music.
     */
    public JTextField musicPath = new QTextField();

    /**
     * The scan button.
     */
    public JButton scanCoversButton, cancelScanCoversButton;

    /**
     * The update button.
     */
    public JButton scanPathButton, cancelScanPathButton;

    @Autowired
    private ScanningListener scanningListener;

    private JProgressBar musicScrollBar;

    public ScannerPanel() {

        super(new MigLayout("ins 0, fill"));

        fileChooserButton = new QButton("Select");
        // fileChooserButton.addActionListener(this);

        add(TextFieldComponents.textFieldComponentForFormsWithButton("Music directory to scan",
                musicPath, Configuration.ALBUM_COVERS_PATH, false, fileChooserButton),
                "left, w 40%, newline");
    }

    private void addScanCoversButton() {
        scanCoversButton = new QButton("Search covers");
        scanCoversButton.addActionListener(scanningListener);
        scanCoversButton.setActionCommand(ConfigurationView.EVENT_SCAN_COVERS);

        add(scanCoversButton, "w 2.7cm");

        cancelScanCoversButton = new QButton("X");
        cancelScanCoversButton.setOpaque(false);
        cancelScanCoversButton.addActionListener(scanningListener);
        cancelScanCoversButton.setActionCommand(ConfigurationView.EVENT_CANCEL_SCAN_COVERS);

        add(cancelScanCoversButton, "gapy 10 0, w 0.8cm");
    }

    private void addScanMusicButton() {
        scanPathButton = new QButton("Scan path");
        // scanPathButton.addActionListener(this);
        scanPathButton.setActionCommand(ConfigurationView.EVENT_UPDATE_COLLECTION);

        add(scanPathButton, "w 2.7cm");

        musicScrollBar = new JProgressBar(0, 100);
        add(musicScrollBar, "w 100");

        cancelScanPathButton = new QButton("X");
        // cancelScanPathButton.addActionListener(this);
        cancelScanPathButton.setActionCommand(ConfigurationView.EVENT_CANCEL_UPDATE_COLLECTION);

        add(cancelScanPathButton, "gapy 0 0, w 0.8cm");
    }

}
