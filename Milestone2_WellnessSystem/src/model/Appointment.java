package model;

import java.sql.Date;
import java.sql.Time;

/**
 * Model class representing an appointment.
 * Used for transferring appointment data between the GUI, controller, and database.
 * Supports encapsulation and constructor overloading.
 */
public class Appointment {
    private int id;
    private String studentName;
    private String counselorName;
    private Date appointmentDate;
    private Time appointmentTime;
    private String status;

    /**
     * Constructor for creating a new appointment (no ID yet).
     * Used when inserting new records into the database.
     */
    public Appointment(String studentName, String counselorName, Date appointmentDate, Time appointmentTime, String status) {
        this.studentName = studentName;
        this.counselorName = counselorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    /**
     * Constructor for loading existing appointments (includes ID).
     * Used when reading records from the database.
     */
    public Appointment(int id, String studentName, String counselorName, Date appointmentDate, Time appointmentTime, String status) {
        this(studentName, counselorName, appointmentDate, appointmentTime, status);
        this.id = id;
    }

    // Getters and Setters

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getStudentName() { return studentName; }

    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getCounselorName() { return counselorName; }

    public void setCounselorName(String counselorName) { this.counselorName = counselorName; }

    public Date getAppointmentDate() { return appointmentDate; }

    public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }

    public Time getAppointmentTime() { return appointmentTime; }

    public void setAppointmentTime(Time appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
