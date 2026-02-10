package com.demo.obfuscated;

/**
 * Default implementation of RuleBuilder.
 */
public class RuleBuilderImpl implements RuleBuilder {
    private LegacyTargetCode target;
    private int threshold;

    @Override
    public RuleBuilder withTarget(LegacyTargetCode target) {
        this.target = target;
        return this;
    }

    @Override
    public RuleBuilder withThreshold(int threshold) {
        this.threshold = threshold;
        return this;
    }

    @Override
    public RuleStrategy build() {
        if (target == null) {
            throw new IllegalStateException("Target is required");
        }
        return new ThresholdRuleStrategy(target, threshold);
    }
}
