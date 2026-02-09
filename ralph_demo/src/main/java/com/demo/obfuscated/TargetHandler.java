package com.demo.obfuscated;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility that routes actions based on target type and environment.
 */
public class TargetHandler {

    public String categorize(LegacyTargetCode target) {
        if (target == null) {
            return "unknown";
        }
        if (target == LegacyTargetCode.CORE_PROD
                || target == LegacyTargetCode.AUX_PROD
                || target == LegacyTargetCode.SUITE_ALPHA_PROD
                || target == LegacyTargetCode.SUITE_BETA_PROD
                || target == LegacyTargetCode.SUITE_GAMMA_PROD) {
            return "production";
        }
        if (target == LegacyTargetCode.CORE_TEST
                || target == LegacyTargetCode.AUX_TEST
                || target == LegacyTargetCode.SUITE_ALPHA_TEST
                || target == LegacyTargetCode.SUITE_GAMMA_TEST) {
            return "test";
        }
        if (target == LegacyTargetCode.CORE_EDGE
                || target == LegacyTargetCode.SUITE_GAMMA_EDGE) {
            return "edge";
        }
        if (target == LegacyTargetCode.SUITE_BETA_STAGE) {
            return "stage";
        }
        return "unknown";
    }

    public boolean requiresApproval(LegacyTargetCode target) {
        String category = categorize(target);
        return "production".equals(category) || "stage".equals(category);
    }

    public List<LegacyTargetCode> getProductionTargets() {
        List<LegacyTargetCode> results = new ArrayList<>();
        for (LegacyTargetCode target : LegacyTargetCode.values()) {
            if (target.isProduction()) {
                results.add(target);
            }
        }
        return results;
    }

    public List<LegacyTargetCode> getTargetsForTenant(String tenant) {
        List<LegacyTargetCode> results = new ArrayList<>();
        if (tenant == null) {
            return results;
        }
        for (LegacyTargetCode target : LegacyTargetCode.values()) {
            if (tenant.equals(target.getTenant())) {
                results.add(target);
            }
        }
        return results;
    }
}
