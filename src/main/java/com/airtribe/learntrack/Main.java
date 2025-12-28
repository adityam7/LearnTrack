package com.airtribe.learntrack;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.util.ConsoleUtils;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.List;

public class Main {
    private static StudentService studentService;
    private static CourseService courseService;
    private static EnrollmentService enrollmentService;

    public static void main(String[] args) {
        try {
            initializeServices();
            ConsoleUtils.printHeader("LearnTrack - Course Management System");

            boolean running = true;
            while (running) {
                try {
                    displayMainMenu();
                    int choice = ConsoleUtils.readIntInRange("Enter your choice: ", 0, 4);

                    switch (choice) {
                        case 1 -> handleStudentManagement();
                        case 2 -> handleCourseManagement();
                        case 3 -> handleEnrollmentManagement();
                        case 4 -> displayStatistics();
                        case 0 -> {
                            running = false;
                            ConsoleUtils.printSuccess("Thank you for using LearnTrack. Goodbye!");
                        }
                        default -> ConsoleUtils.printError("Invalid option. Please try again.");
                    }
                } catch (Exception e) {
                    ConsoleUtils.printError("An unexpected error occurred: " + e.getMessage());
                    ConsoleUtils.pressEnterToContinue();
                }
            }
        } finally {
            ConsoleUtils.closeScanner();
        }
    }

    private static void initializeServices() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

