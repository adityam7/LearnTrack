package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;
import java.util.List;

/**
 * Repository for Student entities.
 * Extends ActiveEntityRepository to inherit common CRUD operations and active/inactive management.
 * Demonstrates inheritance and specialization.
 */
public class StudentRepository extends ActiveEntityRepository<Student> {

    @Override
    protected int getId(Student student) {
        return student.getId();
    }

    @Override
    protected String getEntityName() {
        return "Student";
    }

    @Override
    protected boolean isActive(Student student) {
        return student.isActive();
    }

    @Override
    protected void setActive(Student student, boolean active) {
        student.setActive(active);
    }

    /**
     * Find students by batch.
     *
     * @param batch The batch to search for
     * @return List of students in the specified batch
     */
    public List<Student> findByBatch(String batch) {
        return entities.stream()
                .filter(student -> student.getBatch().equalsIgnoreCase(batch))
                .toList();
    }

    /**
     * Find student by email.
     *
     * @param email The email to search for
     * @return List of students with matching email
     */
    public List<Student> findByEmail(String email) {
        return entities.stream()
                .filter(student -> student.getEmail() != null
                        && student.getEmail().equalsIgnoreCase(email))
                .toList();
    }

    // Inherited methods from BaseRepository:
    // - add, findById, getById, findAll, update, exists, count, deleteById, clear

    // Inherited methods from ActiveEntityRepository:
    // - findAllActive, countActive, activate, deactivate
}