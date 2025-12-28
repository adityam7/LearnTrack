package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class for Enrollment entities.
 * Extends BaseService to inherit common CRUD operations.
 * Includes complex cross-entity validation and enrollment-specific business logic.
 */
public class EnrollmentService extends BaseService<Enrollment, EnrollmentRepository> {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository) {
        super(enrollmentRepository);
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    protected String getEntityName() {
        return "Enrollment";
    }

    /**
     * Enroll a student in a course with validation.
     *
     * @param studentId The student ID
     * @param courseId The course ID
     * @return The created enrollment
     * @throws EntityNotFoundException if student or course doesn't exist
     * @throws IllegalStateException if student is already enrolled
     */
    public Enrollment enrollStudent(int studentId, int courseId) {
        // Validate student exists
        Student student = studentRepository.getById(studentId);

        // Validate student is active
        if (!student.isActive()) {
            throw new IllegalStateException(
                    "Cannot enroll inactive student. Student ID: " + studentId);
        }

        // Validate course exists
        Course course = courseRepository.getById(courseId);

        // Validate course is active
        if (!course.isActive()) {
            throw new IllegalStateException(
                    "Cannot enroll in inactive course. Course ID: " + courseId);
        }

        // Check if already enrolled
        if (repository.isStudentEnrolledInCourse(studentId, courseId)) {
            throw new IllegalStateException("Student is already enrolled in this course");
        }

        // Create enrollment
        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId, LocalDate.now());
        repository.add(enrollment);
        return enrollment;
    }

    /**
     * Get all enrollments for a specific student.
     *
     * @param studentId The student ID
     * @return List of enrollments
     * @throws EntityNotFoundException if student doesn't exist
     */
    public List<Enrollment> getEnrollmentsByStudentId(int studentId) {
        if (!studentRepository.exists(studentId)) {
            throw new EntityNotFoundException("Student", studentId);
        }
        return repository.findByStudentId(studentId);
    }

    /**
     * Get all enrollments for a specific course.
     *
     * @param courseId The course ID
     * @return List of enrollments
     * @throws EntityNotFoundException if course doesn't exist
     */
    public List<Enrollment> getEnrollmentsByCourseId(int courseId) {
        if (!courseRepository.exists(courseId)) {
            throw new EntityNotFoundException("Course", courseId);
        }
        return repository.findByCourseId(courseId);
    }

    /**
     * Get enrollments by status.
     *
     * @param status The enrollment status to filter by
     * @return List of enrollments with the specified status
     */
    public List<Enrollment> getEnrollmentsByStatus(EnrollmentStatus status) {
        return repository.findByStatus(status);
    }

    /**
     * Update the status of an enrollment.
     *
     * @param id The enrollment ID
     * @param status The new status
     */
    public void updateEnrollmentStatus(int id, EnrollmentStatus status) {
        repository.updateStatus(id, status);
    }

    /**
     * Mark an enrollment as completed.
     *
     * @param id The enrollment ID
     */
    public void completeEnrollment(int id) {
        updateEnrollmentStatus(id, EnrollmentStatus.COMPLETED);
    }

    /**
     * Cancel an enrollment.
     *
     * @param id The enrollment ID
     */
    public void cancelEnrollment(int id) {
        updateEnrollmentStatus(id, EnrollmentStatus.CANCELLED);
    }

    /**
     * Check if a student is enrolled in a specific course.
     *
     * @param studentId The student ID
     * @param courseId The course ID
     * @return true if enrolled, false otherwise
     */
    public boolean isStudentEnrolledInCourse(int studentId, int courseId) {
        return repository.isStudentEnrolledInCourse(studentId, courseId);
    }

    /**
     * Count enrollments by status.
     *
     * @param status The status to count
     * @return The number of enrollments with the specified status
     */
    public int getEnrollmentCountByStatus(EnrollmentStatus status) {
        return repository.countByStatus(status);
    }

    // Inherited methods from BaseService:
    // - getById(int id)
    // - getAll()
    // - update(T entity)
    // - exists(int id)
    // - getTotalCount()
}
