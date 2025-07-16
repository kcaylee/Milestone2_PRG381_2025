import view.Dashboard;
import controller.AppointmentController;

public class Main {
    public static void main(String[] args) {
        // Start the Dashboard window
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Dashboard().setVisible(true);
        });
    }
}
