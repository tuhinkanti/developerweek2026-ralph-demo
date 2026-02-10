package com.example.featureflags.core;

import com.example.featureflags.core.api.EnvironmentTarget;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnvironmentTargetTest {

    @Test
    public void testFromAccountProjectAndValue() {
        assertEquals(EnvironmentTarget.PRODUCTION, EnvironmentTarget.fromAccountProjectAndValue(null, null, "production"));
        assertEquals(EnvironmentTarget.SERVICE_B_PROD, EnvironmentTarget.fromAccountProjectAndValue("service-b", "default", "production"));
    }

    @Test
    public void testFromAccountProjectAndValue_NotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            EnvironmentTarget.fromAccountProjectAndValue(null, null, "nonexistent");
        });
    }

    @Test
    public void testFromAccountProjectAndValue_NullValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            EnvironmentTarget.fromAccountProjectAndValue(null, null, null);
        });
    }
}
