package com.demo;

import java.util.Objects;

/**
 * Data class representing a deployment target.
 */
public final class Target {
    private final String id;
    private final String name;
    private final String environment;
    private final String region;
    private final int priority;

    public Target(String id, String name, String environment, String region, int priority) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.environment = Objects.requireNonNull(environment, "environment cannot be null");
        this.region = Objects.requireNonNull(region, "region cannot be null");
        this.priority = priority;
    }

    public static Target of(String name, String environment, String region, int priority) {
        return new Target(name.toUpperCase(), name, environment, region, priority);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getRegion() {
        return region;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Target target = (Target) o;
        return Objects.equals(id, target.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Target{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", environment='" + environment + '\'' +
                ", region='" + region + '\'' +
                ", priority=" + priority +
                '}';
    }
}
