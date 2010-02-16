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

    private float[] dist = { 0.0f, 1.0f };

    private Color[] activeGradient = { new Color(240, 240, 240), new Color(200, 200, 200) };

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

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    // @Override
    // public void paintComponent(Graphics g) {
    // Graphics2D g2d = (Graphics2D) g;
    //
    // super.paintComponent(g);
    //
    // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    //
    // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    // Point2D start = new Point2D.Float(0, 0);
    // Point2D end = new Point2D.Float(0, getHeight() - 1);
    //
    // LinearGradientPaint p = new LinearGradientPaint(start, end, dist, activeGradient);
    //
    // g2d.setPaint(p);
    // g2d.fillRoundRect(1, 1, getWidth() - 1, getHeight(), 15, 15);
    // }
}
