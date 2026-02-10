package com.demo.obfuscated;

/**
 * Builder for constructing RuleStrategy instances.
 */
public interface RuleBuilder {
    RuleBuilder withTarget(LegacyTargetCode target);

    RuleBuilder withThreshold(int threshold);

    RuleStrategy build();
}
