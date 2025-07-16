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
    
    public ArrayList<Appointment> findInDB(ResultSet rs){
        try {
            while (rs.next()) {
                ID = rs.getInt("id");
                studentName = rs.getString("student_name");
                counselorName = rs.getString("counselor_name");
                appointmentDate = rs.getDate("appointment_date");
                appointmentTime = rs.getTime("appointment_time");
                status = rs.getString("status");

                appointments.add(new Appointment(ID,studentName,counselorName,appointmentDate,appointmentTime,status));
            }
            
            return appointments;
        } catch (SQLException e){
            return null;
        }
    }

    public ArrayList<Appointment> getAll(){
        
        _connection = DBConnection.getConnection();
        appointments.clear();
        
        try {
            
            if (_connection == null) {
                System.err.println("❌ Database connection failed - cannot retrieve appointments");
                return null;
            }
            
            Statement stmt = _connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Appointments");
            
            appointments = findInDB(rs);

            return appointments;

        } catch (SQLException e) {
            System.err.println("❌ SQL Error in getAll(): " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("❌ Unexpected error in getAll(): " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Appointment> getByID(int ID){
        appointments.clear();
        
        try {
         _connection = DBConnection.getConnection();
         
         if (_connection == null) {
                System.err.println("❌ Database connection failed - cannot retrieve appointments");
                return null;
            }
         
        String query = "SELECT * FROM Appointments WHERE ID = ?";
        
        PreparedStatement stmt = _connection.prepareStatement(query);
        stmt.setInt(1, ID);
        ResultSet rs = stmt.executeQuery();
        
        appointments = findInDB(rs);
        
        return appointments;
         
        } catch (SQLException e) {
            System.err.println("❌ SQL Error in getAll(): " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("❌ Unexpected error in getAll(): " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean addAppointment(String studentName, String counselorName, Date appointmentDate, Time appointmentTime, String status) {
        try {
            _connection = DBConnection.getConnection();
            
            if (_connection == null) {
                System.err.println("Database connection failed, cannot add appointment");
                return false;
            }
            
            String query = "INSERT INTO Appointments (student_name, counselor_name, appointment_date, appointment_time, status) VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement stmt = _connection.prepareStatement(query);
            stmt.setString(1, studentName);
            stmt.setString(2, counselorName);
            stmt.setDate(3, appointmentDate);
            stmt.setTime(4, appointmentTime);
            stmt.setString(5, status);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Successfully added appointment for " + studentName);
                return true;
            } else {
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

    public void deleteByID(int ID){
        
        try {
         _connection = DBConnection.getConnection();
         
         if (_connection == null) {
                System.err.println("Database connection failed, cannot retrieve appointments");
            }
         
        String query = "DELETE FROM Appointments WHERE ID = ?";
        
        PreparedStatement stmt = _connection.prepareStatement(query);
        stmt.setInt(1, ID);
        stmt.executeUpdate();
         
        } catch (SQLException e) {
            System.err.println("SQL Error in deleteByID(): " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error in deleteByID(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean updateAppointment(String studentName, String counselorName, Date appointmentDate, Time appointmentTime, String status, int ID){
        try {
            _connection = DBConnection.getConnection();
            
            if (_connection == null) {
                System.err.println("Database connection failed, cannot add appointment");
                return false;
            }
            
            String query = "UPDATE Appointments SET student_name = ?, counselor_name = ?, appointment_date = ?, appointment_time = ?, status = ? WHERE ID = ?";
            
            PreparedStatement stmt = _connection.prepareStatement(query);
            stmt.setString(1, studentName);
            stmt.setString(2, counselorName);
            stmt.setDate(3, appointmentDate);
            stmt.setTime(4, appointmentTime);
            stmt.setString(5, status);
            stmt.setInt(6, ID);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                return true;
            } else {
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
