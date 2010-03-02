package com.quiltplayer.view.swing.panels.controlpanels;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.jxlayer.JXLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quiltplayer.view.swing.buttons.AlfabeticControlPanelButton;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.listeners.SelectionListener;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.views.View;

@Component
public class AlfabeticControlPane implements View {
    private static final long serialVersionUID = 1L;

    public static final String ALL = "All";

    public static final String NUMERIC = "0-9";

    private String[] strings = new String[] { ALL, NUMERIC, "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
            "Z", };

    private JXLayer<JScrollPane> layer;

    @Autowired
    private SelectionListener selectionListener;

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.views.View#getUI()
     */
    @Override
    public JComponent getUI() {
        if (layer == null) {
            final JPanel panel = new JPanel(new MigLayout("insets 0, wrap 1, fill"));

            panel.setOpaque(true);

            for (String s : strings) {
                panel.add(new AlfabeticControlPanelButton(s, selectionListener),
                        "w 1.1cm!, h 1.1cm!, alignx center");
            }

            final QScrollPane pane = new QScrollPane(panel);

            layer = new JXLayer<JScrollPane>(pane, new JScrollPaneLayerUI());
        }

        return layer;
    }
}
