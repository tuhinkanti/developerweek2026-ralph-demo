package com.demo.obfuscated;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTargetRepositoryTest {
    private final TargetRepository repository = new InMemoryTargetRepository();

    @Test
    void testFindByValue() {
        Optional<TargetKey> result = repository.findByValue("prod");
        assertTrue(result.isPresent());
        assertEquals("prod", result.get().getValue());
        assertEquals("tenant-main", result.get().getTenant()); // First one in enum is CORE_PROD
    }

    @Test
    void testFindByAccountProjectAndValue() {
        // SUITE_ALPHA_PROD("suite-alpha", "default", "prod", "env-100", "tenant-suite-a")
        Optional<TargetKey> result = repository.findByAccountProjectAndValue("suite-alpha", "default", "prod");
        assertTrue(result.isPresent());
        assertEquals("prod", result.get().getValue());
        assertEquals("suite-alpha", result.get().getAccount());
        assertEquals("default", result.get().getProject());
        assertEquals("tenant-suite-a", result.get().getTenant());
    }

    @Test
    void testFindByTenantAndTarget() {
        // AUX_PROD("prod", "tenant-aux")
        Optional<TargetKey> result = repository.findByTenantAndTarget("tenant-aux", "prod");
        assertTrue(result.isPresent());
        assertEquals("prod", result.get().getValue());
        assertEquals("tenant-aux", result.get().getTenant());
    }

    @Test
    void testFindUnknown() {
        assertFalse(repository.findByValue("unknown").isPresent());
        assertFalse(repository.findByTenantAndTarget("non-existent", "prod").isPresent());
        assertFalse(repository.findByAccountProjectAndValue("none", "none", "none").isPresent());
    }

    @Test
    void testFindAll() {
        assertEquals(LegacyTargetCode.values().length, repository.findAll().size());
    }
}
