package controller;

import model.Appointment;
import model.DBConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * Controller for managing Appointment operations (CRUD) with JavaDB.
 */
public class AppointmentController {

    /**
     * Retrieves all appointments from the database.
     */
    public ArrayList<Appointment> getAll() {
        ArrayList<Appointment> list = new ArrayList<>();
        String query = "SELECT * FROM Appointments";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Appointment a = new Appointment(
                    rs.getInt("id"),
                    rs.getString("student_name"),
                    rs.getString("counselor_name"),
                    rs.getDate("appointment_date"),
                    rs.getTime("appointment_time"),
                    rs.getString("status")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving appointments: " + e.getMessage());
        }

        return list;
    }

    /**
     * Gets appointments by specific ID.
     */
    public ArrayList<Appointment> getByID(int id) {
        ArrayList<Appointment> list = new ArrayList<>();
        String query = "SELECT * FROM Appointments WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment a = new Appointment(
                    rs.getInt("id"),
                    rs.getString("student_name"),
                    rs.getString("counselor_name"),
                    rs.getDate("appointment_date"),
                    rs.getTime("appointment_time"),
                    rs.getString("status")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving appointment by ID: " + e.getMessage());
        }

        return list;
    }

    /**
     * Adds a new appointment to the database.
     */
    public boolean addAppointment(String studentName, String counselorName, Date date, Time time, String status) {
        String query = "INSERT INTO Appointments (student_name, counselor_name, appointment_date, appointment_time, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, studentName);
            ps.setString(2, counselorName);
            ps.setDate(3, date);
            ps.setTime(4, time);
            ps.setString(5, status);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding appointment: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing appointment record.
     */
    public boolean updateAppointment(String studentName, String counselorName, Date date, Time time, String status, int id) {
        String query = "UPDATE Appointments SET student_name = ?, counselor_name = ?, appointment_date = ?, appointment_time = ?, status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, studentName);
            ps.setString(2, counselorName);
            ps.setDate(3, date);
            ps.setTime(4, time);
            ps.setString(5, status);
            ps.setInt(6, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating appointment: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes an appointment by its ID.
     */
    public boolean deleteByID(int id) {
        String query = "DELETE FROM Appointments WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting appointment: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if a counselor already has an appointment at the given date and time.
     */
    public boolean isDuplicateAppointment(String counselorName, Date date, Time time) {
        String query = "SELECT COUNT(*) FROM Appointments WHERE counselor_name = ? AND appointment_date = ? AND appointment_time = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, counselorName);
            ps.setDate(2, date);
            ps.setTime(3, time);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error checking duplicate appointment: " + e.getMessage());
        }

        return false;
    }

    /**
     * Checks if a student already has an appointment at the same date and time.
     * Prevents double-booking with different counselors.
     */
    public boolean studentHasAppointmentAtTime(String studentName, Date date, Time time) {
        String query = "SELECT COUNT(*) FROM Appointments WHERE student_name = ? AND appointment_date = ? AND appointment_time = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, studentName);
            ps.setDate(2, date);
            ps.setTime(3, time);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error checking student appointment conflict: " + e.getMessage());
        }

        return false;
    }
}
