package com.example.featureflags.core.impl;

import com.example.featureflags.core.api.Environment;
import com.example.featureflags.core.api.EnvironmentRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryEnvironmentRepository implements EnvironmentRepository {
    private final Map<String, Environment> environments = new ConcurrentHashMap<>();

    public InMemoryEnvironmentRepository() {
        // Register default environments
        register(Environment.PRODUCTION);
        register(Environment.STAGING);
    }

    @Override
    public Optional<Environment> findByName(String name) {
        if (name == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(environments.get(name));
    }

    @Override
    public void register(Environment environment) {
        environments.put(environment.getName(), environment);
    }
}
