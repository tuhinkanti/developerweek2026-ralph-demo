package com.demo.obfuscated;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionRuleStrategyTest {

    @Test
    void evaluateTrueExpression() {
        ExpressionRuleStrategy strategy = new ExpressionRuleStrategy(LegacyTargetCode.CORE_TEST, "true");
        assertTrue(strategy.evaluate());
    }

    @Test
    void evaluateFalseExpression() {
        ExpressionRuleStrategy strategy = new ExpressionRuleStrategy(LegacyTargetCode.CORE_TEST, "false");
        assertFalse(strategy.evaluate());
    }

    @Test
    void evaluateNullExpression() {
        ExpressionRuleStrategy strategy = new ExpressionRuleStrategy(LegacyTargetCode.CORE_TEST, null);
        assertFalse(strategy.evaluate());
    }

    @Test
    void evaluateArbitraryExpressionOnProdTarget() {
        ExpressionRuleStrategy strategy = new ExpressionRuleStrategy(LegacyTargetCode.CORE_PROD, "some_condition");
        assertTrue(strategy.evaluate());
    }

    @Test
    void evaluateArbitraryExpressionOnNonProdTarget() {
        ExpressionRuleStrategy strategy = new ExpressionRuleStrategy(LegacyTargetCode.CORE_TEST, "some_condition");
        assertFalse(strategy.evaluate());
    }

    @Test
    void getExpressionReturnsValue() {
        ExpressionRuleStrategy strategy = new ExpressionRuleStrategy(LegacyTargetCode.AUX_PROD, "my_rule");
        assertEquals("my_rule", strategy.getExpression());
        assertEquals(LegacyTargetCode.AUX_PROD, strategy.getTarget());
    }
}
