package com.demo.obfuscated;

/**
 * Serializes rule strategies to a semicolon-delimited string format.
 */
public class RuleStrategySerializer {

    public String serialize(RuleStrategy strategy) {
        if (strategy == null) {
            return "";
        }
        LegacyTargetCode target = strategy.getTarget();
        StringBuilder sb = new StringBuilder();
        sb.append("target=").append(target != null ? target.name() : "");
        sb.append(";value=").append(target != null ? target.getValue() : "");
        sb.append(";tenant=").append(target != null ? target.getTenant() : "");
        if (strategy instanceof ThresholdRuleStrategy) {
            sb.append(";threshold=").append(((ThresholdRuleStrategy) strategy).getThreshold());
        }
        if (strategy instanceof ExpressionRuleStrategy) {
            sb.append(";expression=").append(((ExpressionRuleStrategy) strategy).getExpression());
        }
        if (strategy instanceof RolloutRuleStrategy) {
            sb.append(";rolloutPct=").append(((RolloutRuleStrategy) strategy).getRolloutPercentage());
        }
        return sb.toString();
    }
}
