package com.example.featureflags.core.impl;

import com.example.featureflags.core.api.Environment;
import com.example.featureflags.core.api.EnvironmentRepository;
import com.example.featureflags.core.api.EnvironmentTarget;
import com.example.featureflags.core.api.ToggleBuilder;

public class ToggleBuilderImpl implements ToggleBuilder {
    private Environment environment;
    private EnvironmentRepository repository;

    public ToggleBuilderImpl() {
        this(new InMemoryEnvironmentRepository());
    }

    public ToggleBuilderImpl(EnvironmentRepository repository) {
        this.repository = repository;
    }

    @Override
    @Deprecated
    public ToggleBuilder withTarget(EnvironmentTarget target) {
        this.environment = target != null ? target.toEnvironment() : null;
        return this;
    }

    @Override
    public ToggleBuilder withEnvironment(Environment environment) {
        this.environment = environment;
        return this;
    }

    @Override
    public ToggleBuilder withEnvironment(String environmentName) {
        this.environment = repository.findByName(environmentName)
                .orElseThrow(() -> new IllegalArgumentException("Unknown environment: " + environmentName));
        return this;
    }

    public Environment getEnvironment() {
        return environment;
    }

    /**
     * @deprecated Use {@link #getEnvironment()} instead.
     */
    @Deprecated
    public EnvironmentTarget getTarget() {
        // This is a lossy conversion if we had a custom Environment, 
        // but for migration purposes we can try to find the matching enum.
        if (environment == null) return null;
        try {
            return EnvironmentTarget.valueOf(environment.getName());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
