package com.demo.obfuscated;

/**
 * Enum representing all known deployment target codes across tenants.
 *
 * Core targets use 2-parameter form (value, tenant).
 * Suite targets use 5-parameter form (account, project, value, environmentId, tenant).
 */
public enum LegacyTargetCode {
    CORE_PROD("prod", "tenant-main"),
    CORE_TEST("test", "tenant-main"),
    CORE_EDGE("edge", "tenant-main"),
    AUX_PROD("prod", "tenant-aux"),
    AUX_TEST("test", "tenant-aux"),
    SUITE_ALPHA_PROD("suite-alpha", "default", "prod", "env-100", "tenant-suite-a"),
    SUITE_ALPHA_TEST("suite-alpha", "default", "test", "env-101", "tenant-suite-a"),
    SUITE_BETA_PROD("suite-beta", "blue", "prod", "env-200", "tenant-suite-b"),
    SUITE_BETA_STAGE("suite-beta", "blue", "stage", "env-201", "tenant-suite-b"),
    SUITE_GAMMA_PROD("suite-gamma", "green", "prod", "env-300", "tenant-suite-c"),
    SUITE_GAMMA_TEST("suite-gamma", "green", "test", "env-301", "tenant-suite-c"),
    SUITE_GAMMA_EDGE("suite-gamma", "green", "edge", "env-302", "tenant-suite-c");

    private final String value;
    private final String account;
    private final String project;
    private final String environmentId;
    private final String tenant;

    LegacyTargetCode(String value, String tenant) {
        this(null, null, value, null, tenant);
    }

    LegacyTargetCode(String account, String project, String value, String environmentId, String tenant) {
        this.account = account;
        this.project = project;
        this.value = value;
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

    public boolean isProduction() {
        return "prod".equals(value);
    }

    public TargetKey toTargetKey() {
        return new TargetKey(value, account, project, environmentId, tenant);
    }

    public static LegacyTargetCode fromTargetKey(TargetKey key) {
        if (key == null) return null;
        for (LegacyTargetCode code : values()) {
            if (code.toTargetKey().equals(key)) {
                return code;
            }
        }
        throw new IllegalArgumentException("Unknown TargetKey: " + key);
    }

    public static LegacyTargetCode fromValue(String value) {
        for (LegacyTargetCode code : values()) {
            if (code.value.equals(value)) {
                return code;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    public static LegacyTargetCode fromTenantAndValue(String tenant, String value) {
        for (LegacyTargetCode code : values()) {
            if (java.util.Objects.equals(code.tenant, tenant) && java.util.Objects.equals(code.value, value)) {
                return code;
            }
        }
        throw new IllegalArgumentException("Unknown tenant/value: " + tenant + "/" + value);
    }

    public static LegacyTargetCode fromAccountProjectAndValue(String account, String project, String value) {
        for (LegacyTargetCode code : values()) {
            if (java.util.Objects.equals(code.account, account)
                    && java.util.Objects.equals(code.project, project)
                    && java.util.Objects.equals(code.value, value)) {
                return code;
            }
        }
        throw new IllegalArgumentException("Unknown account/project/value: " + account + "/" + project + "/" + value);
    }
}
