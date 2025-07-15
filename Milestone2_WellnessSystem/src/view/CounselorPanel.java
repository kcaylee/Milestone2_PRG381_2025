package view;

import javax.swing.*;
import java.awt.*;

public class CounselorPanel extends JPanel {

    public CounselorPanel() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Counselor Management", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(label, BorderLayout.NORTH);

        // To be implemented by teammate
    }
}
