package com.demo.obfuscated;

/**
 * Default implementation of RuleBuilder.
 */
public class RuleBuilderImpl implements RuleBuilder {
    private LegacyTargetCode target;
    private TargetKey targetKey;
    private int threshold;

    @Override
    @Deprecated
    public RuleBuilder withTarget(LegacyTargetCode target) {
        this.target = target;
        this.targetKey = TargetConverter.fromLegacy(target);
        return this;
    }

    @Override
    public RuleBuilder withTargetKey(TargetKey targetKey) {
        this.targetKey = targetKey;
        try {
            this.target = TargetConverter.toLegacy(targetKey);
        } catch (IllegalArgumentException e) {
            this.target = null;
        }
        return this;
    }

    @Override
    public RuleBuilder withThreshold(int threshold) {
        this.threshold = threshold;
        return this;
    }

    @Override
    public RuleStrategy build() {
        if (targetKey != null) {
            return new ThresholdRuleStrategy(targetKey, threshold);
        }
        if (target == null) {
            throw new IllegalStateException("Target is required");
        }
        return new ThresholdRuleStrategy(target, threshold);
    }
}
