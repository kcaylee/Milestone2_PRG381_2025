package controller;

import model.DBConnection;
import model.Feedback;

import java.sql.*;
import java.util.ArrayList;

public class FeedbackController {

    private Connection connection;

    // Get all feedback
    public ArrayList<Feedback> getAllFeedback() {
        ArrayList<Feedback> list = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            String query = "SELECT * FROM Feedback";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String studentName = rs.getString("student_name");
                int rating = rs.getInt("rating");
                String comments = rs.getString("comments");

                list.add(new Feedback(id, studentName, null, rating, comments));  // counselorName is null
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Add feedback
    public boolean addFeedback(String studentName, String counselorName, int rating, String comments) {
        try {
            connection = DBConnection.getConnection();
            String query = "INSERT INTO Feedback (student_name, rating, comments) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, studentName);
            stmt.setInt(2, rating);
            stmt.setString(3, comments);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete feedback
    public boolean deleteFeedback(int id) {
        try {
            connection = DBConnection.getConnection();
            String query = "DELETE FROM Feedback WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
