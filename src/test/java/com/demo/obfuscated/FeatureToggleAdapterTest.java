package com.demo.obfuscated;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeatureToggleAdapterTest {

    private FeatureToggleAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new FeatureToggleAdapter();
    }

    @Test
    void isEnabledWhenRuleMatches() {
        adapter.register("toggle-x", new ExpressionRuleStrategy(LegacyTargetCode.CORE_PROD, "true"));
        assertTrue(adapter.isEnabled("toggle-x", LegacyTargetCode.CORE_PROD));
    }

    @Test
    void isNotEnabledWhenTargetMismatch() {
        adapter.register("toggle-x", new ExpressionRuleStrategy(LegacyTargetCode.CORE_PROD, "true"));
        assertFalse(adapter.isEnabled("toggle-x", LegacyTargetCode.CORE_TEST));
    }

    @Test
    void isNotEnabledWhenToggleNotRegistered() {
        assertFalse(adapter.isEnabled("unknown", LegacyTargetCode.CORE_PROD));
    }

    @Test
    void getTargetsForToggle() {
        adapter.register("toggle-y", new ThresholdRuleStrategy(LegacyTargetCode.CORE_PROD, 10));
        adapter.register("toggle-y", new ThresholdRuleStrategy(LegacyTargetCode.CORE_TEST, 20));

        List<LegacyTargetCode> targets = adapter.getTargetsForToggle("toggle-y");
        assertEquals(2, targets.size());
        assertTrue(targets.contains(LegacyTargetCode.CORE_PROD));
        assertTrue(targets.contains(LegacyTargetCode.CORE_TEST));
    }

    @Test
    void getProductionTargetsForToggle() {
        adapter.register("toggle-z", new ThresholdRuleStrategy(LegacyTargetCode.CORE_PROD, 10));
        adapter.register("toggle-z", new ThresholdRuleStrategy(LegacyTargetCode.CORE_TEST, 20));
        adapter.register("toggle-z", new ThresholdRuleStrategy(LegacyTargetCode.SUITE_ALPHA_PROD, 30));

        List<LegacyTargetCode> prodTargets = adapter.getProductionTargetsForToggle("toggle-z");
        assertEquals(2, prodTargets.size());
        assertTrue(prodTargets.contains(LegacyTargetCode.CORE_PROD));
        assertTrue(prodTargets.contains(LegacyTargetCode.SUITE_ALPHA_PROD));
    }

    @Test
    void nullInputsReturnFalse() {
        assertFalse(adapter.isEnabled(null, LegacyTargetCode.CORE_PROD));
        assertFalse(adapter.isEnabled("toggle-x", null));
    }
}
