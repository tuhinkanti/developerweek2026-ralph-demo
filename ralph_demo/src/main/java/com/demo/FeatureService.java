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

    public void addFeature(Feature feature) {
        features.add(feature);
    }

    public boolean isEnabledFor(Feature feature, DeploymentTarget target) {
        if (feature == null || target == null) {
            return false;
        }
        return feature.isEnabled() && feature.getTarget() == target;
    }

    public List<DeploymentTarget> getTargetsForEnvironment(String environment) {
        if (environment == null) {
            return List.of();
        }
        return Arrays.stream(DeploymentTarget.values())
                .filter(t -> environment.equals(t.getEnvironment()))
                .collect(Collectors.toList());
    }

    public List<Feature> getFeaturesForTarget(DeploymentTarget target) {
        return features.stream()
                .filter(f -> f.getTarget() == target)
                .collect(Collectors.toList());
    }

    public List<Feature> getProductionFeatures() {
        return features.stream()
                .filter(Feature::isProductionFeature)
                .collect(Collectors.toList());
    }
}
