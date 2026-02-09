package com.demo.obfuscated;

/**
 * A rule strategy that activates for a percentage of requests on a target.
 */
public class RolloutRuleStrategy extends BaseRuleStrategy {
    private final int rolloutPercentage;

    public RolloutRuleStrategy(LegacyTargetCode target, int rolloutPercentage) {
        super(target);
        if (rolloutPercentage < 0 || rolloutPercentage > 100) {
            throw new IllegalArgumentException("Rollout percentage must be 0-100, got: " + rolloutPercentage);
        }
        this.rolloutPercentage = rolloutPercentage;
    }

    public int getRolloutPercentage() {
        return rolloutPercentage;
    }

    public boolean evaluate(int hashBucket) {
        return hashBucket >= 0 && hashBucket < rolloutPercentage;
    }
}
