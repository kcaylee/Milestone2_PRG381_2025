/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.DBConnection;
import utils.DialogHelper;
import view.FeedbackPanel;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;

/**
 *
 * @author carst
 */

//Feedback controller class for managing logic and db operations
public class FeedbackController {
    
    //Feedback GUI ref (view)
    private final FeedbackPanel panel;
    //Connection obj to connect to Derby
    private final Connection conn;
    
    //Constructor to initialize view and db connection
    public FeedbackController(FeedbackPanel panel) {
        this.panel = panel;
        this.conn = DBConnection.getConnection();
        //Add action handlers to btns and tbls
        setListeners();
        //Add data from db to table
        loadFeedback();
    }
    
    //Method for action handlers
    private void setListeners() {
        //Submit/Update/Delete/Clear button action handlers
        panel.btnSubmit.addActionListener(e -> addFeedback());
        panel.btnUpdate.addActionListener(e -> updateFeedback());
        panel.btnDelete.addActionListener(e -> deleteFeedback());
        panel.btnClear.addActionListener(e -> clearForm());

        //Table action handler:
        //Fill text fields on row selected, with row data 
        panel.feedbackTable.getSelectionModel().addListSelectionListener(e -> {
            int row = panel.feedbackTable.getSelectedRow();
            if (row >= 0) {
                panel.txtStudentName.setText(panel.tableModel.getValueAt(row, 1).toString());
                panel.cmbRating.setSelectedItem(panel.tableModel.getValueAt(row, 2));
                panel.txtComments.setText(panel.tableModel.getValueAt(row, 3).toString());
            }
        });
    }
    
    //Method to load db data into table
    private void loadFeedback() {
        try {
            //Ensure existing rows are clear
            panel.tableModel.setRowCount(0);
            //SQL Query to fetch all entries
            String query = "SELECT * FROM FEEDBACK";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            //Populate table per row
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("ID"));
                row.add(rs.getString("student_name"));
                row.add(rs.getInt("rating"));
                row.add(rs.getString("comments"));
                panel.tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            DialogHelper.showError("Failed to load feedback: " + ex.getMessage());
        }
    }
    
    //Method for adding new feedback
    private void addFeedback() {
        //Put text from fields into variables
        String name = panel.txtStudentName.getText().trim();
        int rating = (int) panel.cmbRating.getSelectedItem();
        String comments = panel.txtComments.getText().trim();

        if (name.isEmpty() || comments.isEmpty()) {
            DialogHelper.showError("All fields are required.");
            return;
        }

        try {
            //SQL Insert query 
            String query = "INSERT INTO FEEDBACK (student_name, rating, comments) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, rating);
            pst.setString(3, comments);
            pst.executeUpdate();
            DialogHelper.showInfo("Feedback submitted successfully!");
            
            //Refresh table
            clearForm();
            loadFeedback();
        } catch (SQLException ex) {
            DialogHelper.showError("Failed to submit feedback: " + ex.getMessage());
        }
    }
    
    //Method for updating selected feedback in db
    private void updateFeedback() {
        int row = panel.feedbackTable.getSelectedRow();
        //Exception to check if row is selected
        if (row < 0) {
            DialogHelper.showError("Please select a feedback entry to update.");
            return;
        }
        
        //Get selected row data
        int id = (int) panel.tableModel.getValueAt(row, 0);
        String name = panel.txtStudentName.getText().trim();
        int rating = (int) panel.cmbRating.getSelectedItem();
        String comments = panel.txtComments.getText().trim();

        if (name.isEmpty() || comments.isEmpty()) {
            DialogHelper.showError("All fields are required.");
            return;
        }

        try {
            //SQL Update query
            String query = "UPDATE FEEDBACK SET student_name=?, rating=?, comments=? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, rating);
            pst.setString(3, comments);
            pst.setInt(4, id);
            pst.executeUpdate();
            DialogHelper.showInfo("Feedback updated successfully.");
            clearForm();
            loadFeedback();
        } catch (SQLException ex) {
            DialogHelper.showError("Failed to update feedback: " + ex.getMessage());
        }
    }
    
    //Method for deleteting feedback
    private void deleteFeedback() {
        int row = panel.feedbackTable.getSelectedRow();
        //Row must be selected
        if (row < 0) {
            DialogHelper.showError("Please select a feedback entry to delete.");
            return;
        }

        int id = (int) panel.tableModel.getValueAt(row, 0);
        //Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete this entry?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                //SQL Delete query
                String query = "DELETE FROM FEEDBACK WHERE ID=?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, id);
                pst.executeUpdate();
                DialogHelper.showInfo("Feedback deleted.");
                clearForm();
                loadFeedback();
            } catch (SQLException ex) {
                DialogHelper.showError("Failed to delete feedback: " + ex.getMessage());
            }
        }
    }
    
    //Method to clear text fields
    private void clearForm() {
        panel.txtStudentName.setText("");
        panel.cmbRating.setSelectedIndex(0);
        panel.txtComments.setText("");
        panel.feedbackTable.clearSelection();
    }

}
