package com.quiltplayer.view.swing.designcomponents;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.quiltplayer.view.swing.buttons.QButton;
import com.quiltplayer.view.swing.labels.QLabel;
import com.quiltplayer.view.swing.textfields.QTextField;
import com.quiltplayer.view.swing.window.Keyboard;

/**
 * 
 * @author Vlado Palczynski
 * 
 */
public class TextFieldComponents {

    public static JComponent textFieldComponentForForms(final String name, final String value,
            final boolean enableTextField, final Keyboard keyboardPanel) {
        final JPanel panel = new JPanel(new MigLayout("insets 0, wrap 1"));
        panel.setOpaque(false);

        final JLabel label = new QLabel(name);
        final JTextField textField = new QTextField(keyboardPanel);
        textField.setText(value);
        textField.setEnabled(enableTextField);

        panel.add(label, "left, w 100%");
        panel.add(textField, "left, w 100%, " + QTextField.MIG_HEIGHT);

        return panel;
    }

    public static JComponent textFieldComponentForForms(final String name, final JTextField textField,
            final String value, final boolean enableTextField) {
        final JPanel panel = new JPanel(new MigLayout("insets 0, wrap 1"));
        panel.setOpaque(false);

        final JLabel label = new QLabel(name);
        textField.setText(value);
        textField.setEnabled(enableTextField);

        panel.add(label, "left, w 100%");
        panel.add(textField, "left, w 100%, " + QTextField.MIG_HEIGHT);

        return panel;
    }

    public static JComponent textFieldComponentForFormsWithButton(final String name, final JTextField textField,
            final String value, final boolean enableTextField, final JButton button) {
        final JPanel panel = new JPanel(new MigLayout("insets 0, wrap 2, fill"));
        panel.setOpaque(false);

        final JLabel label = new QLabel(name);
        textField.setEnabled(enableTextField);

        panel.add(label, "left, wrap");
        panel.add(textField, "left, growx, " + QTextField.MIG_HEIGHT);
        panel.add(button, "left, w 1cm, " + QButton.MIG_HEIGHT);

        return panel;
    }

    public static JComponent textFieldComponentForFormsWithButton(final String name, final String value,
            final boolean enableTextField, final JButton button, final Keyboard keyboardPanel) {
        final JPanel panel = new JPanel(new MigLayout("insets 0, wrap 2"));
        panel.setOpaque(false);

        final JLabel label = new QLabel(name);
        final JTextField textField = new QTextField(keyboardPanel);
        textField.setText(value);
        textField.setEnabled(enableTextField);

        panel.add(label, "left, wrap");
        panel.add(textField, "left, grow, " + QTextField.MIG_HEIGHT);
        panel.add(button, "left, w 1cm, " + QButton.MIG_HEIGHT);

        return panel;
    }
}
