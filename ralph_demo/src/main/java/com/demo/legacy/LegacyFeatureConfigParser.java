package com.demo.legacy;

import java.util.ArrayList;
import java.util.List;

public class LegacyFeatureConfigParser {
    public List<LegacyFeatureFlag> parse(String rawConfig) {
        List<LegacyFeatureFlag> result = new ArrayList<>();
        if (rawConfig == null || rawConfig.isEmpty()) {
            return result;
        }
        String[] lines = rawConfig.split("\\r?\\n");
        for (String line : lines) {
            if (line == null) {
                continue;
            }
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }
            String[] parts = trimmed.split("\\|", -1);
            LegacyFeatureFlag flag = new LegacyFeatureFlag();
            if (parts.length > 0) {
                flag.setName(parts[0].trim());
            }
            if (parts.length > 1) {
                flag.setDescription(parts[1].trim());
            }
            if (parts.length > 2) {
                flag.setEnvironment(parts[2].trim());
            }
            if (parts.length > 3) {
                flag.setRegion(parts[3].trim());
            }
            if (parts.length > 4) {
                flag.setEnabledFlag(parts[4].trim());
            }
            if (parts.length > 5) {
                flag.setOwner(parts[5].trim());
            }
            result.add(flag);
        }
        return result;
    }
}
