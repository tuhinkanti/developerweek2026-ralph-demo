package com.demo.legacy;

import java.util.ArrayList;
import java.util.List;

public class LegacyFeatureStore {
    private static final LegacyFeatureStore INSTANCE = new LegacyFeatureStore();

    private final List<LegacyFeatureFlag> flags = new ArrayList<>();
    private final LegacyFeatureConfigParser parser = new LegacyFeatureConfigParser();

    private LegacyFeatureStore() {
        loadDefaults();
    }

    public static LegacyFeatureStore getInstance() {
        return INSTANCE;
    }

    public synchronized void add(LegacyFeatureFlag flag) {
        if (flag != null) {
            flags.add(flag);
        }
    }

    public synchronized void addAll(List<LegacyFeatureFlag> additionalFlags) {
        if (additionalFlags == null) {
            return;
        }
        for (LegacyFeatureFlag flag : additionalFlags) {
            if (flag != null) {
                flags.add(flag);
            }
        }
    }

    public synchronized List<LegacyFeatureFlag> listAll() {
        return new ArrayList<>(flags);
    }

    public synchronized LegacyFeatureFlag findByName(String name) {
        if (name == null) {
            return null;
        }
        for (LegacyFeatureFlag flag : flags) {
            if (flag != null && name.equalsIgnoreCase(flag.getName())) {
                return flag;
            }
        }
        return null;
    }

    public synchronized LegacyFeatureFlag findByNameAndEnvironment(String name, String environment) {
        if (name == null || environment == null) {
            return null;
        }
        for (LegacyFeatureFlag flag : flags) {
            if (flag == null) {
                continue;
            }
            if (name.equalsIgnoreCase(flag.getName())
                    && environment.equalsIgnoreCase(flag.getEnvironment())) {
                return flag;
            }
        }
        return null;
    }

    private void loadDefaults() {
        String rawConfig = String.join("\n",
                "dark-mode|Enable dark mode UI|production|us|true|ui-team",
                "legacy-search|Old search backend|production|eu|false|search-team",
                "beta-checkout|Checkout rewrite|staging|us|true|payments-team",
                "regional-banner|Localized banner|qa|eu|yes|marketing-team",
                "sandbox-tools|Internal sandbox tools|sandbox|global|1|devexp-team"
        );
        addAll(parser.parse(rawConfig));
    }
}
