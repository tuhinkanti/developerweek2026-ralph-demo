package com.demo;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Target entities.
 */
public interface TargetRepository {
    Optional<Target> findByName(String name);
    List<Target> findByEnvironment(String environment);
    List<Target> findAll();
    void save(Target target);
}
