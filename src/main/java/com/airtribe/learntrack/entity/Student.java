package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.util.InputValidator;

public class Student extends Person {
    private String batch;
    private boolean active;

    // Default constructor
    public Student() {
        super();
        this.active = true; // Default to active
    }

    // Constructor overloading - without email (demonstrates constructor overloading)
    public Student(Long id, String firstName, String lastName, String batch) {
        super();
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setBatch(batch);
        this.active = true;
    }

    // Constructor overloading - with email
    public Student(Long id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        setBatch(batch);
        this.active = true;
    }

    // Full constructor with all fields including active status
    public Student(Long id, String firstName, String lastName, String email, String batch, boolean active) {
        super(id, firstName, lastName, email);
        setBatch(batch);
        this.active = active;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        InputValidator.validateNotNullOrEmpty(batch, "Batch");
        this.batch = batch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Method overriding - specialized behavior for Student
    @Override
    public String getDisplayName() {
        // Using super to call parent's method and extend it
        return "Student: " + super.getDisplayName() + " (Batch: " + batch + ")";
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", batch='" + batch + '\'' +
                ", active=" + active +
                '}';
    }
}
