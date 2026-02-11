package com.demo.obfuscated;

/**
 * Base implementation of RuleStrategy that holds a target enum reference.
 */
public abstract class BaseRuleStrategy implements RuleStrategy {
    private final LegacyTargetCode target;

    protected BaseRuleStrategy(LegacyTargetCode target) {
        this.target = target;
    }

    @Override
    public LegacyTargetCode getTarget() {
        return target;
    }
}
