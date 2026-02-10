package com.example.featureflags.core;

import com.example.featureflags.core.impl.InMemoryEnvironmentRepository;
import com.example.featureflags.core.impl.ToggleBuilderImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ToggleBuilderTest {

    @Test
    public void testWithEnvironment_Null() {
        ToggleBuilderImpl builder = new ToggleBuilderImpl(new InMemoryEnvironmentRepository());
        assertThrows(IllegalArgumentException.class, () -> {
            builder.withEnvironment((String) null);
        });
    }
}
