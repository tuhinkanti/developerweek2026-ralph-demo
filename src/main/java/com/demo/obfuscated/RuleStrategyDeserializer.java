package com.demo.obfuscated;

import java.util.HashMap;
import java.util.Map;

/**
 * Deserializes semicolon-delimited strings back into RuleStrategy instances.
 */
public class RuleStrategyDeserializer {

    public RuleStrategy deserialize(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return null;
        }
        Map<String, String> fields = parseFields(raw);
        
        TargetKey targetKey = resolveTargetKey(fields);
        LegacyTargetCode legacyTarget = resolveTarget(fields.get("target"));
        
        if (targetKey == null && legacyTarget == null) {
            return null;
        }

        if (fields.containsKey("expression")) {
            String expr = fields.get("expression");
            return (targetKey != null) ? new ExpressionRuleStrategy(targetKey, expr) : new ExpressionRuleStrategy(legacyTarget, expr);
        }
        if (fields.containsKey("rolloutPct")) {
            int pct = parseIntOrZero(fields.get("rolloutPct"));
            return (targetKey != null) ? new RolloutRuleStrategy(targetKey, pct) : new RolloutRuleStrategy(legacyTarget, pct);
        }
        int threshold = parseIntOrZero(fields.get("threshold"));
        return (targetKey != null) ? new ThresholdRuleStrategy(targetKey, threshold) : new ThresholdRuleStrategy(legacyTarget, threshold);
    }

    private TargetKey resolveTargetKey(Map<String, String> fields) {
        String value = nullIfEmpty(fields.get("value"));
        String tenant = nullIfEmpty(fields.get("tenant"));
        if (value == null && tenant == null) {
            return null;
        }
        
        String account = nullIfEmpty(fields.get("account"));
        String project = nullIfEmpty(fields.get("project"));
        String environmentId = nullIfEmpty(fields.get("environmentId"));
        
        return new TargetKey(value, account, project, environmentId, tenant);
    }

    private String nullIfEmpty(String s) {
        return (s == null || s.isEmpty()) ? null : s;
    }

    private LegacyTargetCode resolveTarget(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        try {
            return LegacyTargetCode.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Map<String, String> parseFields(String raw) {
        Map<String, String> fields = new HashMap<>();
        String[] parts = raw.split(";");
        for (String part : parts) {
            if (part == null || part.trim().isEmpty()) {
                continue;
            }
            String[] pair = part.split("=", 2);
            String key = pair[0].trim();
            String val = pair.length > 1 ? pair[1].trim() : "";
            fields.put(key, val);
        }
        return fields;
    }

    private int parseIntOrZero(String raw) {
        if (raw == null || raw.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
