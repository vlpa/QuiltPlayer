package com.quiltplayer.view.swing.views.impl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.Serializable;
import java.util.Enumeration;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.text.Style;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.external.wiki.WikipediaService;
import com.quiltplayer.view.swing.FontFactory;
import com.quiltplayer.view.swing.panels.QScrollPane;
import com.quiltplayer.view.swing.views.View;

@org.springframework.stereotype.Component
public class AboutView implements Serializable, View {

    private static final long serialVersionUID = 1L;

    private JPanel panel;

    /*
     * (non-Javadoc)
     * 
     * @see org.quiltplayer.view.components.View#getUI()
     */
    @Override
    public Component getUI() {

        panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new MigLayout(
                "insets 0, wrap 1, alignx center, aligny center, fillx, filly"));

        WikipediaService ws = new WikipediaService();

        System.out.println(ws.exists("The_brews"));

        String content = "";
        try {
            content = ws.getWikiContentForPageName("Duran Duran");
            System.out.println(content);
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }

        final JEditorPane htmlPane = new JEditorPane("text/html", content);

        final Font font = FontFactory.getFont(16);
        String pRule = "p { background: #000000; font-family: " + font.getName() + "; "
                + "font-size: " + font.getSize() + "pt; color: #EEEEEE; }";
        String spanRule = "span { background: #555555; font-family: " + font.getName() + "; "
                + "font-size: " + font.getSize() + "pt; color: #FFFFFF; }";
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

        htmlPane.setEditable(false);
        htmlPane.setOpaque(true);

        panel.add(new QScrollPane(htmlPane), "h 100%, w 100%");

        return panel;
    }
}
