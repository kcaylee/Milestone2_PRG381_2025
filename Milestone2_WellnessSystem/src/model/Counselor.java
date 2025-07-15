package model;

public class Counselor {
    private int id;
    private String name;
    private String specialization;
    private String availability;

    // Constructor without ID (for inserts)
    public Counselor(String name, String specialization, String availability) {
        this.name = name;
        this.specialization = specialization;
        this.availability = availability;
    }

    // Constructor with ID (for reads)
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
