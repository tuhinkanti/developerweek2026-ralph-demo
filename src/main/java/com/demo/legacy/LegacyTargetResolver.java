package com.demo.legacy;

import com.demo.DeploymentTarget;

public class LegacyTargetResolver {
    public DeploymentTarget resolve(String environment, String region) {
        if (environment == null) {
            return null;
        }
        String normalizedEnvironment = normalizeEnvironment(environment);
        String normalizedRegion = normalizeRegion(region);

        for (DeploymentTarget target : DeploymentTarget.values()) {
            if (normalizedEnvironment.equals(target.getEnvironment())
                    && normalizedRegion.equals(target.getRegion())) {
                return target;
            }
        }
        for (DeploymentTarget target : DeploymentTarget.values()) {
            if (normalizedEnvironment.equals(target.getEnvironment())) {
                return target;
            }
        }
        return DeploymentTarget.SANDBOX;
    }

    private String normalizeEnvironment(String environment) {
        String env = environment.trim().toLowerCase();
        if (env.startsWith("prod")) {
            return "production";
        }
        if (env.startsWith("stag")) {
            return "staging";
        }
        if ("qa".equals(env) || env.startsWith("test")) {
            return "qa";
        }
        if ("dev".equals(env) || env.startsWith("dev")) {
            return "dev";
        }
        if ("sandbox".equals(env)) {
            return "sandbox";
        }
        return env;
    }

    private String normalizeRegion(String region) {
        if (region == null || region.trim().isEmpty()) {
            return "global";
        }
        String reg = region.trim().toLowerCase();
        if ("us-east".equals(reg) || "us-west".equals(reg) || "usa".equals(reg)) {
            return "us";
        }
        if ("eu-west".equals(reg) || "eu-central".equals(reg)) {
            return "eu";
        }
        if ("apac".equals(reg) || "asia".equals(reg)) {
            return "apac";
        }
        return reg;
    }
}
