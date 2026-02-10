package com.demo.legacy;

public class LegacyFeatureFlag {
    private String name;
    private String description;
    private String environment;
    private String region;
    private String owner;
    private String enabledFlag;

    public LegacyFeatureFlag() {
    }

    public LegacyFeatureFlag(String name, String description, String environment, String region, String enabledFlag, String owner) {
        this.name = name;
        this.description = description;
        this.environment = environment;
        this.region = region;
        this.enabledFlag = enabledFlag;
        this.owner = owner;
    }

    public boolean isEnabled() {
        if (enabledFlag == null) {
            return false;
        }
        String value = enabledFlag.trim().toLowerCase();
        return "true".equals(value) || "1".equals(value) || "yes".equals(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }
}
