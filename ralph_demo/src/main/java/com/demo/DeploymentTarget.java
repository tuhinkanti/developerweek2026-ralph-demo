package com.demo;

/**
 * Enum representing deployment targets across different environments and regions.
 * @deprecated Use {@link Target} and {@link TargetRepository} instead for dynamic target management.
 */
@Deprecated
public enum DeploymentTarget {
    PROD_US("production", "us", 1),
    PROD_EU("production", "eu", 2),
    PROD_APAC("production", "apac", 3),
    STAGING_US("staging", "us", 4),
    STAGING_EU("staging", "eu", 5),
    QA_US("qa", "us", 6),
    QA_EU("qa", "eu", 7),
    DEV_US("dev", "us", 8),
    DEV_EU("dev", "eu", 9),
    SANDBOX("sandbox", "global", 10);

    private final String environment;
    private final String region;
    private final int priority;

    DeploymentTarget(String environment, String region, int priority) {
        this.environment = environment;
        this.region = region;
        this.priority = priority;
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

    public boolean isProduction() {
        return "production".equals(environment);
    }

    public Target toTarget() {
        return Target.of(this.name(), environment, region, priority);
    }

    public static DeploymentTarget fromTarget(Target target) {
        if (target == null || target.getName() == null) return null;
        for (DeploymentTarget dt : values()) {
            if (dt.name().equalsIgnoreCase(target.getName())) {
                return dt;
            }
        }
        return null;
    }
}
