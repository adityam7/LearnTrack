package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.util.InputValidator;

public class Trainer extends Person {
    private String specialization;
    private int yearsOfExperience;

    // Default constructor
    public Trainer() {
        super(); // Explicitly calling parent constructor
    }

    // Parameterized constructor demonstrating use of super
    public Trainer(int id, String firstName, String lastName, String email, String specialization, int yearsOfExperience) {
        super(id, firstName, lastName, email); // Call parent constructor with super
        setSpecialization(specialization);
        setYearsOfExperience(yearsOfExperience);
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        InputValidator.validateNotNullOrEmpty(specialization, "Specialization");
        this.specialization = specialization;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        if (yearsOfExperience < 0) {
            throw new IllegalArgumentException("Years of experience cannot be negative");
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    // Method overriding - specialized behavior for Trainer
    @Override
    public String getDisplayName() {
        // Using super to call parent's method and extend it
        return "Trainer: " + super.getDisplayName() + " (Specialization: " + specialization + ")";
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", specialization='" + specialization + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                '}';
    }
}
