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

    @Test
    void testToTargetKey() {
        for (LegacyTargetCode code : LegacyTargetCode.values()) {
            TargetKey key = code.toTargetKey();
            assertEquals(code.getValue(), key.getValue());
            assertEquals(code.getTenant(), key.getTenant());
            assertEquals(code.getAccount(), key.getAccount());
            assertEquals(code.getProject(), key.getProject());
            assertEquals(code.getEnvironmentId(), key.getEnvironmentId());
        }
    }

    @Test
    void testFromTargetKey() {
        for (LegacyTargetCode code : LegacyTargetCode.values()) {
            TargetKey key = code.toTargetKey();
            assertEquals(code, LegacyTargetCode.fromTargetKey(key));
        }
    }

    @Test
    void testFromValue() {
        assertEquals(LegacyTargetCode.CORE_PROD, LegacyTargetCode.fromValue("prod"));
    }

    @Test
    void testFromTenantAndValue() {
        assertEquals(LegacyTargetCode.AUX_PROD, LegacyTargetCode.fromTenantAndValue("tenant-aux", "prod"));
    }

    @Test
    void testFromAccountProjectAndValue() {
        assertEquals(LegacyTargetCode.SUITE_ALPHA_PROD, LegacyTargetCode.fromAccountProjectAndValue("suite-alpha", "default", "prod"));
    }

    @Test
    void testTargetConverter() {
        for (LegacyTargetCode code : LegacyTargetCode.values()) {
            TargetKey key = TargetConverter.fromLegacy(code);
            assertEquals(code, TargetConverter.toLegacy(key));
        }
    }

    @Test
    void testUnknownInputsThrowException() {
        assertThrows(IllegalArgumentException.class, () -> LegacyTargetCode.fromValue("unknown"));
        assertThrows(IllegalArgumentException.class, () -> LegacyTargetCode.fromTenantAndValue("unknown", "prod"));
        assertThrows(IllegalArgumentException.class, () -> LegacyTargetCode.fromAccountProjectAndValue("unknown", "none", "prod"));
        assertThrows(IllegalArgumentException.class, () -> LegacyTargetCode.fromTargetKey(new TargetKey("none", null, null, null, "none")));
    }
}
