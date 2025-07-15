package view;

import javax.swing.*;
import java.awt.*;

public class AppointmentPanel extends JPanel {

    public AppointmentPanel() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Appointment Management", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(label, BorderLayout.NORTH);

        // To be implemented by teammate
    }
}
