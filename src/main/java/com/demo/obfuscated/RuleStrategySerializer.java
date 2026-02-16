package com.demo.obfuscated;

/**
 * Serializes rule strategies to a semicolon-delimited string format.
 */
public class RuleStrategySerializer {

    public String serialize(RuleStrategy strategy) {
        if (strategy == null) {
            return "";
        }
        TargetKey key = strategy.getTargetKey();
        LegacyTargetCode legacy = strategy.getTarget();
        StringBuilder sb = new StringBuilder();
        
        // target name for backward compatibility
        sb.append("target=").append(legacy != null ? legacy.name() : "");
        
        if (key != null) {
            sb.append(";value=").append(key.getValue() != null ? key.getValue() : "");
            sb.append(";tenant=").append(key.getTenant() != null ? key.getTenant() : "");
            sb.append(";account=").append(key.getAccount() != null ? key.getAccount() : "");
            sb.append(";project=").append(key.getProject() != null ? key.getProject() : "");
            sb.append(";environmentId=").append(key.getEnvironmentId() != null ? key.getEnvironmentId() : "");
        } else if (legacy != null) {
            sb.append(";value=").append(legacy.getValue() != null ? legacy.getValue() : "");
            sb.append(";tenant=").append(legacy.getTenant() != null ? legacy.getTenant() : "");
        }

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
