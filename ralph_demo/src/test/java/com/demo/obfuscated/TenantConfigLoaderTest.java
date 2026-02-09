package com.demo.obfuscated;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TenantConfigLoaderTest {

    private TenantConfigLoader loader;

    @BeforeEach
    void setUp() {
        loader = new TenantConfigLoader();
    }

    @Test
    void getTargetsForCoreTenant() {
        List<LegacyTargetCode> targets = loader.getTargetsForTenant("tenant-main");
        assertEquals(3, targets.size());
        assertTrue(targets.contains(LegacyTargetCode.CORE_PROD));
        assertTrue(targets.contains(LegacyTargetCode.CORE_TEST));
        assertTrue(targets.contains(LegacyTargetCode.CORE_EDGE));
    }

    @Test
    void getTargetsForSuiteTenant() {
        List<LegacyTargetCode> targets = loader.getTargetsForTenant("tenant-suite-c");
        assertEquals(3, targets.size());
    }

    @Test
    void getTargetsForUnknownTenant() {
        List<LegacyTargetCode> targets = loader.getTargetsForTenant("unknown");
        assertTrue(targets.isEmpty());
    }

    @Test
    void resolveByTenantAndValue() {
        assertEquals(LegacyTargetCode.CORE_PROD, loader.resolve("tenant-main", "prod"));
        assertEquals(LegacyTargetCode.AUX_TEST, loader.resolve("tenant-aux", "test"));
        assertNull(loader.resolve("tenant-main", "stage"));
    }

    @Test
    void resolveByAccountProjectValue() {
        assertEquals(LegacyTargetCode.SUITE_BETA_STAGE,
                loader.resolveByAccountProjectValue("suite-beta", "blue", "stage"));
    }

    @Test
    void resolveByAccountProjectValueNotFound() {
        assertNull(loader.resolveByAccountProjectValue("suite-beta", "blue", "prod-x"));
    }

    @Test
    void nullInputsReturnNull() {
        assertNull(loader.resolve(null, "prod"));
        assertNull(loader.resolve("tenant-main", null));
        assertNull(loader.resolveByAccountProjectValue(null, null, null));
    }
}
