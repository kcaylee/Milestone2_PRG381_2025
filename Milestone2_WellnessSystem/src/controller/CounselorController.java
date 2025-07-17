package controller;

import model.Counselor;
import model.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class CounselorController {

    private Connection connection;

    // Get all counselors
    public ArrayList<Counselor> getAllCounselors() {
        ArrayList<Counselor> list = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            String query = "SELECT * FROM Counselors";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                String availability = rs.getString("availability");

                list.add(new Counselor(id, name, specialization, availability));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Add a new counselor
   public boolean addCounselor(String name, String specialization, String availability) {
    try {
        connection = DBConnection.getConnection();

        if (counselorExists(name)) {
            return false; // Duplicate counselor
        }

        String query = "INSERT INTO Counselors (name, specialization, availability) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setString(2, specialization);
        stmt.setString(3, availability);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    // Update an existing counselor
    public boolean updateCounselor(int id, String name, String specialization, String availability) {
        try {
            connection = DBConnection.getConnection();
            String query = "UPDATE Counselors SET name = ?, specialization = ?, availability = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, specialization);
            stmt.setString(3, availability);
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a counselor
    public boolean deleteCounselor(int id) {
        try {
            connection = DBConnection.getConnection();
            String query = "DELETE FROM Counselors WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get list of counselor names (used in dropdowns)
    public ArrayList<String> getCounselorNames() {
        ArrayList<String> names = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            String query = "SELECT name FROM Counselors";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }
        public boolean counselorExists(String name) {
        try {
            connection = DBConnection.getConnection();
            String query = "SELECT COUNT(*) FROM Counselors WHERE name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
