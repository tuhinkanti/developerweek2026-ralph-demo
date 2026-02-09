package com.demo.obfuscated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public boolean isEnabled(String toggleName, LegacyTargetCode target) {
        if (toggleName == null || target == null) {
            return false;
        }
        List<RuleStrategy> rules = toggleRules.get(toggleName);
        if (rules == null || rules.isEmpty()) {
            return false;
        }
        for (RuleStrategy rule : rules) {
            if (rule.getTarget() == target) {
                return evaluateRule(rule);
            }
        }
        return false;
    }

    public List<LegacyTargetCode> getTargetsForToggle(String toggleName) {
        List<LegacyTargetCode> targets = new ArrayList<>();
        List<RuleStrategy> rules = toggleRules.get(toggleName);
        if (rules == null) {
            return targets;
        }
        for (RuleStrategy rule : rules) {
            if (rule.getTarget() != null && !targets.contains(rule.getTarget())) {
                targets.add(rule.getTarget());
            }
        }
        return targets;
    }

    public List<LegacyTargetCode> getProductionTargetsForToggle(String toggleName) {
        List<LegacyTargetCode> targets = new ArrayList<>();
        List<RuleStrategy> rules = toggleRules.get(toggleName);
        if (rules == null) {
            return targets;
        }
        for (RuleStrategy rule : rules) {
            LegacyTargetCode t = rule.getTarget();
            if (t != null && t.isProduction() && !targets.contains(t)) {
                targets.add(t);
            }
        }
        return targets;
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
