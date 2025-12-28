package com.airtribe.learntrack;

import com.airtribe.learntrack.entity.*;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== LearnTrack - Course Management System ===\n");

        // 1. Demonstrate Person class
        demonstratePerson();

        // 2. Demonstrate Student class (with constructor overloading)
        demonstrateStudent();

        // 3. Demonstrate Course class
        demonstrateCourse();

        // 4. Demonstrate Enrollment class
        demonstrateEnrollment();

        // 5. Demonstrate input validation
        demonstrateValidation();

        // 6. Demonstrate Inheritance and Method Overriding
        demonstrateInheritance();

        System.out.println("\n=== Demo Complete ===");
    }

    private static void demonstratePerson() {
        System.out.println("--- Person Class Demo ---");

        Person person = new Person(1L, "John", "Doe", "john.doe@example.com");
        System.out.println(person);
        System.out.println("Display Name: " + person.getDisplayName());
        System.out.println();
    }

    private static void demonstrateStudent() {
        System.out.println("--- Student Class Demo (Constructor Overloading) ---");

        // Constructor 1: Without email
        Student student1 = new Student(101L, "Alice", "Smith", "Batch-2024-Jan");
        System.out.println("Student 1 (without email): " + student1);

        // Constructor 2: With email
        Student student2 = new Student(102L, "Bob", "Johnson", "bob.j@example.com", "Batch-2024-Jan");
        System.out.println("Student 2 (with email): " + student2);

        // Constructor 3: Full constructor with active status
        Student student3 = new Student(103L, "Carol", "Williams", "carol.w@example.com", "Batch-2024-Feb", false);
        System.out.println("Student 3 (with active status): " + student3);

        // Using setters
        student1.setEmail("alice.smith@example.com");
        System.out.println("Student 1 after setting email: " + student1.getEmail());
        System.out.println("Student 1 Display Name: " + student1.getDisplayName());
        System.out.println();
    }

    private static void demonstrateCourse() {
        System.out.println("--- Course Class Demo ---");

        Course course1 = new Course(201L, "Java Programming", "Comprehensive Java course covering basics to advanced topics", 12);
        System.out.println(course1);

        Course course2 = new Course(202L, "Web Development", "Full-stack web development with modern frameworks", 16, true);
        System.out.println(course2);

        // Using setters
        course1.setActive(true);
        System.out.println("Course 1 is active: " + course1.isActive());
        System.out.println();
    }

    private static void demonstrateEnrollment() {
        System.out.println("--- Enrollment Class Demo ---");

        // Default constructor (uses current date and ACTIVE status)
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setId(301L);
        enrollment1.setStudentId(101L);
        enrollment1.setCourseId(201L);
        System.out.println("Enrollment 1 (default): " + enrollment1);

        // Constructor with enrollment date
        Enrollment enrollment2 = new Enrollment(302L, 102L, 202L, LocalDate.of(2024, 1, 15));
        System.out.println("Enrollment 2: " + enrollment2);

        // Full constructor with status
        Enrollment enrollment3 = new Enrollment(303L, 103L, 201L, LocalDate.of(2024, 2, 1), EnrollmentStatus.COMPLETED);
        System.out.println("Enrollment 3: " + enrollment3);

        // Update enrollment status
        enrollment2.setStatus(EnrollmentStatus.CANCELLED);
        System.out.println("Enrollment 2 after status update: " + enrollment2);
        System.out.println();
    }

    private static void demonstrateValidation() {
        System.out.println("--- Input Validation Demo ---");

        // Try to create a student with invalid data
        try {
            Student invalidStudent = new Student(null, "Test", "User", "Batch-2024");
            System.out.println(invalidStudent);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught validation error: " + e.getMessage());
        }

        // Try to set an invalid email
        try {
            Person person = new Person();
            person.setId(1L);
            person.setFirstName("Test");
            person.setLastName("User");
            person.setEmail("invalid-email");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught validation error: " + e.getMessage());
        }

        // Try to create a course with invalid duration
        try {
            Course invalidCourse = new Course(1L, "Test Course", "Description", -5);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught validation error: " + e.getMessage());
        }

        System.out.println("Validation is working correctly!");
    }

    private static void demonstrateInheritance() {
        System.out.println("\n--- Inheritance & Method Overriding Demo ---");

        // Create a Person (base class)
        Person person = new Person(1L, "John", "Doe", "john.doe@example.com");

        // Create a Student (extends Person)
        Student student = new Student(101L, "Alice", "Smith", "alice@example.com", "Batch-2024-Jan");

        // Create a Trainer (extends Person)
        Trainer trainer = new Trainer(201L, "Dr. Robert", "Brown", "robert.brown@example.com", "Java & Backend Development", 10);

        System.out.println("\n1. Use of 'super' in constructors:");
        System.out.println("   - Student constructor calls super(id, firstName, lastName, email)");
        System.out.println("   - Trainer constructor calls super(id, firstName, lastName, email)");
        System.out.println("   - This demonstrates how child classes use 'super' to initialize parent fields\n");

        System.out.println("2. Method Overriding - getDisplayName():");
        System.out.println("   Person (base class):   " + person.getDisplayName());
        System.out.println("   Student (override):    " + student.getDisplayName());
        System.out.println("   Trainer (override):    " + trainer.getDisplayName());

        System.out.println("\n3. How overriding works:");
        System.out.println("   - Person.getDisplayName() returns: 'firstName lastName'");
        System.out.println("   - Student.getDisplayName() calls super.getDisplayName() and adds batch info");
        System.out.println("   - Trainer.getDisplayName() calls super.getDisplayName() and adds specialization info");

        System.out.println("\n4. Polymorphism demonstration:");
        Person[] people = {person, student, trainer};
        System.out.println("   Calling getDisplayName() on array of Person type:");
        for (int i = 0; i < people.length; i++) {
            System.out.println("   [" + i + "] " + people[i].getDisplayName());
        }
        System.out.println("   Notice: Each object calls its own version of getDisplayName()!");

        System.out.println("\n5. Trainer specific details:");
        System.out.println(trainer);
        System.out.println("   Specialization: " + trainer.getSpecialization());
        System.out.println("   Years of Experience: " + trainer.getYearsOfExperience());
    }
}