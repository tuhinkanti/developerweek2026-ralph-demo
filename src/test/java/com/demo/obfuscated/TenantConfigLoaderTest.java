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
        List<TargetKey> targets = loader.getTargetsForTenant("tenant-main");
        assertEquals(3, targets.size());
        assertTrue(targets.contains(LegacyTargetCode.CORE_PROD.toTargetKey()));
        assertTrue(targets.contains(LegacyTargetCode.CORE_TEST.toTargetKey()));
        assertTrue(targets.contains(LegacyTargetCode.CORE_EDGE.toTargetKey()));
    }

    @Test
    void getTargetsForSuiteTenant() {
        List<TargetKey> targets = loader.getTargetsForTenant("tenant-suite-c");
        assertEquals(3, targets.size());
    }

    @Test
    void getTargetsForUnknownTenant() {
        List<TargetKey> targets = loader.getTargetsForTenant("unknown");
        assertTrue(targets.isEmpty());
    }

    @Test
    void resolveByTenantAndValue() {
        assertEquals(LegacyTargetCode.CORE_PROD.toTargetKey(), loader.resolve("tenant-main", "prod"));
        assertEquals(LegacyTargetCode.AUX_TEST.toTargetKey(), loader.resolve("tenant-aux", "test"));
        assertNull(loader.resolve("tenant-main", "stage"));
    }

    @Test
    void resolveByAccountProjectValue() {
        assertEquals(LegacyTargetCode.SUITE_BETA_STAGE.toTargetKey(),
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
