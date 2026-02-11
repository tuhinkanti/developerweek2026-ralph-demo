package com.demo.obfuscated;

/**
 * A rule strategy that evaluates a boolean expression string against a target.
 */
public class ExpressionRuleStrategy extends BaseRuleStrategy {
    private final String expression;

    public ExpressionRuleStrategy(LegacyTargetCode target, String expression) {
        super(target);
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public boolean evaluate() {
        if (expression == null || expression.trim().isEmpty()) {
            return false;
        }
        if ("true".equalsIgnoreCase(expression.trim())) {
            return true;
        }
        if ("false".equalsIgnoreCase(expression.trim())) {
            return false;
        }
        return getTarget() != null && getTarget().isProduction();
    }
}
