package com.demo;

/**
 * Represents a feature flag configuration with a deployment target.
 */
public class Feature {
    private final String name;
    private final String description;
    private final DeploymentTarget target;
    private final boolean enabled;

    public Feature(String name, String description, DeploymentTarget target, boolean enabled) {
        this.name = name;
        this.description = description;
        this.target = target;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public DeploymentTarget getTarget() {
        return target;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isProductionFeature() {
        return target != null && target.isProduction();
    }
}
