package com.example.featureflags.core.api;

import java.util.Optional;

/**
 * Repository for managing and looking up {@link Environment} instances.
 */
public interface EnvironmentRepository {
    /**
     * Finds an environment by its unique name.
     * @param name the name of the environment
     * @return an Optional containing the environment if found, or empty otherwise
     */
    Optional<Environment> findByName(String name);

    /**
     * Registers a new environment in the repository.
     * @param environment the environment to register
     */
    void register(Environment environment);
}
