package com.quiltplayer.view.swing.views.impl.configurations;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

@Component
public class LogPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final File logFile = new File(System.getProperty("user.home") + "/.quiltplayer/log/quiltplayer.log");

    private JTextArea logArea;

    public LogPanel() {
        super(new MigLayout("ins 0.2cm 0.2cm 0.2cm 0.2cm, fill"));

        setOpaque(false);

        logArea = new JTextArea();
        logArea.setOpaque(false);

        add(logArea);
    }

    public void updateLogText() {
        try {
            Reader reader = new FileReader(logFile);
            logArea.read(reader, null);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
