package com.airtribe.learntrack.service;

import com.airtribe.learntrack.repository.ActiveEntityRepository;
import java.util.List;

/**
 * Abstract service class for entities with active/inactive status.
 * Extends BaseService and adds methods specific to entities with active status.
 *
 * @param <T> The entity type this service manages (must have active status)
 * @param <R> The repository type that extends ActiveEntityRepository<T>
 */
public abstract class ActiveEntityService<T, R extends ActiveEntityRepository<T>>
        extends BaseService<T, R> {

    public ActiveEntityService(R repository) {
        super(repository);
    }

    /**
     * Get all active entities.
     *
     * @return List of entities where active = true
     */
    public List<T> getAllActive() {
        return repository.findAllActive();
    }

    /**
     * Get the count of active entities only.
     *
     * @return The number of active entities
     */
    public int getActiveCount() {
        return repository.countActive();
    }
}
