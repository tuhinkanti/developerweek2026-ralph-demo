package com.example.featureflags.core.api;

/**
 * @deprecated Use {@link Environment} instead.
 */
@Deprecated
public enum EnvironmentTarget {
    PRODUCTION("production", "service-a"),
    STAGING("staging", "service-a"),
    DEV("dev", "service-a"),
    SERVICE_B_PROD("service-b", "default", "production", "env-1", "service-b-online");

    private final String value;
    private final String scope;
    private final String account;
    private final String project;
    private final String environmentId;

    EnvironmentTarget(String value, String scope) {
        this(null, null, value, null, scope);
    }

    EnvironmentTarget(String account, String project, String value, String environmentId, String scope) {
        this.value = value;
        this.scope = scope;
        this.account = account;
        this.project = project;
        this.environmentId = environmentId;
    }

    public String getValue() {
        return value;
    }

    public String getScope() {
        return scope;
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

    public Environment toEnvironment() {
        return Environment.of(this.name());
    }
    
    public static EnvironmentTarget fromAccountProjectAndValue(String account, String project, String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        for (EnvironmentTarget target : values()) {
            if (value.equals(target.getValue()) && 
                ((project == null && target.getProject() == null) || (project != null && project.equals(target.getProject()))) &&
                ((account == null && target.getAccount() == null) || (account != null && account.equals(target.getAccount())))) {
                return target;
            }
        }
        throw new IllegalArgumentException("Unknown target for account=" + account + ", project=" + project + ", value=" + value);
    }
}
