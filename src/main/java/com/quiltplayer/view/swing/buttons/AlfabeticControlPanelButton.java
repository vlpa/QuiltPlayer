package com.quiltplayer.view.swing.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.listeners.SelectionListener;

public class AlfabeticControlPanelButton extends ScrollableButton {

    private static final long serialVersionUID = 1L;

    private SelectionListener selectionListener;

    private String label;

    public AlfabeticControlPanelButton(final String label, final SelectionListener selectionListener) {
        this.label = label;
        this.selectionListener = selectionListener;

        setLayout(new MigLayout("insets 0, fill"));

        setOpaque(false);

        final JLabel l = new JLabel();
        l.setText(label);
        l.setForeground(Color.WHITE);
        l.setOpaque(false);
        l.setFont(FontFactory.getFont(14f));

        add(l, "align center");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.buttons.ScrollableButton#triggerAction()
     */
    @Override
    protected void triggerAction() {
        selectionListener.actionPerformed(new ActionEvent("", 0, label));
    }
}
