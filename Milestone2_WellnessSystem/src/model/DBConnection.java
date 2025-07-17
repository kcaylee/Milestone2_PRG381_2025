package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class DBConnection {

    private static final String DB_URL = "jdbc:derby://localhost:1527/WellnessDB;create=true";
    private static final String USER = "app";
    private static final String PASSWORD = "app";

    private static Connection connection = null;

  public static Connection getConnection() {
    if (connection == null) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("✅ Connected to JavaDB (WellnessDB)");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ JavaDB Driver not found.");
            JOptionPane.showMessageDialog(null,
                    "JavaDB Driver not found.\nMake sure Derby is properly configured.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            System.err.println("❌ Connection failed.");
            JOptionPane.showMessageDialog(null,
                    "Could not connect to the database.\nPlease ensure JavaDB is started before running the app.",
                    "Database Not Running", JOptionPane.ERROR_MESSAGE);
        }
    }
    return connection;
}


    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("🔒 Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Failed to close connection.");
            e.printStackTrace();
        }
    }
}
