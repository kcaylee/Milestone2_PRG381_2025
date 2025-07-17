package model;

/**
 * Model class representing student feedback.
 * Used to transfer feedback data between the view, controller, and database layers.
 * Supports encapsulation and constructor overloading.
 */
public class Feedback {
    private int id;
    private String studentName;
    private String counselorName; // Optional, may not be stored in DB
    private int rating;
    private String comments;

    /**
     * Constructor for inserting new feedback records (no ID assigned yet).
     */
    public Feedback(String studentName, String counselorName, int rating, String comments) {
        this.studentName = studentName;
        this.counselorName = counselorName;
        this.rating = rating;
        this.comments = comments;
    }

    /**
     * Constructor for reading existing feedback records (includes ID).
     */
    public Feedback(int id, String studentName, String counselorName, int rating, String comments) {
        this(studentName, counselorName, rating, comments);
        this.id = id;
    }

    // Getters and Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getCounselorName() { return counselorName; }
    public void setCounselorName(String counselorName) { this.counselorName = counselorName; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}
