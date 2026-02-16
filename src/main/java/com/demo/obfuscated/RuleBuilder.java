package com.demo.obfuscated;

/**
 * Builder for constructing RuleStrategy instances.
 */
public interface RuleBuilder {
    @Deprecated
    RuleBuilder withTarget(LegacyTargetCode target);

    default RuleBuilder withTargetKey(TargetKey targetKey) {
        return withTarget(TargetConverter.toLegacy(targetKey));
    }

    RuleBuilder withThreshold(int threshold);

    RuleStrategy build();
}
