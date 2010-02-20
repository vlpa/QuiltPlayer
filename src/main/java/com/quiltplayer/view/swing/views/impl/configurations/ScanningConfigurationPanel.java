package com.quiltplayer.view.swing.views.impl.configurations;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.ScanningController;
import com.quiltplayer.core.scanner.tasks.FileScannerTask;
import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.core.storage.Storage;
import com.quiltplayer.internal.id3.Id3Extractor;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.designcomponents.TextFieldComponents;
import com.quiltplayer.view.swing.listeners.ScanningListener;
import com.quiltplayer.view.swing.textfields.QTextField;
import com.quiltplayer.view.swing.views.impl.ConfigurationView;
import com.quiltplayer.view.swing.window.KeyboardPanel;

/**
 * Panel for the scanning tab in configurations view.
 * 
 * @author Vlado Palczynski
 * 
 */
@Component
public class ScanningConfigurationPanel extends JPanel implements ActionListener,
        PropertyChangeListener {

    private static final long serialVersionUID = 1L;

    @Autowired
    private KeyboardPanel keyboardPanel;

    @Autowired
    private Id3Extractor id3Extractor;

    @Autowired
    private Storage storage;

    @Autowired
    private ArtistStorage artistStorage;

    private JButton fileChooserButton;

    /**
     * The path to music.
     */
    public JTextField musicPath = new QTextField(keyboardPanel);

    /**
     * The scan button.
     */
    public JButton scanCoversButton, cancelScanCoversButton;

    /**
     * The update button.
     */
    private JButton scanPathButton, cancelScanPathButton;

    @Autowired
    private ScanningListener scanningListener;

    private JProgressBar musicScrollBar;

    private JFileChooser fc;

    private FileScannerTask task;

    public ScanningConfigurationPanel() {

        super(new MigLayout("ins 0, wrap 2, fill"));

        fileChooserButton = new QButton("Select");
        fileChooserButton.addActionListener(this);

        add(TextFieldComponents.textFieldComponentForFormsWithButton("Music directory to scan",
                musicPath, Configuration.ALBUM_COVERS_PATH, false, fileChooserButton),
                "left, w 40%, newline");

        addScanMusicButton();

        addScanCoversButton();
    }

    private void addScanCoversButton() {
        scanCoversButton = new QButton("Search covers");
        scanCoversButton.addActionListener(scanningListener);
        scanCoversButton.setActionCommand(ConfigurationView.EVENT_SCAN_COVERS);

        add(scanCoversButton, "w 2.7cm, newline");

        cancelScanCoversButton = new QButton("X");
        cancelScanCoversButton.setOpaque(false);
        cancelScanCoversButton.addActionListener(scanningListener);
        cancelScanCoversButton.setActionCommand(ConfigurationView.EVENT_CANCEL_SCAN_COVERS);

        add(cancelScanCoversButton, "gapy 0.1cm 0, w 0.8cm, newline");
    }

    private void addScanMusicButton() {
        scanPathButton = new QButton("Scan path");
        scanPathButton.addActionListener(this);
        scanPathButton.setActionCommand(ScanningController.EVENT_UPDATE_COLLECTION);

        add(scanPathButton, "w 2.7cm");

        musicScrollBar = new JProgressBar(0, 100);
        add(musicScrollBar, "w 100");

        cancelScanPathButton = new QButton("X");
        cancelScanPathButton.addActionListener(this);
        cancelScanPathButton.setActionCommand(ScanningController.EVENT_CANCEL_UPDATE_COLLECTION);

        add(cancelScanPathButton, "gapy 0 0, w 0.8cm, newline");
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == ScanningController.EVENT_UPDATE_COLLECTION) {
            scanPathButton.setEnabled(false);
            cancelScanPathButton.setEnabled(true);
            scanPathButton.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            task = new FileScannerTask(id3Extractor, storage, artistStorage);
            task.addPropertyChangeListener(this);
            task.execute();
        }
        else if (e.getActionCommand() == ScanningController.EVENT_CANCEL_UPDATE_COLLECTION) {
            task.cancel(true);
            scanPathButton.setEnabled(true);
            cancelScanPathButton.setEnabled(false);
        }
        else if (fc == null) {
            fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setOpaque(false);
        }

        JFrame frame = new JFrame();
        frame.setBackground(Color.black);

        int returnVal = fc.showOpenDialog(frame);

        if (returnVal == JFileChooser.APPROVE_OPTION)
            musicPath.setText(fc.getSelectedFile().getAbsolutePath());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (task.isDone()) {
            scanPathButton.setEnabled(true);
            cancelScanPathButton.setEnabled(false);
        }
        else {
            int progress = task.getProgress();
            musicScrollBar.setValue(progress);
            musicScrollBar.repaint();
            musicScrollBar.updateUI();
        }
    }
}
