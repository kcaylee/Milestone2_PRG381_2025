package view.components;

import javax.swing.*;
import java.awt.*;

public class SharedUI {

    // Reusable title label style
    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        return label;
    }

    // Reusable default button
    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        return button;
    }

    // Reusable padded panel
    public static JPanel wrapWithPadding(JComponent component, int padding) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    // Optional: Reusable font for tables
    public static Font getTableHeaderFont() {
        return new Font("SansSerif", Font.BOLD, 12);
    }
}
