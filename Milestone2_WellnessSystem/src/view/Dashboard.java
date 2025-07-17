package view;

import javax.swing.*;
import java.awt.*;
import view.components.SharedUI;

/**
 * Dashboard is the main JFrame that serves as the entry point for the
 * desktop application. It uses a tabbed pane to navigate between
 * appointments, counselors, and feedback management panels.
 */
public class Dashboard extends JFrame {

    private JTabbedPane tabbedPane;

    /**
     * Constructs the Dashboard window and initializes all GUI components.
     */
    public Dashboard() {
        setTitle("BC Student Wellness Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window on the screen

        initComponents(); // Setup the tabbed interface
    }

    /**
     * Initializes and adds tabs for different management panels.
     */
    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // Each tab corresponds to a functional section of the system
        tabbedPane.addTab("Appointments", new AppointmentPanel());
        tabbedPane.addTab("Counselors", new CounselorPanel());
        tabbedPane.addTab("Feedback", new FeedbackPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
