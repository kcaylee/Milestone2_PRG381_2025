package view;

import javax.swing.*;
import java.awt.*;

public class FeedbackPanel extends JPanel {

    public FeedbackPanel() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Feedback Management", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(label, BorderLayout.NORTH);

        // To be implemented by teammate
    }
}
