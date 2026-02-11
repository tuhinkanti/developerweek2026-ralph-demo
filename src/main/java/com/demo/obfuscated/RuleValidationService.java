package com.demo.obfuscated;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates rule strategies against business constraints.
 */
public class RuleValidationService {

    public List<String> validate(RuleStrategy strategy) {
        List<String> errors = new ArrayList<>();
        if (strategy == null) {
            errors.add("Strategy must not be null");
            return errors;
        }
        LegacyTargetCode target = strategy.getTarget();
        if (target == null) {
            errors.add("Target must not be null");
            return errors;
        }

        if (strategy instanceof ThresholdRuleStrategy) {
            validateThreshold((ThresholdRuleStrategy) strategy, target, errors);
        }
        if (strategy instanceof RolloutRuleStrategy) {
            validateRollout((RolloutRuleStrategy) strategy, target, errors);
        }
        if (strategy instanceof ExpressionRuleStrategy) {
            validateExpression((ExpressionRuleStrategy) strategy, target, errors);
        }

        return errors;
    }

    private void validateThreshold(ThresholdRuleStrategy strategy, LegacyTargetCode target, List<String> errors) {
        if (strategy.getThreshold() < 0) {
            errors.add("Threshold must be non-negative");
        }
        if (target == LegacyTargetCode.CORE_PROD && strategy.getThreshold() > 1000) {
            errors.add("Production threshold cannot exceed 1000");
        }
        if (target == LegacyTargetCode.CORE_TEST && strategy.getThreshold() > 5000) {
            errors.add("Test threshold cannot exceed 5000");
        }
    }

    private void validateRollout(RolloutRuleStrategy strategy, LegacyTargetCode target, List<String> errors) {
        if (target == LegacyTargetCode.CORE_PROD && strategy.getRolloutPercentage() > 50) {
            errors.add("Production rollout cannot exceed 50%");
        }
    }

    private void validateExpression(ExpressionRuleStrategy strategy, LegacyTargetCode target, List<String> errors) {
        if (strategy.getExpression() == null || strategy.getExpression().trim().isEmpty()) {
            errors.add("Expression must not be empty");
        }
        if (target.isProduction() && "true".equalsIgnoreCase(strategy.getExpression())) {
            errors.add("Unconditional enable is not allowed for production targets");
        }
    }
}
