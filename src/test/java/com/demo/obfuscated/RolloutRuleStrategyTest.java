package com.demo.obfuscated;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RolloutRuleStrategyTest {

    @Test
    void evaluateWithinRollout() {
        RolloutRuleStrategy strategy = new RolloutRuleStrategy(LegacyTargetCode.CORE_PROD, 50);
        assertTrue(strategy.evaluate(0));
        assertTrue(strategy.evaluate(49));
    }

    @Test
    void evaluateOutsideRollout() {
        RolloutRuleStrategy strategy = new RolloutRuleStrategy(LegacyTargetCode.CORE_PROD, 50);
        assertFalse(strategy.evaluate(50));
        assertFalse(strategy.evaluate(99));
    }

    @Test
    void rejectsInvalidPercentage() {
        assertThrows(IllegalArgumentException.class, () ->
                new RolloutRuleStrategy(LegacyTargetCode.CORE_PROD, -1));
        assertThrows(IllegalArgumentException.class, () ->
                new RolloutRuleStrategy(LegacyTargetCode.CORE_PROD, 101));
    }

    @Test
    void zeroPercentRollout() {
        RolloutRuleStrategy strategy = new RolloutRuleStrategy(LegacyTargetCode.CORE_TEST, 0);
        assertFalse(strategy.evaluate(0));
        assertEquals(0, strategy.getRolloutPercentage());
    }

    @Test
    void fullRollout() {
        RolloutRuleStrategy strategy = new RolloutRuleStrategy(LegacyTargetCode.CORE_TEST, 100);
        assertTrue(strategy.evaluate(99));
        assertEquals(100, strategy.getRolloutPercentage());
    }
}
