package com.demo.obfuscated;

/**
 * A rule strategy that activates when a metric exceeds a threshold for a given target.
 */
public class ThresholdRuleStrategy extends BaseRuleStrategy {
    private final int threshold;

    public ThresholdRuleStrategy(LegacyTargetCode target, int threshold) {
        super(target);
        this.threshold = threshold;
    }

    public ThresholdRuleStrategy(TargetKey targetKey, int threshold) {
        super(targetKey);
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }

    public boolean evaluate(int currentValue) {
        return currentValue >= threshold;
    }
}
