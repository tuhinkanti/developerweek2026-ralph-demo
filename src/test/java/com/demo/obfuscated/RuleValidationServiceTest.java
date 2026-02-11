package com.demo.obfuscated;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuleValidationServiceTest {

    private RuleValidationService service;

    @BeforeEach
    void setUp() {
        service = new RuleValidationService();
    }

    @Test
    void validThresholdPasses() {
        ThresholdRuleStrategy strategy = new ThresholdRuleStrategy(LegacyTargetCode.CORE_PROD, 500);
        List<String> errors = service.validate(strategy);
        assertTrue(errors.isEmpty());
    }

    @Test
    void prodThresholdTooHigh() {
        ThresholdRuleStrategy strategy = new ThresholdRuleStrategy(LegacyTargetCode.CORE_PROD, 1500);
        List<String> errors = service.validate(strategy);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("1000"));
    }

    @Test
    void testThresholdTooHigh() {
        ThresholdRuleStrategy strategy = new ThresholdRuleStrategy(LegacyTargetCode.CORE_TEST, 6000);
        List<String> errors = service.validate(strategy);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("5000"));
    }

    @Test
    void prodRolloutTooHigh() {
        RolloutRuleStrategy strategy = new RolloutRuleStrategy(LegacyTargetCode.CORE_PROD, 75);
        List<String> errors = service.validate(strategy);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("50%"));
    }

    @Test
    void nonProdRolloutAllowed() {
        RolloutRuleStrategy strategy = new RolloutRuleStrategy(LegacyTargetCode.CORE_TEST, 75);
        List<String> errors = service.validate(strategy);
        assertTrue(errors.isEmpty());
    }

    @Test
    void emptyExpressionFails() {
        ExpressionRuleStrategy strategy = new ExpressionRuleStrategy(LegacyTargetCode.CORE_TEST, "");
        List<String> errors = service.validate(strategy);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("empty"));
    }

    @Test
    void unconditionalEnableBlockedForProd() {
        ExpressionRuleStrategy strategy = new ExpressionRuleStrategy(LegacyTargetCode.CORE_PROD, "true");
        List<String> errors = service.validate(strategy);
        assertTrue(errors.stream().anyMatch(e -> e.contains("production")));
    }

    @Test
    void nullStrategyFails() {
        List<String> errors = service.validate(null);
        assertEquals(1, errors.size());
    }

    @Test
    void nullTargetFails() {
        ThresholdRuleStrategy strategy = new ThresholdRuleStrategy(null, 10);
        List<String> errors = service.validate(strategy);
        assertTrue(errors.stream().anyMatch(e -> e.contains("null")));
    }
}
