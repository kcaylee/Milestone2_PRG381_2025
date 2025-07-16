/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.*;
import java.util.ArrayList;
import model.Appointment;
import model.DBConnection;

/**
 *
 * @author carst
 */
public class AppointmentController {
    
    private Connection _connection;
    private int ID;
    private String studentName;
    private String counselorName;
    private Date appointmentDate;
    private Time appointmentTime;
    private String status;
    
    private ArrayList<Appointment> appointments = new ArrayList<>();
    
    //Function to find a ResultSet in the Database, and returning an ArrayList<>
    public ArrayList<Appointment> findInDB(ResultSet rs){
        try {
            while (rs.next()) {
                ID = rs.getInt("id");
                studentName = rs.getString("student_name");
                counselorName = rs.getString("counselor_name");
                appointmentDate = rs.getDate("appointment_date");
                appointmentTime = rs.getTime("appointment_time");
                status = rs.getString("status");

                //Adding entries to list of appointments
                appointments.add(new Appointment(ID,studentName,counselorName,appointmentDate,appointmentTime,status));
            }
            //Returning list of appointments
            return appointments;
        } catch (SQLException e){
            return null;
        }
    }

    //Function to get all of the entries in the Appointments table
    public ArrayList<Appointment> getAll(){
        
        //Connecting to Database
        _connection = DBConnection.getConnection();
        appointments.clear();
        
        try {
            //If null then connection failed
            if (_connection == null) {
                System.err.println("Database connection failed");
                return null;
            }
            
            //Creating SQL query to select all entries in Appointments table
            Statement stmt = _connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Appointments");
            
            //Turning ResultSet into List of Appointments
            appointments = findInDB(rs);

            return appointments;

        } catch (SQLException e) {
            System.err.println("SQL Error in getAll(): " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error in getAll(): " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //Function to find an entry based on ID
    public ArrayList<Appointment> getByID(int ID){
        //Clear appointments list to only display new results
        appointments.clear();
        
        try {
            //Connecting to DB
         _connection = DBConnection.getConnection();
         
         if (_connection == null) {
                System.err.println("Database connection failed");
                return null;
            }
         
        //SQL Query for selecting all entries in Appointments table where the ID matches
        String query = "SELECT * FROM Appointments WHERE ID = ?";
        
        //Using PreparedStatements to insert variables later
        PreparedStatement stmt = _connection.prepareStatement(query);
        stmt.setInt(1, ID);
        ResultSet rs = stmt.executeQuery();
        
        appointments = findInDB(rs);
        
        return appointments;
         
        } catch (SQLException e) {
            System.err.println("SQL Error in getAll(): " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error in getAll(): " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //Function to add a new appointment to the table
    public boolean addAppointment(String studentName, String counselorName, Date appointmentDate, Time appointmentTime, String status) {
        try {
            //Connecting to DB
            _connection = DBConnection.getConnection();
            
            if (_connection == null) {
                System.err.println("Database connection failed");
                return false;
            }
            
            //SQL Query for inserting a new entry into the table using the variables that are passed through
            String query = "INSERT INTO Appointments (student_name, counselor_name, appointment_date, appointment_time, status) VALUES (?, ?, ?, ?, ?)";
            
            //Using PreparedStatement to add variables to query
            PreparedStatement stmt = _connection.prepareStatement(query);
            stmt.setString(1, studentName);
            stmt.setString(2, counselorName);
            stmt.setDate(3, appointmentDate);
            stmt.setTime(4, appointmentTime);
            stmt.setString(5, status);
            
            //Checking if any rows were affected in this update
            int rowsAffected = stmt.executeUpdate();
            
            
            if (rowsAffected > 0) {
                //If rows were affected, it means that the appointment was successfully added
                System.out.println("Successfully added appointment for " + studentName);
                return true;
            } else {
                //If no rows were affected, it means the entry was not added
                System.out.println("No rows were affected during appointment insertion");
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("SQL Error in addAppointment(): " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error in addAppointment(): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //Function to delete an entry in Appointments based on ID
    public void deleteByID(int ID){
        
        try {
            //Connecting to DB
         _connection = DBConnection.getConnection();
         
         if (_connection == null) {
                System.err.println("Database connection failed, cannot retrieve appointments");
            }
         
        //SQL Query to delete a entry from appointments based on ID
        String query = "DELETE FROM Appointments WHERE ID = ?";
        
        PreparedStatement stmt = _connection.prepareStatement(query);
        stmt.setInt(1, ID);
        
        //Checking if rows were affected
        int rowsAffected = stmt.executeUpdate();
            
        if (rowsAffected > 0) {
            //If so, then the entry was successfully deleted
            System.out.println("Successfully deleted appointment");
        } else {
            //If not, then an error has occured
            System.out.println("No rows were affected during appointment deletion");
        }
         
        } catch (SQLException e) {
            System.err.println("SQL Error in deleteByID(): " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error in deleteByID(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Function to update an already existing entry
    public boolean updateAppointment(String studentName, String counselorName, Date appointmentDate, Time appointmentTime, String status, int ID){
        try {
            //Connecting to DB
            _connection = DBConnection.getConnection();
            
            if (_connection == null) {
                System.err.println("Database connection failed, cannot add appointment");
                return false;
            }
            
            //SQL query to update attributes of a certain entry in Appointments based on ID
            String query = "UPDATE Appointments SET student_name = ?, counselor_name = ?, appointment_date = ?, appointment_time = ?, status = ? WHERE ID = ?";
            
            PreparedStatement stmt = _connection.prepareStatement(query);
            stmt.setString(1, studentName);
            stmt.setString(2, counselorName);
            stmt.setDate(3, appointmentDate);
            stmt.setTime(4, appointmentTime);
            stmt.setString(5, status);
            stmt.setInt(6, ID);
            
            //Checking if rows were affected
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                //If so, the entry was successfully updated
                System.out.println("Successfully edited appointment for " + studentName);
                return true;
            } else {
                //If not, an error occured
                System.out.println("No rows were affected during appointment edit");
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("SQL Error in addAppointment(): " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error in addAppointment(): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
}
