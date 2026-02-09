package com.demo.obfuscated;

/**
 * A rule strategy defines how a rule is evaluated against a specific target.
 */
public interface RuleStrategy {
    LegacyTargetCode getTarget();
}
