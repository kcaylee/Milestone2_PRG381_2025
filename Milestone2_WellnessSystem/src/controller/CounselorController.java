package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 601465
 */

import model.DBConnection;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CounselorController {                                                             
   public static void loadCounselors(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT * FROM Counselors";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("availability")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading counselors.");
        }
    }

    public static void insertCounselor(String name, String specialization, String availability) {
        String sql = "INSERT INTO Counselors (name, specialization, availability) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, specialization);
            ps.setString(3, availability);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Counselor added successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding counselor.");
        }
    }

    public static void deleteCounselor(int id) {
        String sql = "DELETE FROM Counselors WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Counselor deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Counselor ID not found.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting counselor.");
        }
    }

    public static void updateCounselor(int id, String name, String specialization, String availability) {
        String sql = "UPDATE Counselors SET name = ?, specialization = ?, availability = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, specialization);
            ps.setString(3, availability);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Counselor updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Counselor ID not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating counselor.");
        }
    }  
}
