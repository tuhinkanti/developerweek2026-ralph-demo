package com.demo.obfuscated;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LegacyTargetCodeTest {

    @Test
    void coreTargetsHaveNoAccountOrProject() {
        assertNull(LegacyTargetCode.CORE_PROD.getAccount());
        assertNull(LegacyTargetCode.CORE_PROD.getProject());
        assertEquals("prod", LegacyTargetCode.CORE_PROD.getValue());
        assertEquals("tenant-main", LegacyTargetCode.CORE_PROD.getTenant());
    }

    @Test
    void suiteTargetsHaveAllFields() {
        LegacyTargetCode target = LegacyTargetCode.SUITE_ALPHA_PROD;
        assertEquals("suite-alpha", target.getAccount());
        assertEquals("default", target.getProject());
        assertEquals("prod", target.getValue());
        assertEquals("env-100", target.getEnvironmentId());
        assertEquals("tenant-suite-a", target.getTenant());
    }

    @Test
    void isProductionForProdTargets() {
        assertTrue(LegacyTargetCode.CORE_PROD.isProduction());
        assertTrue(LegacyTargetCode.AUX_PROD.isProduction());
        assertTrue(LegacyTargetCode.SUITE_BETA_PROD.isProduction());
    }

    @Test
    void isProductionFalseForNonProdTargets() {
        assertFalse(LegacyTargetCode.CORE_TEST.isProduction());
        assertFalse(LegacyTargetCode.CORE_EDGE.isProduction());
        assertFalse(LegacyTargetCode.SUITE_BETA_STAGE.isProduction());
    }

    @Test
    void enumValuesCount() {
        assertEquals(12, LegacyTargetCode.values().length);
    }
}
