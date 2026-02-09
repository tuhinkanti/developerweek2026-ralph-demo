package com.demo.obfuscated;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleStrategySerializerTest {

    private final RuleStrategySerializer serializer = new RuleStrategySerializer();
    private final RuleStrategyDeserializer deserializer = new RuleStrategyDeserializer();

    @Test
    void serializeThresholdStrategy() {
        ThresholdRuleStrategy strategy = new ThresholdRuleStrategy(LegacyTargetCode.CORE_PROD, 42);
        String result = serializer.serialize(strategy);

        assertTrue(result.contains("target=CORE_PROD"));
        assertTrue(result.contains("threshold=42"));
        assertTrue(result.contains("tenant=tenant-main"));
    }

    @Test
    void serializeExpressionStrategy() {
        ExpressionRuleStrategy strategy = new ExpressionRuleStrategy(LegacyTargetCode.SUITE_ALPHA_PROD, "my_rule");
        String result = serializer.serialize(strategy);

        assertTrue(result.contains("target=SUITE_ALPHA_PROD"));
        assertTrue(result.contains("expression=my_rule"));
    }

    @Test
    void serializeRolloutStrategy() {
        RolloutRuleStrategy strategy = new RolloutRuleStrategy(LegacyTargetCode.CORE_TEST, 75);
        String result = serializer.serialize(strategy);

        assertTrue(result.contains("target=CORE_TEST"));
        assertTrue(result.contains("rolloutPct=75"));
    }

    @Test
    void serializeNullReturnsEmpty() {
        assertEquals("", serializer.serialize(null));
    }

    @Test
    void deserializeThresholdStrategy() {
        String raw = "target=CORE_PROD;value=prod;tenant=tenant-main;threshold=42";
        RuleStrategy result = deserializer.deserialize(raw);

        assertNotNull(result);
        assertInstanceOf(ThresholdRuleStrategy.class, result);
        assertEquals(LegacyTargetCode.CORE_PROD, result.getTarget());
        assertEquals(42, ((ThresholdRuleStrategy) result).getThreshold());
    }

    @Test
    void deserializeExpressionStrategy() {
        String raw = "target=SUITE_ALPHA_PROD;expression=my_rule";
        RuleStrategy result = deserializer.deserialize(raw);

        assertNotNull(result);
        assertInstanceOf(ExpressionRuleStrategy.class, result);
        assertEquals("my_rule", ((ExpressionRuleStrategy) result).getExpression());
    }

    @Test
    void deserializeRolloutStrategy() {
        String raw = "target=CORE_TEST;rolloutPct=75";
        RuleStrategy result = deserializer.deserialize(raw);

        assertNotNull(result);
        assertInstanceOf(RolloutRuleStrategy.class, result);
        assertEquals(75, ((RolloutRuleStrategy) result).getRolloutPercentage());
    }

    @Test
    void deserializeNullReturnsNull() {
        assertNull(deserializer.deserialize(null));
        assertNull(deserializer.deserialize(""));
    }

    @Test
    void deserializeInvalidTargetReturnsNull() {
        assertNull(deserializer.deserialize("target=INVALID;threshold=10"));
    }
}
