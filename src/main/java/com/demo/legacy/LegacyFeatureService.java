package com.demo.legacy;

import com.demo.DeploymentTarget;
import com.demo.Feature;

public class LegacyFeatureService {
    private final LegacyFeatureStore store;
    private final LegacyTargetResolver resolver;

    public LegacyFeatureService() {
        this(LegacyFeatureStore.getInstance(), new LegacyTargetResolver());
    }

    public LegacyFeatureService(LegacyFeatureStore store, LegacyTargetResolver resolver) {
        this.store = store;
        this.resolver = resolver;
    }

    @Deprecated
    public boolean isEnabled(String featureName, String environment, String region) {
        LegacyFeatureFlag flag = store.findByNameAndEnvironment(featureName, environment);
        if (flag == null || !flag.isEnabled()) {
            return false;
        }
        DeploymentTarget target = resolver.resolve(environment, region);
        return matchesTarget(flag, target);
    }

    public Feature toFeature(String featureName, String environment, String region) {
        LegacyFeatureFlag flag = store.findByNameAndEnvironment(featureName, environment);
        if (flag == null) {
            return null;
        }
        DeploymentTarget target = resolver.resolve(environment, region);
        return new Feature(flag.getName(), flag.getDescription(), target, flag.isEnabled());
    }

    private boolean matchesTarget(LegacyFeatureFlag flag, DeploymentTarget target) {
        if (flag == null || target == null) {
            return false;
        }
        String env = flag.getEnvironment();
        if (env == null || !env.equalsIgnoreCase(target.getEnvironment())) {
            return false;
        }
        String region = flag.getRegion();
        if (region == null || region.isEmpty()) {
            return true;
        }
        if ("global".equalsIgnoreCase(region)) {
            return true;
        }
        return region.equalsIgnoreCase(target.getRegion());
    }
}
