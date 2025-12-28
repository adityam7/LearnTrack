package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;

/**
 * Service class for Student entities.
 * Extends ActiveEntityService to inherit common CRUD operations.
 * Demonstrates inheritance and specialization.
 */
public class StudentService extends ActiveEntityService<Student, StudentRepository> {

    public StudentService(StudentRepository studentRepository) {
        super(studentRepository);
    }

    @Override
    protected String getEntityName() {
        return "Student";
    }

    /**
     * Create a new student with all fields.
     *
     * @param firstName Student's first name
     * @param lastName Student's last name
     * @param email Student's email address
     * @param batch Student's batch
     * @return The created student
     */
    public Student createStudent(String firstName, String lastName, String email, String batch) {
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, email, batch);
        repository.add(student);
        return student;
    }

    /**
     * Create a new student without email.
     *
     * @param firstName Student's first name
     * @param lastName Student's last name
     * @param batch Student's batch
     * @return The created student
     */
    public Student createStudent(String firstName, String lastName, String batch) {
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, batch);
        repository.add(student);
        return student;
    }

    /**
     * Deactivate a student by setting active = false.
     *
     * @param id The ID of the student to deactivate
     */
    public void deactivateStudent(int id) {
        repository.deactivate(id);
    }

    // Inherited methods from BaseService:
    // - getById(int id)
    // - getAll()
    // - update(T entity)
    // - exists(int id)
    // - getTotalCount()

    // Inherited methods from ActiveEntityService:
    // - getAllActive()
    // - getActiveCount()
}
