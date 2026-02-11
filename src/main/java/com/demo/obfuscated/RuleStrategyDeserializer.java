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
        LegacyTargetCode target = resolveTarget(fields.get("target"));
        if (target == null) {
            return null;
        }
        if (fields.containsKey("expression")) {
            return new ExpressionRuleStrategy(target, fields.get("expression"));
        }
        if (fields.containsKey("rolloutPct")) {
            int pct = parseIntOrZero(fields.get("rolloutPct"));
            return new RolloutRuleStrategy(target, pct);
        }
        int threshold = parseIntOrZero(fields.get("threshold"));
        return new ThresholdRuleStrategy(target, threshold);
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
