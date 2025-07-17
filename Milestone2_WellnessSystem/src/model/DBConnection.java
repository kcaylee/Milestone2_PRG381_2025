package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * DBConnection is a utility class that manages the JavaDB (Derby) database connection
 * for the BC Student Wellness Management System.
 * 
 * It uses a singleton pattern to keep one connection open during the application lifecycle.
 * If the connection becomes invalid (closed), it will reconnect automatically.
 */
public class DBConnection {

    // Database connection details
    private static final String DB_URL = "jdbc:derby://localhost:1527/WellnessDB;create=true";
    private static final String USER = "app";
    private static final String PASSWORD = "app";

    // Singleton connection object
    private static Connection connection = null;

    /**
     * Returns a valid database connection.
     * If the connection is null or closed, a new one is established.
     *
     * @return a valid Connection object to the WellnessDB
     */
    public static Connection getConnection() {
        try {
            // If no connection has been made or the previous one is closed, reconnect
            if (connection == null || connection.isClosed()) {
                // Load the JavaDB driver
                Class.forName("org.apache.derby.jdbc.ClientDriver");

                // Establish the connection
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                System.out.println("Connected to JavaDB (WellnessDB)");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JavaDB Driver not found.");
            JOptionPane.showMessageDialog(
                null,
                "JavaDB Driver not found.\nMake sure Derby is properly configured.",
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException e) {
            System.err.println("Connection to database failed.");
            JOptionPane.showMessageDialog(
                null,
                "Could not connect to the database.\nPlease ensure JavaDB is started before running the app.",
                "Database Not Running",
                JOptionPane.ERROR_MESSAGE
            );
        }

        return connection;
    }

    /**
     * Closes the current database connection, if open.
     * Should be called when the application shuts down.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close database connection.");
            e.printStackTrace();
        }
    }
}
