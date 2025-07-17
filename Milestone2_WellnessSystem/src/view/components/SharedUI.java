package view.components;

import javax.swing.*;
import java.awt.*;

/**
 * SharedUI is a utility class that provides reusable UI components and styling
 * for the BC Student Wellness Management System.
 */
public class SharedUI {

    /**
     * Creates a reusable title label with consistent styling.
     *
     * @param text the label text
     * @return styled JLabel centered and bold
     */
    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        return label;
    }

    /**
     * Creates a reusable JButton with focus painting disabled.
     *
     * @param text the button text
     * @return styled JButton
     */
    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Wraps a given component in a JPanel with empty padding on all sides.
     *
     * @param component the component to wrap
     * @param padding   the padding (in pixels)
     * @return padded JPanel containing the component
     */
    public static JPanel wrapWithPadding(JComponent component, int padding) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Provides a consistent font for use in table headers.
     *
     * @return the styled Font object
     */
    public static Font getTableHeaderFont() {
        return new Font("SansSerif", Font.BOLD, 12);
    }
}
