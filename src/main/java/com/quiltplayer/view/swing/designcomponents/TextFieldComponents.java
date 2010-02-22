package com.quiltplayer.view.swing.designcomponents;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.view.swing.labels.QLabel;
import com.quiltplayer.view.swing.textfields.QTextField;
import com.quiltplayer.view.swing.window.KeyboardPanel;

/**
 * 
 * @author Vlado Palczynski
 * 
 */
public class TextFieldComponents {

    public static JComponent textFieldComponentForForms(final String name, final String value,
            final boolean enableTextField, final KeyboardPanel keyboardPanel) {
        final JPanel panel = new JPanel(new MigLayout("insets 0, wrap 1"));

        final JLabel label = new QLabel(name);
        final JTextField textField = new QTextField(keyboardPanel);
        textField.setText(value);
        textField.setEnabled(enableTextField);

        panel.add(label, "left, w 100%");
        panel.add(textField, "left, w 100%");

        return panel;
    }

    public static JComponent textFieldComponentForForms(final String name,
            final JTextField textField, final String value, final boolean enableTextField) {
        final JPanel panel = new JPanel(new MigLayout("insets 0, wrap 1"));

        final JLabel label = new QLabel(name);
        textField.setText(value);
        textField.setEnabled(enableTextField);

        panel.add(label, "left, w 100%");
        panel.add(textField, "left, w 100%");

        return panel;
    }

    public static JComponent textFieldComponentForFormsWithButton(final String name,
            final JTextField textField, final String value, final boolean enableTextField,
            final JButton button) {

        final JPanel panel = new JPanel(new MigLayout("insets 0, wrap 2, fill"));

        final JLabel label = new QLabel(name);
        textField.setEnabled(enableTextField);

        panel.add(label, "left, wrap");
        panel.add(textField, "left, grow");
        panel.add(button, "left, w 1cm");

        return panel;
    }

    public static JComponent textFieldComponentForFormsWithButton(final String name,
            final String value, final boolean enableTextField, final JButton button,
            final KeyboardPanel keyboardPanel) {

        final JPanel panel = new JPanel(new MigLayout("insets 0, wrap 2"));

        final JLabel label = new QLabel(name);
        final JTextField textField = new QTextField(keyboardPanel);
        textField.setText(value);
        textField.setEnabled(enableTextField);

        panel.add(label, "left, wrap");
        panel.add(textField, "left, grow");
        panel.add(button, "left, w 1cm");

        return panel;
    }
}
