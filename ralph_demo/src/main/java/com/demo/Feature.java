package com.demo;

/**
 * Represents a feature flag configuration with a deployment target.
 */
public class Feature {
    private final String name;
    private final String description;
    private final DeploymentTarget target;
    private final Target targetEntity;
    private final boolean enabled;

    public Feature(String name, String description, DeploymentTarget target, boolean enabled) {
        this.name = name;
        this.description = description;
        this.target = target;
        this.targetEntity = target != null ? target.toTarget() : null;
        this.enabled = enabled;
    }

    public Feature(String name, String description, Target targetEntity, boolean enabled) {
        this.name = name;
        this.description = description;
        this.targetEntity = targetEntity;
        this.target = DeploymentTarget.fromTarget(targetEntity);
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @deprecated Use {@link #getTargetEntity()} instead.
     */
    @Deprecated
    public DeploymentTarget getTarget() {
        return target;
    }

    public Target getTargetEntity() {
        return targetEntity;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isProductionFeature() {
        return targetEntity != null && "production".equals(targetEntity.getEnvironment());
    }
}
