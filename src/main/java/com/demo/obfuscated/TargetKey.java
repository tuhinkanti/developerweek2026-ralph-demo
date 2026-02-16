package com.demo.obfuscated;

import java.util.Objects;

/**
 * Data class representing a target key, replacing the LegacyTargetCode enum.
 */
public final class TargetKey {
    private final String value;
    private final String account;
    private final String project;
    private final String environmentId;
    private final String tenant;

    public TargetKey(String value, String account, String project, String environmentId, String tenant) {
        this.value = value;
        this.account = account;
        this.project = project;
        this.environmentId = environmentId;
        this.tenant = tenant;
    }

    public String getValue() {
        return value;
    }

    public String getAccount() {
        return account;
    }

    public String getProject() {
        return project;
    }

    public String getEnvironmentId() {
        return environmentId;
    }

    public String getTenant() {
        return tenant;
    }

    public String getValueWithProject() {
        if (project != null && !project.isEmpty()) {
            return project + "__" + value;
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetKey that = (TargetKey) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(account, that.account) &&
                Objects.equals(project, that.project) &&
                Objects.equals(environmentId, that.environmentId) &&
                Objects.equals(tenant, that.tenant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, account, project, environmentId, tenant);
    }

    @Override
    public String toString() {
        return "TargetKey{value=" + value + ", account=" + account + ", project=" + project + ", environmentId=" + environmentId + ", tenant=" + tenant + "}";
    }
}
