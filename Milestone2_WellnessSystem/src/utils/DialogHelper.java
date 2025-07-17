package utils;

import javax.swing.*;

/**
 * Utility class for displaying standardized dialogs in the application.
 * Helps with showing error, information, and confirmation messages consistently.
 */
public class DialogHelper {

    /**
     * Displays an error dialog with the given message.
     * @param message The error message to display
     */
    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an informational dialog with the given message.
     * @param message The information message to display
     */
    public static void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays a confirmation dialog and returns true if the user clicks 'Yes'.
     * @param message The confirmation prompt
     * @return true if user confirms (Yes), false otherwise (No)
     */
    public static boolean showConfirm(String message) {
        int result = JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}
