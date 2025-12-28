package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Enrollment entities.
 * Extends BaseRepository to inherit common CRUD operations.
 * Demonstrates inheritance and specialization.
 */
public class EnrollmentRepository extends BaseRepository<Enrollment> {

    @Override
    protected int getId(Enrollment enrollment) {
        return enrollment.getId();
    }

    @Override
    protected String getEntityName() {
        return "Enrollment";
    }

    /**
     * Find all enrollments for a specific student.
     *
     * @param studentId The student ID to search for
     * @return List of enrollments for the student
     */
    public List<Enrollment> findByStudentId(int studentId) {
        return entities.stream()
                .filter(enrollment -> enrollment.getStudentId() == studentId)
                .toList();
    }

    /**
     * Find all enrollments for a specific course.
     *
     * @param courseId The course ID to search for
     * @return List of enrollments for the course
     */
    public List<Enrollment> findByCourseId(int courseId) {
        return entities.stream()
                .filter(enrollment -> enrollment.getCourseId() == courseId)
                .toList();
    }

    /**
     * Find enrollments by status.
     *
     * @param status The enrollment status to filter by
     * @return List of enrollments with the specified status
     */
    public List<Enrollment> findByStatus(EnrollmentStatus status) {
        return entities.stream()
                .filter(enrollment -> enrollment.getStatus() == status)
                .toList();
    }

    /**
     * Find a specific enrollment by student and course.
     *
     * @param studentId The student ID
     * @param courseId The course ID
     * @return Optional containing the enrollment if found
     */
    public Optional<Enrollment> findByStudentAndCourse(int studentId, int courseId) {
        return entities.stream()
                .filter(enrollment -> enrollment.getStudentId() == studentId
                        && enrollment.getCourseId() == courseId)
                .findFirst();
    }

    /**
     * Update the status of an enrollment.
     *
     * @param id The enrollment ID
     * @param status The new status
     */
    public void updateStatus(int id, EnrollmentStatus status) {
        Enrollment enrollment = getById(id);
        enrollment.setStatus(status);
    }

    /**
     * Check if a student is enrolled in a specific course.
     *
     * @param studentId The student ID
     * @param courseId The course ID
     * @return true if enrolled, false otherwise
     */
    public boolean isStudentEnrolledInCourse(int studentId, int courseId) {
        return findByStudentAndCourse(studentId, courseId).isPresent();
    }

    /**
     * Count enrollments by status.
     *
     * @param status The status to count
     * @return The number of enrollments with the specified status
     */
    public int countByStatus(EnrollmentStatus status) {
        return (int) entities.stream()
                .filter(enrollment -> enrollment.getStatus() == status)
                .count();
    }

    /**
     * Find active enrollments for a student.
     *
     * @param studentId The student ID
     * @return List of active enrollments for the student
     */
    public List<Enrollment> findActiveEnrollmentsByStudentId(int studentId) {
        return entities.stream()
                .filter(enrollment -> enrollment.getStudentId() == studentId
                        && enrollment.getStatus() == EnrollmentStatus.ACTIVE)
                .toList();
    }

    /**
     * Find active enrollments for a course.
     *
     * @param courseId The course ID
     * @return List of active enrollments for the course
     */
    public List<Enrollment> findActiveEnrollmentsByCourseId(int courseId) {
        return entities.stream()
                .filter(enrollment -> enrollment.getCourseId() == courseId
                        && enrollment.getStatus() == EnrollmentStatus.ACTIVE)
                .toList();
    }
}