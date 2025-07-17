import view.Dashboard;

/**
 * Entry point for the Wellness Management System.
 * This class initializes and displays the main Dashboard GUI using Java Swing.
 */
public class Main {
    public static void main(String[] args) {
        // Launch the Dashboard form on the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Dashboard().setVisible(true);
        });
    }
}
