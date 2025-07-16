package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:derby://localhost:1527/WellnessDB;create=true";
    private static final String USER = "app";
    private static final String PASSWORD = "app";

    static {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            System.out.println("✅ JavaDB Driver loaded");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ JavaDB Driver not found");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        // Always return a new connection
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        System.out.println("✅ Connected to JavaDB (WellnessDB)");
        return conn;
    }
}
