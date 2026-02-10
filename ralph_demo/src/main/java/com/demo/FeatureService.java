package com.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing feature flags and their deployment targets.
 */
public class FeatureService {
    private final List<Feature> features = new ArrayList<>();
    private final TargetRepository targetRepository;

    public FeatureService() {
        this(new InMemoryTargetRepository());
        // Seed repository with legacy targets
        for (DeploymentTarget dt : DeploymentTarget.values()) {
            targetRepository.save(dt.toTarget());
        }
    }

    public FeatureService(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    public void addFeature(Feature feature) {
        features.add(feature);
    }

    /**
     * @deprecated Use {@link #isEnabledFor(Feature, Target)} instead.
     */
    @Deprecated
    public boolean isEnabledFor(Feature feature, DeploymentTarget target) {
        if (feature == null || target == null) {
            return false;
        }
        return isEnabledFor(feature, target.toTarget());
    }

    public boolean isEnabledFor(Feature feature, Target target) {
        if (feature == null || target == null) {
            return false;
        }
        return feature.isEnabled() && target.equals(feature.getTargetEntity());
    }

    /**
     * @deprecated Use {@link #getTargetEntitiesForEnvironment(String)} instead.
     */
    @Deprecated
    public List<DeploymentTarget> getTargetsForEnvironment(String environment) {
        if (environment == null) {
            return List.of();
        }
        return Arrays.stream(DeploymentTarget.values())
                .filter(t -> environment.equals(t.getEnvironment()))
                .collect(Collectors.toList());
    }

    public List<Target> getTargetEntitiesForEnvironment(String environment) {
        if (environment == null) {
            return List.of();
        }
        return targetRepository.findByEnvironment(environment);
    }

    /**
     * @deprecated Use {@link #getFeaturesForTargetEntity(Target)} instead.
     */
    @Deprecated
    public List<Feature> getFeaturesForTarget(DeploymentTarget target) {
        if (target == null) return List.of();
        return getFeaturesForTargetEntity(target.toTarget());
    }

    public List<Feature> getFeaturesForTargetEntity(Target target) {
        return features.stream()
                .filter(f -> target.equals(f.getTargetEntity()))
                .collect(Collectors.toList());
    }

    public List<Feature> getProductionFeatures() {
        return features.stream()
                .filter(Feature::isProductionFeature)
                .collect(Collectors.toList());
    }
}
