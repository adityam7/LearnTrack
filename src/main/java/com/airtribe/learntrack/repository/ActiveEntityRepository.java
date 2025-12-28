package com.airtribe.learntrack.repository;

import java.util.List;

/**
 * Abstract repository class for entities with active/inactive status.
 * Extends BaseRepository and adds methods specific to entities with active status.
 *
 * @param <T> The entity type this repository manages (must have active status)
 */
public abstract class ActiveEntityRepository<T> extends BaseRepository<T> {

    /**
     * Abstract method to check if an entity is active.
     * Must be implemented by concrete repository classes.
     *
     * @param entity The entity to check
     * @return true if the entity is active, false otherwise
     */
    protected abstract boolean isActive(T entity);

    /**
     * Abstract method to set the active status of an entity.
     * Must be implemented by concrete repository classes.
     *
     * @param entity The entity to modify
     * @param active The new active status
     */
    protected abstract void setActive(T entity, boolean active);

    /**
     * Find all active entities.
     *
     * @return List of entities where active = true
     */
    public List<T> findAllActive() {
        return entities.stream()
                .filter(this::isActive)
                .toList();
    }

    /**
     * Count active entities only.
     *
     * @return The number of active entities
     */
    public int countActive() {
        return (int) entities.stream()
                .filter(this::isActive)
                .count();
    }

    /**
     * Activate an entity by setting active = true.
     *
     * @param id The ID of the entity to activate
     */
    public void activate(int id) {
        T entity = getById(id);
        setActive(entity, true);
    }

    /**
     * Deactivate an entity by setting active = false.
     *
     * @param id The ID of the entity to deactivate
     */
    public void deactivate(int id) {
        T entity = getById(id);
        setActive(entity, false);
    }
}