package controller;

import model.DBConnection;
import model.Feedback;

import java.sql.*;
import java.util.ArrayList;

/**
 * Controller class for managing feedback data.
 * Handles all database operations related to student feedback.
 * Implements CRUD logic as part of the Feedback Management section in Milestone 2.
 */
public class FeedbackController {

    private Connection connection;

    /**
     * Retrieves all feedback records from the database.
     * Used to populate feedback history in the GUI.
     */
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

                // counselorName is not stored in DB for feedback, so pass null
                list.add(new Feedback(id, studentName, null, rating, comments));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Adds a new feedback entry to the database.
     * Only student name, rating, and comments are stored (counselorName is unused here).
     */
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

    /**
     * Deletes a feedback record based on its ID.
     * Triggers a confirmation dialog in the GUI before execution.
     */
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
