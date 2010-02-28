package com.quiltplayer.view.swing.views.impl;

import java.awt.Component;
import java.awt.Font;
import java.io.Serializable;
import java.util.Enumeration;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.Style;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.jxlayer.JXLayer;
import org.springframework.beans.factory.annotation.Autowired;

import com.quiltplayer.external.wiki.WikipediaService;
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

    @Autowired
    private WikipediaService wikipediaService;

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.view.components.View#getUI()
     */
    @Override
    public Component getUI() {

        panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(ColorConstantsDark.BACKGROUND);
        panel.setLayout(new MigLayout(
                "insets 0, wrap 1, alignx center, aligny center, fillx, filly"));

        System.out.println("Content: " + content);

        final JEditorPane htmlPane = new JEditorPane("text/html", content);
        htmlPane.setOpaque(true);
        htmlPane.setBackground(ColorConstantsDark.BACKGROUND);
        htmlPane.setAutoscrolls(true);
        htmlPane.setDragEnabled(true);
        htmlPane.setDoubleBuffered(true);
        htmlPane.setEditable(true);
        htmlPane.setFocusable(false);

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

        panel.add(htmlPane, "w 100%, h 100%");

        final QScrollPane pane = new QScrollPane(panel);

        return new JXLayer<JScrollPane>(pane, new JScrollPaneLayerUI());
    }

    public void changeWikiContent(final String name) {
        System.out.println(wikipediaService.exists(name));

        try {
            content = wikipediaService.getWikiContentForPageName(name);
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
