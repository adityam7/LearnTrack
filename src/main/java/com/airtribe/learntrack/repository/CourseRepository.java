package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;
import java.util.List;

/**
 * Repository for Course entities.
 * Extends ActiveEntityRepository to inherit common CRUD operations and active/inactive management.
 * Demonstrates inheritance and specialization.
 */
public class CourseRepository extends ActiveEntityRepository<Course> {

    @Override
    protected int getId(Course course) {
        return course.getId();
    }

    @Override
    protected String getEntityName() {
        return "Course";
    }

    @Override
    protected boolean isActive(Course course) {
        return course.isActive();
    }

    @Override
    protected void setActive(Course course, boolean active) {
        course.setActive(active);
    }

    /**
     * Find courses by name (partial match, case-insensitive).
     *
     * @param namePattern The name pattern to search for
     * @return List of courses with matching names
     */
    public List<Course> findByNameContaining(String namePattern) {
        return entities.stream()
                .filter(course -> course.getCourseName()
                        .toLowerCase()
                        .contains(namePattern.toLowerCase()))
                .toList();
    }

    /**
     * Find courses by duration range.
     *
     * @param minWeeks Minimum duration in weeks
     * @param maxWeeks Maximum duration in weeks
     * @return List of courses within the duration range
     */
    public List<Course> findByDurationRange(int minWeeks, int maxWeeks) {
        return entities.stream()
                .filter(course -> course.getDurationInWeeks() >= minWeeks
                        && course.getDurationInWeeks() <= maxWeeks)
                .toList();
    }
}