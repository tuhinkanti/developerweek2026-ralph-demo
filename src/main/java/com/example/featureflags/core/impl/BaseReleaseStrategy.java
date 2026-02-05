package com.example.featureflags.core.impl;

import com.example.featureflags.core.api.ReleaseStrategy;
import com.example.featureflags.core.api.Environment;
import com.example.featureflags.core.api.EnvironmentTarget;

public class BaseReleaseStrategy implements ReleaseStrategy {
    private final Environment environment;

    @Deprecated
    public BaseReleaseStrategy(EnvironmentTarget target) {
        this.environment = target != null ? target.toEnvironment() : null;
    }

    public BaseReleaseStrategy(Environment environment) {
        this.environment = environment;
    }

    @Override
    @Deprecated
    public EnvironmentTarget getTarget() {
        if (environment == null) return null;
        try {
            return EnvironmentTarget.valueOf(environment.getName());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }
}
