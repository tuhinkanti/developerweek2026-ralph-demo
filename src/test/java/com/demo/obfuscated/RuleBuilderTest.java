package com.demo.obfuscated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RuleBuilderTest {

    @Test
    void testBuildWithEnum() {
        RuleBuilder builder = new RuleBuilderImpl();
        RuleStrategy strategy = builder.withTarget(LegacyTargetCode.CORE_PROD)
                .withThreshold(100)
                .build();
        
        assertEquals(LegacyTargetCode.CORE_PROD, strategy.getTarget());
        assertEquals(LegacyTargetCode.CORE_PROD.toTargetKey(), strategy.getTargetKey());
    }

    @Test
    void testBuildWithTargetKey() {
        TargetKey key = LegacyTargetCode.CORE_TEST.toTargetKey();
        RuleBuilder builder = new RuleBuilderImpl();
        RuleStrategy strategy = builder.withTargetKey(key)
                .withThreshold(200)
                .build();
        
        assertEquals(key, strategy.getTargetKey());
        assertEquals(LegacyTargetCode.CORE_TEST, strategy.getTarget());
    }

    @Test
    void testBuildWithUnknownTargetKey() {
        TargetKey unknownKey = new TargetKey("unknown", null, null, null, "ten");
        RuleBuilder builder = new RuleBuilderImpl();
        RuleStrategy strategy = builder.withTargetKey(unknownKey)
                .withThreshold(300)
                .build();
        
        assertEquals(unknownKey, strategy.getTargetKey());
        assertNull(strategy.getTarget());
    }

    @Test
    void testMissingTargetThrowsException() {
        RuleBuilder builder = new RuleBuilderImpl();
        assertThrows(IllegalStateException.class, builder::build);
    }
}
