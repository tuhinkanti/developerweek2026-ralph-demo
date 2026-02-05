package com.example.featureflags.core;

import com.example.featureflags.core.api.Environment;
import com.example.featureflags.core.impl.InMemoryEnvironmentRepository;
import com.example.featureflags.core.impl.ToggleBuilderImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnvironmentRepositoryTest {

    @Test
    public void testRepositoryLookup() {
        InMemoryEnvironmentRepository repo = new InMemoryEnvironmentRepository();
        repo.register(Environment.of("TEST_ENV"));
        
        ToggleBuilderImpl builder = new ToggleBuilderImpl(repo);
        builder.withEnvironment("TEST_ENV");
        
        assertEquals("TEST_ENV", builder.getEnvironment().getName());
    }

    @Test
    public void testRepositoryLookupFailure() {
        assertThrows(IllegalArgumentException.class, () -> {
            ToggleBuilderImpl builder = new ToggleBuilderImpl(new InMemoryEnvironmentRepository());
            builder.withEnvironment("UNKNOWN_ENV");
        });
    }
}
