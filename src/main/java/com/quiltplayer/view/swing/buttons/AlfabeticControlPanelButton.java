package com.quiltplayer.view.swing.buttons;

import java.awt.Color;

import javax.swing.JLabel;

import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.listeners.SelectionListener;

public class AlfabeticControlPanelButton extends ScrollableButton {

    private static final long serialVersionUID = 1L;

    public AlfabeticControlPanelButton(final String label, final SelectionListener selectionListener) {
        setBackground(Color.white);

        final JLabel l = new JLabel();
        l.setText(label);
        l.setForeground(Color.DARK_GRAY);
        l.setOpaque(false);
        l.setFont(FontFactory.getFont(14f));

        add(l);

        addActionListener(selectionListener);
        setActionCommand(label);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.view.swing.buttons.ScrollableButton#triggerAction()
     */
    @Override
    protected void triggerAction() {
        // TODO Auto-generated method stub

    }
}
