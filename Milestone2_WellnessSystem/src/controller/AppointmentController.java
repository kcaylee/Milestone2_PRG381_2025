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

    public ArrayList<Appointment> getAll(){
        ArrayList<Appointment> appointments = new ArrayList<>();
        
        try {
            _connection = DBConnection.getConnection();
            
            if (_connection == null) {
                System.err.println("❌ Database connection failed - cannot retrieve appointments");
                return appointments;
            }
            
            System.out.println("🔍 Attempting to query Appointments table...");
            Statement stmt = _connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Appointments");

            int ID;
            String studentName;
            String counselorName;
            Date appointmentDate;
            Time appointmentTime;
            String status;

            int count = 0;
            while (rs.next()) {
                ID = rs.getInt("id");
                studentName = rs.getString("student_name");
                counselorName = rs.getString("counselor_name");
                appointmentDate = rs.getDate("appointment_date");
                appointmentTime = rs.getTime("appointment_time");
                status = rs.getString("status");

                appointments.add(new Appointment(ID,studentName,counselorName,appointmentDate,appointmentTime,status));
                count++;
            }

            System.out.println("✅ Successfully retrieved " + count + " appointments from database");
            DBConnection.closeConnection();

            return appointments;

        } catch (SQLException e) {
            System.err.println("❌ SQL Error in getAll(): " + e.getMessage());
            e.printStackTrace();
            return appointments;
        } catch (Exception e) {
            System.err.println("❌ Unexpected error in getAll(): " + e.getMessage());
            e.printStackTrace();
            return appointments;
        }
    }

    public void getByID(){

    }

    public void addAppointment(){

    }

    public void deleteByID(){

    }

    public void updateAppointment(){

    }
    
    public void testDatabaseConnection() {
        System.out.println("🧪 Testing database connection and getAll() method...");
        System.out.println("=" + "=".repeat(50));
        
        ArrayList<Appointment> appointments = getAll();
        
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
