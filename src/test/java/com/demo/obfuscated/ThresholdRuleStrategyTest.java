package com.demo.obfuscated;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThresholdRuleStrategyTest {

    @Test
    void evaluateAboveThreshold() {
        ThresholdRuleStrategy strategy = new ThresholdRuleStrategy(LegacyTargetCode.CORE_PROD, 50);
        assertTrue(strategy.evaluate(50));
        assertTrue(strategy.evaluate(100));
    }

    @Test
    void evaluateBelowThreshold() {
        ThresholdRuleStrategy strategy = new ThresholdRuleStrategy(LegacyTargetCode.CORE_PROD, 50);
        assertFalse(strategy.evaluate(49));
        assertFalse(strategy.evaluate(0));
    }

    @Test
    void getTargetReturnsEnum() {
        ThresholdRuleStrategy strategy = new ThresholdRuleStrategy(LegacyTargetCode.SUITE_GAMMA_PROD, 10);
        assertEquals(LegacyTargetCode.SUITE_GAMMA_PROD, strategy.getTarget());
        assertEquals(10, strategy.getThreshold());
    }
}
