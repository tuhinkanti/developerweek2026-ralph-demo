package com.demo.obfuscated;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Utility that routes actions based on target type and environment.
 */
public class TargetHandler {

    public String categorize(TargetKey target) {
        if (target == null) {
            return "unknown";
        }
        String value = target.getValue();
        if ("prod".equals(value)) return "production";
        if ("test".equals(value)) return "test";
        if ("edge".equals(value)) return "edge";
        if ("stage".equals(value)) return "stage";
        return "unknown";
    }

    @Deprecated
    public String categorize(LegacyTargetCode target) {
        return categorize(target != null ? target.toTargetKey() : null);
    }

    public boolean requiresApproval(TargetKey target) {
        String category = categorize(target);
        return "production".equals(category) || "stage".equals(category);
    }

    @Deprecated
    public boolean requiresApproval(LegacyTargetCode target) {
        return requiresApproval(target != null ? target.toTargetKey() : null);
    }

    public List<TargetKey> getProductionTargets() {
        return Arrays.stream(LegacyTargetCode.values())
                .filter(LegacyTargetCode::isProduction)
                .map(LegacyTargetCode::toTargetKey)
                .collect(Collectors.toList());
    }

    public List<TargetKey> getTargetsForTenant(String tenant) {
        if (tenant == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(LegacyTargetCode.values())
                .filter(t -> tenant.equals(t.getTenant()))
                .map(LegacyTargetCode::toTargetKey)
                .collect(Collectors.toList());
    }
}
