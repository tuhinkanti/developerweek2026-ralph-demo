package com.example.featureflags.core;

import com.example.featureflags.core.api.Environment;
import com.example.featureflags.core.api.EnvironmentTarget;
import com.example.featureflags.core.impl.BaseReleaseStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReleaseStrategyTest {

    @Test
    public void testLegacyStrategyTarget() {
        BaseReleaseStrategy strategy = new BaseReleaseStrategy(EnvironmentTarget.PRODUCTION);
        assertEquals(EnvironmentTarget.PRODUCTION, strategy.getTarget());
        assertEquals(Environment.PRODUCTION, strategy.getEnvironment());
    }

    @Test
    public void testNewEnvironmentStrategy() {
        Environment customEnv = Environment.of("CUSTOM_ENV");
        BaseReleaseStrategy strategy = new BaseReleaseStrategy(customEnv);
        assertEquals(customEnv, strategy.getEnvironment());
        assertEquals("CUSTOM_ENV", strategy.getEnvironment().getName());
    }
}
