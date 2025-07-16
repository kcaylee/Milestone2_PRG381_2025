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

    public void addAppointment(){

    }

    public void deleteByID(int ID){
        
        try {
         _connection = DBConnection.getConnection();
         
         if (_connection == null) {
                System.err.println("❌ Database connection failed - cannot retrieve appointments");
            }
         
        String query = "DELETE FROM Appointments WHERE ID = ?";
        
        PreparedStatement stmt = _connection.prepareStatement(query);
        stmt.setInt(1, ID);
        stmt.executeQuery();
         
        } catch (SQLException e) {
            System.err.println("❌ SQL Error in getAll(): " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Unexpected error in getAll(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateAppointment(){
            
    }
    
    public void testDatabaseConnection() {
        appointments = getAll();
        
        if (appointments != null) {
            System.out.println("📊 Retrieved " + appointments.size() + " appointments");
            
            if (appointments.isEmpty()) {
                System.out.println("📭 No appointments found in database (table might be empty)");
            } else {
                System.out.println("📝 Appointments found:");
                for (int i = 0; i < appointments.size(); i++) {
                    Appointment app = appointments.get(i);
                    System.out.println("  " + (i+1) + ". ID: " + app.getId() + 
                                     ", Student: " + app.getStudentName() + 
                                     ", Counselor: " + app.getCounselorName() + 
                                     ", Date: " + app.getAppointmentDate() + 
                                     ", Time: " + app.getAppointmentTime() + 
                                     ", Status: " + app.getStatus());
                }
            }
        } else {
            System.out.println("❌ getAll() returned null - connection or query failed");
        }
        
        System.out.println("=" + "=".repeat(50));
    }
    
}
