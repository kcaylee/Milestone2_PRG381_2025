package model;

/**
 * Model class representing a counselor.
 * Used for storing and transferring counselor data between GUI, controller, and database.
 * Supports constructor overloading for insert and read operations.
 */
public class Counselor {
    private int id;
    private String name;
    private String specialization;
    private String availability;

    /**
     * Constructor used when inserting a new counselor (no ID yet).
     */
    public Counselor(String name, String specialization, String availability) {
        this.name = name;
        this.specialization = specialization;
        this.availability = availability;
    }

    /**
     * Constructor used when loading an existing counselor from the database (with ID).
     */
    public Counselor(int id, String name, String specialization, String availability) {
        this(name, specialization, availability);
        this.id = id;
    }

    // Getters and Setters

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }

    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getAvailability() { return availability; }

    public void setAvailability(String availability) { this.availability = availability; }
}
