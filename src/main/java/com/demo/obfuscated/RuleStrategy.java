package com.demo.obfuscated;

/**
 * A rule strategy defines how a rule is evaluated against a specific target.
 */
public interface RuleStrategy {
    @Deprecated
    LegacyTargetCode getTarget();

    default TargetKey getTargetKey() {
        return TargetConverter.fromLegacy(getTarget());
    }

    default String getTargetValue() {
        LegacyTargetCode target = getTarget();
        return target != null ? target.getValue() : null;
    }
}
