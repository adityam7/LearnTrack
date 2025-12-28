package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.util.InputValidator;

public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public Person() {
    }

    public Person(Long id, String firstName, String lastName, String email) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        InputValidator.validateId(id, "ID");
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        InputValidator.validateNotNullOrEmpty(firstName, "First name");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        InputValidator.validateNotNullOrEmpty(lastName, "Last name");
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        InputValidator.validateEmail(email);
        this.email = email;
    }

    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}