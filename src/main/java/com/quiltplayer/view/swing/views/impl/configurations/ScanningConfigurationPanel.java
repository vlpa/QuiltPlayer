package com.quiltplayer.view.swing.views.impl.configurations;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.controller.ConfigurationController;
import com.quiltplayer.controller.ScanningController;
import com.quiltplayer.core.scanner.tasks.FileScannerTask;
import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.core.storage.Storage;
import com.quiltplayer.internal.id3.Id3Extractor;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.designcomponents.TextFieldComponents;
import com.quiltplayer.view.swing.listeners.ConfigurationListener;
import com.quiltplayer.view.swing.listeners.ScanningListener;
import com.quiltplayer.view.swing.progressbars.QProgressBar;
import com.quiltplayer.view.swing.textfields.QTextField;
import com.quiltplayer.view.swing.views.impl.ConfigurationView;
import com.quiltplayer.view.swing.window.Keyboard;

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
    private Keyboard keyboardPanel;

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
    private JTextField musicPath = new QTextField(keyboardPanel);

    /**
     * The scan button.
     */
    private JButton scanCoversButton, cancelScanCoversButton;

    /**
     * The update button.
     */
    private JButton scanPathButton, cancelScanPathButton;

    @Autowired
    private ScanningListener scanningListener;

    @Autowired
    private ConfigurationListener configurationListener;

    private QProgressBar musicScrollBar;

    private JFileChooser fc;

    private FileScannerTask task;

    private JComponent musicPathComponent;

    public ScanningConfigurationPanel() {
        super(new MigLayout("ins 0, wrap 2, fill"));
    }

    @PostConstruct
    public void init() {
        fileChooserButton = new QButton("Select");
        fileChooserButton.addActionListener(this);

        musicPath.setText(Configuration.getInstance().getFolderProperties().getMusicPath());

        setupScanMusicButton();

        musicPathComponent = TextFieldComponents.textFieldComponentForFormsWithButton(
                "Music directory to scan", musicPath, Configuration.getInstance()
                        .getFolderProperties().getCovers().getAbsolutePath(), false,
                fileChooserButton);

        addMusicComponent(musicPathComponent);
        add(scanPathButton, "cell 1 1, w 2.7cm, right, aligny bottom, gapy 0.5cm");

        // add(musicScrollBar, "w 80%, h 1.0cm");
        // add(cancelScanPathButton, "gapy 0 0, w 0.8cm, newline");

        setupScanCoversButton();

        add(scanCoversButton, "cell 0 2, w 2.7cm, gapy 0.3cm, span 2, right");
        // add(cancelScanCoversButton, "span 2, gapy 0.1cm 0, w 0.8cm, newline");
    }

    private void addMusicComponent(JComponent component) {
        add(component, "cell 0 0, left, aligny bottom, grow, span 2");
    }

    private void setupScanCoversButton() {
        scanCoversButton = new QButton("Search covers");
        scanCoversButton.addActionListener(scanningListener);
        scanCoversButton.setActionCommand(ScanningController.EVENT_SCAN_COVERS);

        cancelScanCoversButton = new QButton("X");
        cancelScanCoversButton.setOpaque(false);
        cancelScanCoversButton.addActionListener(scanningListener);
        cancelScanCoversButton.setActionCommand(ConfigurationView.EVENT_CANCEL_SCAN_COVERS);
    }

    private void setupScanMusicButton() {

        scanPathButton = new QButton("Scan path");
        scanPathButton.addActionListener(this);
        scanPathButton.setActionCommand(ScanningController.EVENT_UPDATE_COLLECTION);

        musicScrollBar = new QProgressBar(0, 100);

        cancelScanPathButton = new QButton("X");
        cancelScanPathButton.addActionListener(this);
        cancelScanPathButton.setActionCommand(ScanningController.EVENT_CANCEL_UPDATE_COLLECTION);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == ScanningController.EVENT_UPDATE_COLLECTION) {
            Configuration.getInstance().getFolderProperties().setMusicPath(musicPath.getText());

            configurationListener.actionPerformed(new ActionEvent("", 0,
                    ConfigurationController.EVENT_UPDATE_CONFIGURATION));

            remove(musicPathComponent);
            addMusicComponent(musicScrollBar);

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
            remove(musicScrollBar);
            addMusicComponent(musicPathComponent);

            repaint();
        }
        else {
            int progress = task.getProgress();
            musicScrollBar.setValue(progress);
            musicScrollBar.repaint();
            musicScrollBar.updateUI();
        }
    }
}
