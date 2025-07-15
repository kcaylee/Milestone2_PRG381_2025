package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:derby://localhost:1527/WellnessDB;create=true";
    private static final String USER = "app";
    private static final String PASSWORD = "app";

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load Derby driver (optional in modern JDKs)
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                System.out.println("✅ Connected to JavaDB (WellnessDB)");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ JavaDB Driver not found.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("❌ Connection failed.");
                e.printStackTrace();
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
