package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;

/**
 * Service class for Course entities.
 * Extends ActiveEntityService to inherit common CRUD operations.
 * Demonstrates inheritance and specialization.
 */
public class CourseService extends ActiveEntityService<Course, CourseRepository> {

    public CourseService(CourseRepository courseRepository) {
        super(courseRepository);
    }

    @Override
    protected String getEntityName() {
        return "Course";
    }

    /**
     * Create a new course.
     *
     * @param courseName The course name
     * @param description The course description
     * @param durationInWeeks The course duration in weeks
     * @return The created course
     */
    public Course createCourse(String courseName, String description, int durationInWeeks) {
        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName, description, durationInWeeks);
        repository.add(course);
        return course;
    }

    /**
     * Activate a course by setting active = true.
     *
     * @param id The ID of the course to activate
     */
    public void activateCourse(int id) {
        repository.activate(id);
    }

    /**
     * Deactivate a course by setting active = false.
     *
     * @param id The ID of the course to deactivate
     */
    public void deactivateCourse(int id) {
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
