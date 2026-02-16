package com.demo.obfuscated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Adapter that manages feature toggle rules and evaluates them by target.
 */
public class FeatureToggleAdapter {

    private final Map<String, List<RuleStrategy>> toggleRules = new HashMap<>();

    public void register(String toggleName, RuleStrategy strategy) {
        if (toggleName == null || strategy == null) {
            return;
        }
        toggleRules.computeIfAbsent(toggleName, k -> new ArrayList<>()).add(strategy);
    }

    public boolean isEnabled(String toggleName, TargetKey target) {
        if (toggleName == null || target == null) {
            return false;
        }
        List<RuleStrategy> rules = toggleRules.get(toggleName);
        if (rules == null || rules.isEmpty()) {
            return false;
        }
        for (RuleStrategy rule : rules) {
            if (target.equals(rule.getTargetKey())) {
                return evaluateRule(rule);
            }
        }
        return false;
    }

    @Deprecated
    public boolean isEnabled(String toggleName, LegacyTargetCode target) {
        return isEnabled(toggleName, target != null ? target.toTargetKey() : null);
    }

    public List<TargetKey> getTargetsForToggle(String toggleName) {
        List<RuleStrategy> rules = toggleRules.get(toggleName);
        if (rules == null) {
            return new ArrayList<>();
        }
        return rules.stream()
                .map(RuleStrategy::getTargetKey)
                .filter(java.util.Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    @Deprecated
    public List<LegacyTargetCode> getLegacyTargetsForToggle(String toggleName) {
        return getTargetsForToggle(toggleName).stream()
                .map(TargetConverter::toLegacy)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<TargetKey> getProductionTargetsForToggle(String toggleName) {
        return getTargetsForToggle(toggleName).stream()
                .filter(t -> "prod".equals(t.getValue()))
                .collect(Collectors.toList());
    }

    private boolean evaluateRule(RuleStrategy rule) {
        if (rule instanceof ExpressionRuleStrategy) {
            return ((ExpressionRuleStrategy) rule).evaluate();
        }
        if (rule instanceof ThresholdRuleStrategy) {
            return ((ThresholdRuleStrategy) rule).evaluate(50);
        }
        if (rule instanceof RolloutRuleStrategy) {
            return ((RolloutRuleStrategy) rule).evaluate(25);
        }
        return false;
    }
}
