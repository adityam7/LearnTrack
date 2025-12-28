package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.util.InputValidator;

public class Course {
    private Long id;
    private String courseName;
    private String description;
    private int durationInWeeks;
    private boolean active;

    // Default constructor
    public Course() {
        this.active = true; // Default to active
    }

    // Parameterized constructor without active status
    public Course(Long id, String courseName, String description, int durationInWeeks) {
        setId(id);
        setCourseName(courseName);
        setDescription(description);
        setDurationInWeeks(durationInWeeks);
        this.active = true;
    }

    // Full parameterized constructor with all fields
    public Course(Long id, String courseName, String description, int durationInWeeks, boolean active) {
        setId(id);
        setCourseName(courseName);
        setDescription(description);
        setDurationInWeeks(durationInWeeks);
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        InputValidator.validateId(id, "Course ID");
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        InputValidator.validateNotNullOrEmpty(courseName, "Course name");
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        InputValidator.validateNotNullOrEmpty(description, "Description");
        this.description = description;
    }

    public int getDurationInWeeks() {
        return durationInWeeks;
    }

    public void setDurationInWeeks(int durationInWeeks) {
        if (durationInWeeks <= 0) {
            throw new IllegalArgumentException("Duration in weeks must be positive");
        }
        this.durationInWeeks = durationInWeeks;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", description='" + description + '\'' +
                ", durationInWeeks=" + durationInWeeks +
                ", active=" + active +
                '}';
    }
}
