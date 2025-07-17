package controller;

import java.sql.*;
import java.util.ArrayList;
import model.Appointment;
import model.DBConnection;

public class AppointmentController {

    private Connection _connection;
    private ArrayList<Appointment> appointments = new ArrayList<>();

    // Helper: Convert ResultSet to List of Appointments
    private ArrayList<Appointment> extractAppointments(ResultSet rs) throws SQLException {
        appointments.clear();
        while (rs.next()) {
            int id = rs.getInt("id");
            String studentName = rs.getString("student_name");
            String counselorName = rs.getString("counselor_name");
            Date date = rs.getDate("appointment_date");
            Time time = rs.getTime("appointment_time");
            String status = rs.getString("status");
            appointments.add(new Appointment(id, studentName, counselorName, date, time, status));
        }
        return appointments;
    }

    // Get all appointments
    public ArrayList<Appointment> getAll() {
        try {
            _connection = DBConnection.getConnection();
            if (_connection == null) return null;

            String query = "SELECT * FROM Appointments";
            Statement stmt = _connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            return extractAppointments(rs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get appointment by ID
    public ArrayList<Appointment> getByID(int id) {
        try {
            _connection = DBConnection.getConnection();
            if (_connection == null) return null;

            String query = "SELECT * FROM Appointments WHERE id = ?";
            PreparedStatement stmt = _connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            return extractAppointments(rs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Add new appointment
    public boolean addAppointment(String studentName, String counselorName, Date date, Time time, String status) {
        try {
            _connection = DBConnection.getConnection();
            if (_connection == null) return false;

            String query = "INSERT INTO Appointments (student_name, counselor_name, appointment_date, appointment_time, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = _connection.prepareStatement(query);
            stmt.setString(1, studentName);
            stmt.setString(2, counselorName);
            stmt.setDate(3, date);
            stmt.setTime(4, time);
            stmt.setString(5, status);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update appointment
    public boolean updateAppointment(String studentName, String counselorName, Date date, Time time, String status, int id) {
        try {
            _connection = DBConnection.getConnection();
            if (_connection == null) return false;

            String query = "UPDATE Appointments SET student_name = ?, counselor_name = ?, appointment_date = ?, appointment_time = ?, status = ? WHERE id = ?";
            PreparedStatement stmt = _connection.prepareStatement(query);
            stmt.setString(1, studentName);
            stmt.setString(2, counselorName);
            stmt.setDate(3, date);
            stmt.setTime(4, time);
            stmt.setString(5, status);
            stmt.setInt(6, id);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete appointment by ID
    public void deleteByID(int id) {
        try {
            _connection = DBConnection.getConnection();
            if (_connection == null) return;

            String query = "DELETE FROM Appointments WHERE id = ?";
            PreparedStatement stmt = _connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
