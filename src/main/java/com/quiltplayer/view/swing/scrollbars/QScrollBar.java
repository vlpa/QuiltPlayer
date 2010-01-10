package com.quiltplayer.view.swing.scrollbars;

import javax.swing.JScrollBar;

public class QScrollBar extends JScrollBar {
    private static final long serialVersionUID = 1L;

    private QScrollBarUIDark ui = new QScrollBarUIDark();

    public QScrollBar(int orientation) {
        setUI(ui);
        setOrientation(orientation);
    }

    /*
     * @see javax.swing.JScrollBar#updateUI()
     */
    @Override
    public void updateUI() {
        // TODO Auto-generated method stub
        super.updateUI();

        setUI(ui);
    }

}
