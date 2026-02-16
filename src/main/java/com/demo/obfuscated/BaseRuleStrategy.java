package com.demo.obfuscated;

/**
 * Base implementation of RuleStrategy that holds a target enum reference.
 */
public abstract class BaseRuleStrategy implements RuleStrategy {
    private final LegacyTargetCode target;
    private final TargetKey targetKey;

    protected BaseRuleStrategy(LegacyTargetCode target) {
        this.target = target;
        this.targetKey = TargetConverter.fromLegacy(target);
    }

    protected BaseRuleStrategy(TargetKey targetKey) {
        this.targetKey = targetKey;
        LegacyTargetCode resolved;
        try {
            resolved = TargetConverter.toLegacy(targetKey);
        } catch (IllegalArgumentException e) {
            resolved = null;
        }
        this.target = resolved;
    }

    @Override
    public LegacyTargetCode getTarget() {
        return target;
    }

    @Override
    public TargetKey getTargetKey() {
        return targetKey;
    }

    @Override
    public String getTargetValue() {
        return targetKey != null ? targetKey.getValue() : null;
    }
}
