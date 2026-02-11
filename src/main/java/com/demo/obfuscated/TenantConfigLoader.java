package com.demo.obfuscated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads tenant configuration and resolves enum targets from tenant/value pairs.
 */
public class TenantConfigLoader {

    private final Map<String, List<LegacyTargetCode>> tenantTargets = new HashMap<>();

    public TenantConfigLoader() {
        indexTargets();
    }

    private void indexTargets() {
        for (LegacyTargetCode target : LegacyTargetCode.values()) {
            String tenant = target.getTenant();
            if (tenant != null) {
                tenantTargets.computeIfAbsent(tenant, k -> new ArrayList<>()).add(target);
            }
        }
    }

    public List<LegacyTargetCode> getTargetsForTenant(String tenant) {
        if (tenant == null) {
            return List.of();
        }
        List<LegacyTargetCode> targets = tenantTargets.get(tenant);
        return targets != null ? new ArrayList<>(targets) : List.of();
    }

    public LegacyTargetCode resolve(String tenant, String value) {
        if (tenant == null || value == null) {
            return null;
        }
        List<LegacyTargetCode> targets = tenantTargets.get(tenant);
        if (targets == null) {
            return null;
        }
        for (LegacyTargetCode target : targets) {
            if (value.equals(target.getValue())) {
                return target;
            }
        }
        return null;
    }

    public LegacyTargetCode resolveByAccountProjectValue(String account, String project, String value) {
        if (value == null) {
            return null;
        }
        for (LegacyTargetCode target : LegacyTargetCode.values()) {
            if (matches(target.getAccount(), account)
                    && matches(target.getProject(), project)
                    && value.equals(target.getValue())) {
                return target;
            }
        }
        return null;
    }

    private boolean matches(String actual, String expected) {
        if (actual == null && expected == null) {
            return true;
        }
        if (actual == null || expected == null) {
            return false;
        }
        return actual.equals(expected);
    }
}
