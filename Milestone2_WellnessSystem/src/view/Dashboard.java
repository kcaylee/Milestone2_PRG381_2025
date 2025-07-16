package view;

import javax.swing.*;
import java.awt.*;
import view.components.SharedUI;

public class Dashboard extends JFrame {

    private JTabbedPane tabbedPane;

    public Dashboard() {
        setTitle("BC Student Wellness Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // Add panels to tabs
        tabbedPane.addTab("Appointments", new AppointmentPanelTemp());
        tabbedPane.addTab("Counselors", new CounselorPanel());
        tabbedPane.addTab("Feedback", new FeedbackPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
