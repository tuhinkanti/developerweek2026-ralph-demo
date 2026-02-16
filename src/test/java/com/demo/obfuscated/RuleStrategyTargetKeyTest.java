package com.demo.obfuscated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RuleStrategyTargetKeyTest {

    @Test
    void testThresholdStrategyWithTargetKey() {
        TargetKey key = LegacyTargetCode.CORE_PROD.toTargetKey();
        ThresholdRuleStrategy strategy = new ThresholdRuleStrategy(key, 100);
        
        assertEquals(key, strategy.getTargetKey());
        assertEquals(LegacyTargetCode.CORE_PROD, strategy.getTarget());
        assertEquals("prod", strategy.getTargetValue());
        assertTrue(strategy.evaluate(150));
    }

    @Test
    void testExpressionStrategyWithTargetKey() {
        TargetKey key = LegacyTargetCode.SUITE_ALPHA_PROD.toTargetKey();
        ExpressionRuleStrategy strategy = new ExpressionRuleStrategy(key, "true");
        
        assertEquals(key, strategy.getTargetKey());
        assertEquals(LegacyTargetCode.SUITE_ALPHA_PROD, strategy.getTarget());
        assertEquals("prod", strategy.getTargetValue());
        assertTrue(strategy.evaluate());
    }

    @Test
    void testRolloutStrategyWithTargetKey() {
        TargetKey key = LegacyTargetCode.CORE_TEST.toTargetKey();
        RolloutRuleStrategy strategy = new RolloutRuleStrategy(key, 50);
        
        assertEquals(key, strategy.getTargetKey());
        assertEquals(LegacyTargetCode.CORE_TEST, strategy.getTarget());
        assertEquals("test", strategy.getTargetValue());
        assertTrue(strategy.evaluate(25));
    }

    @Test
    void testStrategyWithUnknownTargetKey() {
        TargetKey unknownKey = new TargetKey("unknown", null, null, null, "unknown-tenant");
        ThresholdRuleStrategy strategy = new ThresholdRuleStrategy(unknownKey, 100);
        
        assertEquals(unknownKey, strategy.getTargetKey());
        assertNull(strategy.getTarget());
        assertEquals("unknown", strategy.getTargetValue());
    }
}
