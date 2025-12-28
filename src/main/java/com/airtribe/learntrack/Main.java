package com.airtribe.learntrack;

import com.airtribe.learntrack.entity.*;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.util.IdGenerator;
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

        // 7. Demonstrate IdGenerator Utility
        demonstrateIdGenerator();

        System.out.println("\n=== Demo Complete ===");
    }

    private static void demonstratePerson() {
        System.out.println("--- Person Class Demo ---");

        Person person = new Person(IdGenerator.getNextPersonId(), "John", "Doe", "john.doe@example.com");
        System.out.println(person);
        System.out.println("Display Name: " + person.getDisplayName());
        System.out.println();
    }

    private static void demonstrateStudent() {
        System.out.println("--- Student Class Demo (Constructor Overloading) ---");

        // Constructor 1: Without email
        Student student1 = new Student(IdGenerator.getNextStudentId(), "Alice", "Smith", "Batch-2024-Jan");
        System.out.println("Student 1 (without email): " + student1);

        // Constructor 2: With email
        Student student2 = new Student(IdGenerator.getNextStudentId(), "Bob", "Johnson", "bob.j@example.com", "Batch-2024-Jan");
        System.out.println("Student 2 (with email): " + student2);

        // Constructor 3: Full constructor with active status
        Student student3 = new Student(IdGenerator.getNextStudentId(), "Carol", "Williams", "carol.w@example.com", "Batch-2024-Feb", false);
        System.out.println("Student 3 (with active status): " + student3);

        // Using setters
        student1.setEmail("alice.smith@example.com");
        System.out.println("Student 1 after setting email: " + student1.getEmail());
        System.out.println("Student 1 Display Name: " + student1.getDisplayName());
        System.out.println();
    }

    private static void demonstrateCourse() {
        System.out.println("--- Course Class Demo ---");

        Course course1 = new Course(IdGenerator.getNextCourseId(), "Java Programming", "Comprehensive Java course covering basics to advanced topics", 12);
        System.out.println(course1);

        Course course2 = new Course(IdGenerator.getNextCourseId(), "Web Development", "Full-stack web development with modern frameworks", 16, true);
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
        enrollment1.setId(IdGenerator.getNextEnrollmentId());
        enrollment1.setStudentId(1001);  // Reference to student1
        enrollment1.setCourseId(2001);    // Reference to course1
        System.out.println("Enrollment 1 (default): " + enrollment1);

        // Constructor with enrollment date
        Enrollment enrollment2 = new Enrollment(IdGenerator.getNextEnrollmentId(), 1002, 2002, LocalDate.of(2024, 1, 15));
        System.out.println("Enrollment 2: " + enrollment2);

        // Full constructor with status
        Enrollment enrollment3 = new Enrollment(IdGenerator.getNextEnrollmentId(), 1003, 2001, LocalDate.of(2024, 2, 1), EnrollmentStatus.COMPLETED);
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
            Student invalidStudent = new Student(-1, "Test", "User", "Batch-2024");
            System.out.println(invalidStudent);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught validation error: " + e.getMessage());
        }

        // Try to set an invalid email
        try {
            Person person = new Person();
            person.setId(1);
            person.setFirstName("Test");
            person.setLastName("User");
            person.setEmail("invalid-email");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught validation error: " + e.getMessage());
        }

        // Try to create a course with invalid duration
        try {
            Course invalidCourse = new Course(1, "Test Course", "Description", -5);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught validation error: " + e.getMessage());
        }

        System.out.println("Validation is working correctly!");
    }

    private static void demonstrateInheritance() {
        System.out.println("\n--- Inheritance & Method Overriding Demo ---");

        // Create a Person (base class)
        Person person = new Person(IdGenerator.getNextPersonId(), "John", "Doe", "john.doe@example.com");

        // Create a Student (extends Person)
        Student student = new Student(IdGenerator.getNextStudentId(), "Alice", "Smith", "alice@example.com", "Batch-2024-Jan");

        // Create a Trainer (extends Person)
        Trainer trainer = new Trainer(IdGenerator.getNextTrainerId(), "Dr. Robert", "Brown", "robert.brown@example.com", "Java & Backend Development", 10);

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

    private static void demonstrateIdGenerator() {
        System.out.println("\n--- IdGenerator Utility Demo ---");

        System.out.println("\n1. Static Fields and Methods:");
        System.out.println("   - IdGenerator uses static fields to maintain ID counters");
        System.out.println("   - Static methods allow ID generation without creating instances");
        System.out.println("   - Each entity type has its own counter starting from different ranges");
        System.out.println("   - Thread-safe with synchronized methods");
        System.out.println("   - Range-based allocation prevents ID collisions\n");

        System.out.println("2. ID Ranges:");
        System.out.println("   - Persons:     100 - 999    (900 IDs)");
        System.out.println("   - Students:    1000 - 1999  (1000 IDs)");
        System.out.println("   - Courses:     2000 - 2999  (1000 IDs)");
        System.out.println("   - Enrollments: 3000 - 3999  (1000 IDs)");
        System.out.println("   - Trainers:    4000 - 4999 (1000 IDs)\n");

        System.out.println("3. Generating IDs for different entities:");

        // Generate Person IDs
        System.out.println("\n   Person IDs:");
        int personId1 = IdGenerator.getNextPersonId();
        int personId2 = IdGenerator.getNextPersonId();
        int personId3 = IdGenerator.getNextPersonId();
        System.out.println("   Generated Person IDs: " + personId1 + ", " + personId2 + ", " + personId3);

        // Generate Student IDs
        System.out.println("\n   Student IDs:");
        int studentId1 = IdGenerator.getNextStudentId();
        int studentId2 = IdGenerator.getNextStudentId();
        int studentId3 = IdGenerator.getNextStudentId();
        System.out.println("   Generated Student IDs: " + studentId1 + ", " + studentId2 + ", " + studentId3);

        // Generate Trainer IDs
        System.out.println("\n   Trainer IDs:");
        int trainerId1 = IdGenerator.getNextTrainerId();
        int trainerId2 = IdGenerator.getNextTrainerId();
        System.out.println("   Generated Trainer IDs: " + trainerId1 + ", " + trainerId2);

        // Generate Course IDs
        System.out.println("\n   Course IDs:");
        int courseId1 = IdGenerator.getNextCourseId();
        int courseId2 = IdGenerator.getNextCourseId();
        System.out.println("   Generated Course IDs: " + courseId1 + ", " + courseId2);

        // Generate Enrollment IDs
        System.out.println("\n   Enrollment IDs:");
        int enrollmentId1 = IdGenerator.getNextEnrollmentId();
        int enrollmentId2 = IdGenerator.getNextEnrollmentId();
        System.out.println("   Generated Enrollment IDs: " + enrollmentId1 + ", " + enrollmentId2);

        System.out.println("\n4. Using IdGenerator with entity creation:");
        Student newStudent = new Student(
                IdGenerator.getNextStudentId(),
                "Emily",
                "Davis",
                "emily.davis@example.com",
                "Batch-2025-Jan"
        );
        System.out.println("   Created student with auto-generated ID: " + newStudent);

        Course newCourse = new Course(
                IdGenerator.getNextCourseId(),
                "Advanced Java",
                "Deep dive into Java advanced concepts",
                16
        );
        System.out.println("   Created course with auto-generated ID: " + newCourse);

        Enrollment newEnrollment = new Enrollment(
                IdGenerator.getNextEnrollmentId(),
                newStudent.getId(),
                newCourse.getId(),
                LocalDate.now()
        );
        System.out.println("   Created enrollment with auto-generated ID: " + newEnrollment);

        System.out.println("\n5. Capacity Monitoring:");
        System.out.println("   - Remaining Person IDs: " + IdGenerator.getRemainingPersonIds());
        System.out.println("   - Remaining Student IDs: " + IdGenerator.getRemainingStudentIds());
        System.out.println("   - Remaining Trainer IDs: " + IdGenerator.getRemainingTrainerIds());
        System.out.println("   - Remaining Course IDs: " + IdGenerator.getRemainingCourseIds());
        System.out.println("   - Remaining Enrollment IDs: " + IdGenerator.getRemainingEnrollmentIds());

        System.out.println("\n6. Capacity Summary:");
        System.out.println("   " + IdGenerator.getCapacitySummary());

        System.out.println("\n7. Full Capacity Report:");
        IdGenerator.printCapacityReport();

        System.out.println("8. Benefits of static utility class:");
        System.out.println("   - No need to create instances (constructor is private)");
        System.out.println("   - Centralized ID management across the application");
        System.out.println("   - Each entity type has separate ID ranges to avoid conflicts");
        System.out.println("   - Thread-safe ID generation");
        System.out.println("   - Capacity tracking and warnings at 90% usage");
        System.out.println("   - Can validate and register external IDs");
    }
}