        studentService = new StudentService(studentRepository);
        courseService = new CourseService(courseRepository);
        enrollmentService = new EnrollmentService(enrollmentRepository, studentRepository, courseRepository);
    }

    private static void displayMainMenu() {
        ConsoleUtils.printSubHeader("Main Menu");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. View Statistics");
        System.out.println("0. Exit");
        ConsoleUtils.printSeparator();
    }

    // ==================== STUDENT MANAGEMENT ====================

    private static void handleStudentManagement() {
        boolean back = false;
        while (!back) {
            try {
                displayStudentMenu();
                int choice = ConsoleUtils.readIntInRange("Enter your choice: ", 0, 4);

                switch (choice) {
                    case 1 -> addNewStudent();
                    case 2 -> viewAllStudents();
                    case 3 -> searchStudentById();
                    case 4 -> deactivateStudent();
                    case 0 -> back = true;
                    default -> ConsoleUtils.printError("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                ConsoleUtils.printError("Error in student management: " + e.getMessage());
                ConsoleUtils.pressEnterToContinue();
            }
        }
    }

    private static void displayStudentMenu() {
        ConsoleUtils.printSubHeader("Student Management");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Deactivate Student");
        System.out.println("0. Back to Main Menu");
        ConsoleUtils.printSeparator();
    }

    private static void addNewStudent() {
        try {
            ConsoleUtils.printSubHeader("Add New Student");

            String firstName = ConsoleUtils.readLine("Enter first name: ");
            String lastName = ConsoleUtils.readLine("Enter last name: ");
            String batch = ConsoleUtils.readLine("Enter batch: ");
            boolean includeEmail = ConsoleUtils.readBoolean("Include email?");

            Student student;
            if (includeEmail) {
                String email = ConsoleUtils.readLine("Enter email: ");
                student = studentService.createStudent(firstName, lastName, email, batch);
            } else {
                student = studentService.createStudent(firstName, lastName, batch);
            }

            ConsoleUtils.printSuccess("Student added successfully!");
            System.out.println(student);
            ConsoleUtils.pressEnterToContinue();
        } catch (IllegalArgumentException e) {
            ConsoleUtils.printError("Validation error: " + e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        }
    }

    private static void viewAllStudents() {
        ConsoleUtils.printSubHeader("All Students");

        List<Student> students = studentService.getAll();
        if (students.isEmpty()) {
            ConsoleUtils.printInfo("No students found.");
        } else {
            System.out.printf("%-10s %-20s %-30s %-20s %-10s%n",
                    "ID", "Name", "Email", "Batch", "Active");
            ConsoleUtils.printSeparator();
            for (Student student : students) {
                System.out.printf("%-10d %-20s %-30s %-20s %-10s%n",
                        student.getId(),
                        student.getFirstName() + " " + student.getLastName(),
                        student.getEmail() != null ? student.getEmail() : "N/A",
                        student.getBatch(),
                        student.isActive() ? "Yes" : "No");
            }
        }
        ConsoleUtils.pressEnterToContinue();
    }

    private static void searchStudentById() {
        try {
            int id = ConsoleUtils.readInt("Enter student ID: ");
            Student student = studentService.getById(id);

            ConsoleUtils.printSubHeader("Student Details");
            System.out.println(student);
            System.out.println("Display Name: " + student.getDisplayName());
            ConsoleUtils.pressEnterToContinue();
        } catch (EntityNotFoundException e) {
            ConsoleUtils.printError(e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        }
    }

    private static void deactivateStudent() {
        try {
            int id = ConsoleUtils.readInt("Enter student ID to deactivate: ");
            Student student = studentService.getById(id);

            if (!student.isActive()) {
                ConsoleUtils.printInfo("Student is already inactive.");
            } else {
                studentService.deactivateStudent(id);
                ConsoleUtils.printSuccess("Student deactivated successfully!");
            }
            ConsoleUtils.pressEnterToContinue();
        } catch (EntityNotFoundException e) {
            ConsoleUtils.printError(e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        }
    }

    // ==================== COURSE MANAGEMENT ====================

    private static void handleCourseManagement() {
        boolean back = false;
        while (!back) {
            try {
                displayCourseMenu();
                int choice = ConsoleUtils.readIntInRange("Enter your choice: ", 0, 5);

                switch (choice) {
                    case 1 -> addNewCourse();
                    case 2 -> viewAllCourses();
                    case 3 -> searchCourseById();
                    case 4 -> activateCourse();
                    case 5 -> deactivateCourse();
                    case 0 -> back = true;
                    default -> ConsoleUtils.printError("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                ConsoleUtils.printError("Error in course management: " + e.getMessage());
                ConsoleUtils.pressEnterToContinue();
            }
        }
    }

    private static void displayCourseMenu() {
        ConsoleUtils.printSubHeader("Course Management");
        System.out.println("1. Add New Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search Course by ID");
        System.out.println("4. Activate Course");
        System.out.println("5. Deactivate Course");
        System.out.println("0. Back to Main Menu");
        ConsoleUtils.printSeparator();
    }

    private static void addNewCourse() {
        try {
            ConsoleUtils.printSubHeader("Add New Course");

            String courseName = ConsoleUtils.readLine("Enter course name: ");
            String description = ConsoleUtils.readLine("Enter description: ");
            int durationInWeeks = ConsoleUtils.readInt("Enter duration in weeks: ");

            Course course = courseService.createCourse(courseName, description, durationInWeeks);

            ConsoleUtils.printSuccess("Course added successfully!");
            System.out.println(course);
            ConsoleUtils.pressEnterToContinue();
        } catch (IllegalArgumentException e) {
            ConsoleUtils.printError("Validation error: " + e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        }
    }

    private static void viewAllCourses() {
        ConsoleUtils.printSubHeader("All Courses");

        List<Course> courses = courseService.getAll();
        if (courses.isEmpty()) {
            ConsoleUtils.printInfo("No courses found.");
        } else {
            System.out.printf("%-10s %-30s %-15s %-10s%n",
                    "ID", "Course Name", "Duration (weeks)", "Active");
            ConsoleUtils.printSeparator();
            for (Course course : courses) {
                System.out.printf("%-10d %-30s %-15d %-10s%n",
                        course.getId(),
                        course.getCourseName(),
                        course.getDurationInWeeks(),
                        course.isActive() ? "Yes" : "No");
            }
        }
        ConsoleUtils.pressEnterToContinue();
    }

    private static void searchCourseById() {
        try {
            int id = ConsoleUtils.readInt("Enter course ID: ");
            Course course = courseService.getById(id);

            ConsoleUtils.printSubHeader("Course Details");
            System.out.println(course);
            ConsoleUtils.pressEnterToContinue();
        } catch (EntityNotFoundException e) {
            ConsoleUtils.printError(e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        }
    }

    private static void activateCourse() {
        try {
            int id = ConsoleUtils.readInt("Enter course ID to activate: ");
            Course course = courseService.getById(id);

            if (course.isActive()) {
                ConsoleUtils.printInfo("Course is already active.");
            } else {
                courseService.activateCourse(id);
                ConsoleUtils.printSuccess("Course activated successfully!");
            }
            ConsoleUtils.pressEnterToContinue();
        } catch (EntityNotFoundException e) {
            ConsoleUtils.printError(e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        }
    }

    private static void deactivateCourse() {
        try {
            int id = ConsoleUtils.readInt("Enter course ID to deactivate: ");
            Course course = courseService.getById(id);

            if (!course.isActive()) {
                ConsoleUtils.printInfo("Course is already inactive.");
            } else {
                courseService.deactivateCourse(id);
                ConsoleUtils.printSuccess("Course deactivated successfully!");
            }
            ConsoleUtils.pressEnterToContinue();
        } catch (EntityNotFoundException e) {
            ConsoleUtils.printError(e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        }
    }

    // ==================== ENROLLMENT MANAGEMENT ====================

    private static void handleEnrollmentManagement() {
        boolean back = false;
        while (!back) {
            try {
                displayEnrollmentMenu();
                int choice = ConsoleUtils.readIntInRange("Enter your choice: ", 0, 4);

                switch (choice) {
                    case 1 -> enrollStudent();
                    case 2 -> viewEnrollmentsByStudent();
                    case 3 -> viewAllEnrollments();
                    case 4 -> updateEnrollmentStatus();
                    case 0 -> back = true;
                    default -> ConsoleUtils.printError("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                ConsoleUtils.printError("Error in enrollment management: " + e.getMessage());
                ConsoleUtils.pressEnterToContinue();
            }
        }
    }

    private static void displayEnrollmentMenu() {
        ConsoleUtils.printSubHeader("Enrollment Management");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. View Enrollments by Student");
        System.out.println("3. View All Enrollments");
        System.out.println("4. Update Enrollment Status");
        System.out.println("0. Back to Main Menu");
        ConsoleUtils.printSeparator();
    }

    private static void enrollStudent() {
        try {
            ConsoleUtils.printSubHeader("Enroll Student in Course");

            int studentId = ConsoleUtils.readInt("Enter student ID: ");
            int courseId = ConsoleUtils.readInt("Enter course ID: ");

            Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);

            ConsoleUtils.printSuccess("Student enrolled successfully!");
            System.out.println(enrollment);
            ConsoleUtils.pressEnterToContinue();
        } catch (EntityNotFoundException e) {
            ConsoleUtils.printError(e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        } catch (IllegalStateException e) {
            ConsoleUtils.printError("Enrollment error: " + e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        }
    }

    private static void viewEnrollmentsByStudent() {
        try {
            int studentId = ConsoleUtils.readInt("Enter student ID: ");
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);

            ConsoleUtils.printSubHeader("Enrollments for Student ID: " + studentId);

            if (enrollments.isEmpty()) {
                ConsoleUtils.printInfo("No enrollments found for this student.");
            } else {
                System.out.printf("%-10s %-15s %-15s %-15s%n",
                        "Enroll ID", "Course ID", "Date", "Status");
                ConsoleUtils.printSeparator();
                for (Enrollment enrollment : enrollments) {
                    System.out.printf("%-10d %-15d %-15s %-15s%n",
                            enrollment.getId(),
                            enrollment.getCourseId(),
                            enrollment.getEnrollmentDate(),
                            enrollment.getStatus());
                }
            }
            ConsoleUtils.pressEnterToContinue();
        } catch (EntityNotFoundException e) {
            ConsoleUtils.printError(e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        }
    }

    private static void viewAllEnrollments() {
        ConsoleUtils.printSubHeader("All Enrollments");

        List<Enrollment> enrollments = enrollmentService.getAll();
        if (enrollments.isEmpty()) {
            ConsoleUtils.printInfo("No enrollments found.");
        } else {
            System.out.printf("%-10s %-15s %-15s %-15s %-15s%n",
                    "Enroll ID", "Student ID", "Course ID", "Date", "Status");
            ConsoleUtils.printSeparator();
            for (Enrollment enrollment : enrollments) {
                System.out.printf("%-10d %-15d %-15d %-15s %-15s%n",
                        enrollment.getId(),
                        enrollment.getStudentId(),
                        enrollment.getCourseId(),
                        enrollment.getEnrollmentDate(),
                        enrollment.getStatus());
            }
        }
        ConsoleUtils.pressEnterToContinue();
    }

    private static void updateEnrollmentStatus() {
        try {
            ConsoleUtils.printSubHeader("Update Enrollment Status");

            int enrollmentId = ConsoleUtils.readInt("Enter enrollment ID: ");
            Enrollment enrollment = enrollmentService.getById(enrollmentId);

            System.out.println("Current enrollment: " + enrollment);
            System.out.println("\nAvailable statuses:");
            System.out.println("1. ACTIVE");
            System.out.println("2. COMPLETED");
            System.out.println("3. CANCELLED");

            int statusChoice = ConsoleUtils.readIntInRange("Select new status: ", 1, 3);

            EnrollmentStatus newStatus = switch (statusChoice) {
                case 1 -> EnrollmentStatus.ACTIVE;
                case 2 -> EnrollmentStatus.COMPLETED;
                case 3 -> EnrollmentStatus.CANCELLED;
                default -> throw new IllegalStateException("Unexpected value: " + statusChoice);
            };

            enrollmentService.updateEnrollmentStatus(enrollmentId, newStatus);
            ConsoleUtils.printSuccess("Enrollment status updated successfully!");
            ConsoleUtils.pressEnterToContinue();
        } catch (EntityNotFoundException e) {
            ConsoleUtils.printError(e.getMessage());
            ConsoleUtils.pressEnterToContinue();
        }
    }

    // ==================== STATISTICS ====================

    private static void displayStatistics() {
        ConsoleUtils.printSubHeader("System Statistics");

        System.out.println("Students:");
        System.out.println("  Total: " + studentService.getTotalCount());
        System.out.println("  Active: " + studentService.getActiveCount());

        System.out.println("\nCourses:");
        System.out.println("  Total: " + courseService.getTotalCount());
        System.out.println("  Active: " + courseService.getActiveCount());

        System.out.println("\nEnrollments:");
        System.out.println("  Total: " + enrollmentService.getTotalCount());
        System.out.println("  Active: " + enrollmentService.getEnrollmentCountByStatus(EnrollmentStatus.ACTIVE));
        System.out.println("  Completed: " + enrollmentService.getEnrollmentCountByStatus(EnrollmentStatus.COMPLETED));
        System.out.println("  Cancelled: " + enrollmentService.getEnrollmentCountByStatus(EnrollmentStatus.CANCELLED));

        // Display capacity warnings
        System.out.println("\nCapacity Warnings:");
        boolean hasWarnings = false;

        if (IdGenerator.getRemainingStudentIds() < 100) {
            ConsoleUtils.printWarning("Student ID capacity low: " +
                    IdGenerator.getRemainingStudentIds() + " IDs remaining");
            hasWarnings = true;
        }

        if (IdGenerator.getRemainingCourseIds() < 100) {
            ConsoleUtils.printWarning("Course ID capacity low: " +
                    IdGenerator.getRemainingCourseIds() + " IDs remaining");
            hasWarnings = true;
        }

        if (IdGenerator.getRemainingEnrollmentIds() < 100) {
            ConsoleUtils.printWarning("Enrollment ID capacity low: " +
                    IdGenerator.getRemainingEnrollmentIds() + " IDs remaining");
            hasWarnings = true;
        }

        if (IdGenerator.getRemainingTrainerIds() < 100) {
            ConsoleUtils.printWarning("Trainer ID capacity low: " +
                    IdGenerator.getRemainingTrainerIds() + " IDs remaining");
            hasWarnings = true;
        }

        if (!hasWarnings) {
            ConsoleUtils.printInfo("All ID ranges have sufficient capacity");
        }

        ConsoleUtils.pressEnterToContinue();
    }
}
