package com.example.featureflags.core.api;

import java.util.Objects;

/**
 * Represents a deployment environment or target for feature flags.
 * This replaces the legacy {@link EnvironmentTarget} enum to allow for
 * dynamic environment definitions.
 */
public final class Environment {
    private final String name;

    private Environment(String name) {
        this.name = Objects.requireNonNull(name, "Environment name cannot be null");
    }

    public static Environment of(String name) {
        return new Environment(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Environment that = (Environment) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

    // Static constants for well-known environments to ease migration
    public static final Environment PRODUCTION = Environment.of("PRODUCTION");
    public static final Environment STAGING = Environment.of("STAGING");
}
