package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Generic abstract base repository providing common CRUD operations.
 * Demonstrates use of generics and abstract classes to reduce code duplication.
 *
 * @param <T> The entity type this repository manages
 */
public abstract class BaseRepository<T> {
    protected final List<T> entities;

    public BaseRepository() {
        this.entities = new ArrayList<>();
    }

    /**
     * Abstract method to extract ID from an entity.
     * Must be implemented by concrete repository classes.
     *
     * @param entity The entity to get ID from
     * @return The entity's ID
     */
    protected abstract int getId(T entity);

    /**
     * Abstract method to get the entity type name for error messages.
     *
     * @return The entity type name (e.g., "Student", "Course")
     */
    protected abstract String getEntityName();

    /**
     * Add a new entity to the repository.
     *
     * @param entity The entity to add
     * @throws IllegalArgumentException if entity is null
     */
    public void add(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException(getEntityName() + " cannot be null");
        }
        entities.add(entity);
    }

    /**
     * Find an entity by its ID.
     *
     * @param id The ID to search for
     * @return Optional containing the entity if found, empty otherwise
     */
    public Optional<T> findById(int id) {
        return entities.stream()
                .filter(entity -> getId(entity) == id)
                .findFirst();
    }

    /**
     * Get an entity by ID, throwing exception if not found.
     *
     * @param id The ID to search for
     * @return The entity
     * @throws EntityNotFoundException if entity not found
     */
    public T getById(int id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(getEntityName(), id));
    }

    /**
     * Get all entities in the repository.
     *
     * @return A new list containing all entities
     */
    public List<T> findAll() {
        return new ArrayList<>(entities);
    }

    /**
     * Update an existing entity.
     *
     * @param entity The entity with updated values
     * @throws IllegalArgumentException if entity is null
     * @throws EntityNotFoundException if entity doesn't exist
     */
    public void update(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException(getEntityName() + " cannot be null");
        }

        T existing = getById(getId(entity));
        int index = entities.indexOf(existing);
        entities.set(index, entity);
    }

    /**
     * Check if an entity with the given ID exists.
     *
     * @param id The ID to check
     * @return true if entity exists, false otherwise
     */
    public boolean exists(int id) {
        return findById(id).isPresent();
    }

    /**
     * Get the total count of entities.
     *
     * @return The number of entities in the repository
     */
    public int count() {
        return entities.size();
    }

    /**
     * Delete an entity by ID.
     *
     * @param id The ID of the entity to delete
     * @return true if entity was deleted, false if not found
     */
    public boolean deleteById(int id) {
        return entities.removeIf(entity -> getId(entity) == id);
    }

    /**
     * Clear all entities from the repository.
     */
    public void clear() {
        entities.clear();
    }
}
