package com.quiltplayer.view.swing.views.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;
import java.util.Enumeration;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.Style;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.jxlayer.JXLayer;

import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.layers.JScrollPaneLayerUI;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.views.View;

@org.springframework.stereotype.Component
public class WikiView implements Serializable, View {

    private static final long serialVersionUID = 1L;

    private JPanel panel;

    private String content = "";

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.view.components.View#getUI()
     */
    @Override
    public JComponent getUI() {

        panel = new JPanel(new MigLayout("ins 1cm 2cm 0 2cm, w 100%!")) {

            private static final long serialVersionUID = 1L;

            /*
             * (non-Javadoc)
             * 
             * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
             */
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                super.paintComponent(g);
            }

        };
        panel.setOpaque(true);
        panel.setBackground(ColorConstantsDark.BACKGROUND);

        JEditorPane htmlPane = null;
        htmlPane = new JEditorPane("text/html", content);

        htmlPane.setOpaque(true);
        htmlPane.setBackground(ColorConstantsDark.BACKGROUND);
        htmlPane.setAutoscrolls(false);
        htmlPane.setDragEnabled(false);
        htmlPane.setEditable(true);
        htmlPane.setFocusable(false);
        htmlPane.setFont(FontFactory.getFont(13));
        htmlPane.setForeground(Color.white);
        htmlPane.setBounds(10, 10, panel.getWidth() - 150, panel.getHeight());

        final Font font = FontFactory.getFont(13);

        String pRule = "p { font-family: " + font.getName() + "; " + "font-size: " + font.getSize()
                + "pt; color: #EEEEEE; }";
        String spanRule = "span { font-family: " + font.getName() + "; " + "font-size: "
                + font.getSize() + "pt; color: #FFFFFF; }";
        String linkRule = "a { font-family: " + font.getName() + "; " + "font-size: "
                + font.getSize() + "pt;" + "color: #AAAAAA;}";
        String linkRule2 = "a:hoover { text-decoration: none; font-family: " + font.getFamily()
                + "; " + "font-size: " + font.getSize() + "pt;" + "color: #FFFFFF;}";

        ((HTMLDocument) htmlPane.getDocument()).getStyleSheet().addRule(linkRule);
        ((HTMLDocument) htmlPane.getDocument()).getStyleSheet().addRule(pRule);
        ((HTMLDocument) htmlPane.getDocument()).getStyleSheet().addRule(spanRule);
        ((HTMLDocument) htmlPane.getDocument()).getStyleSheet().addRule(linkRule2);

        StyleSheet styles = ((HTMLDocument) htmlPane.getDocument()).getStyleSheet();

        Enumeration<?> rules = styles.getStyleNames();
        while (rules.hasMoreElements()) {
            String name = (String) rules.nextElement();
            Style rule = styles.getStyle(name);
            System.out.println(rule.toString());
        }

        htmlPane.setCaretPosition(0);

        panel.add(htmlPane, "w 100%, h 100%");

        final QScrollPane pane = new QScrollPane(panel);

        return new JXLayer<JScrollPane>(pane, new JScrollPaneLayerUI());
    }

    public void setContent(final String content) {
        this.content = content;
    }
}
