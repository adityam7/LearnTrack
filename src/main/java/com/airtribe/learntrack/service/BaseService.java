package com.airtribe.learntrack.service;

import com.airtribe.learntrack.repository.BaseRepository;
import java.util.List;

/**
 * Generic abstract base service providing common CRUD operations.
 * Demonstrates use of generics and abstract classes to reduce code duplication.
 *
 * @param <T> The entity type this service manages
 * @param <R> The repository type that extends BaseRepository<T>
 */
public abstract class BaseService<T, R extends BaseRepository<T>> {
    protected final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    /**
     * Abstract method to get the entity type name for error messages.
     * Must be implemented by concrete service classes.
     *
     * @return The entity type name (e.g., "Student", "Course")
     */
    protected abstract String getEntityName();

    /**
     * Get an entity by its ID.
     *
     * @param id The ID to search for
     * @return The entity
     * @throws com.airtribe.learntrack.exception.EntityNotFoundException if entity not found
     */
    public T getById(int id) {
        return repository.getById(id);
    }

    /**
     * Get all entities.
     *
     * @return List of all entities
     */
    public List<T> getAll() {
        return repository.findAll();
    }

    /**
     * Update an existing entity.
     *
     * @param entity The entity with updated values
     */
    public void update(T entity) {
        repository.update(entity);
    }

    /**
     * Check if an entity with the given ID exists.
     *
     * @param id The ID to check
     * @return true if entity exists, false otherwise
     */
    public boolean exists(int id) {
        return repository.exists(id);
    }

    /**
     * Get the total count of entities.
     *
     * @return The number of entities
     */
    public int getTotalCount() {
        return repository.count();
    }
}