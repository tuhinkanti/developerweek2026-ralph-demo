package com.demo.obfuscated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TargetKeyTest {

    @Test
    void testEqualityAndHashCode() {
        TargetKey key1 = new TargetKey("prod", null, null, null, "tenant-main");
        TargetKey key2 = new TargetKey("prod", null, null, null, "tenant-main");
        TargetKey key3 = new TargetKey("test", null, null, null, "tenant-main");
        TargetKey suiteKey1 = new TargetKey("prod", "suite-alpha", "default", "env-100", "tenant-suite-a");
        TargetKey suiteKey2 = new TargetKey("prod", "suite-alpha", "default", "env-100", "tenant-suite-a");

        assertEquals(key1, key2);
        assertEquals(key1.hashCode(), key2.hashCode());
        assertNotEquals(key1, key3);
        assertEquals(suiteKey1, suiteKey2);
        assertEquals(suiteKey1.hashCode(), suiteKey2.hashCode());
        assertNotEquals(key1, suiteKey1);
    }

    @Test
    void testGetValueWithProject() {
        TargetKey coreKey = new TargetKey("prod", null, null, null, "tenant-main");
        assertEquals("prod", coreKey.getValueWithProject());

        TargetKey suiteKey = new TargetKey("prod", "suite-alpha", "default", "env-100", "tenant-suite-a");
        assertEquals("default__prod", suiteKey.getValueWithProject());
    }

    @Test
    void testToString() {
        TargetKey key = new TargetKey("prod", "acc", "proj", "env", "ten");
        String str = key.toString();
        assertTrue(str.contains("value=prod"));
        assertTrue(str.contains("account=acc"));
        assertTrue(str.contains("project=proj"));
        assertTrue(str.contains("environmentId=env"));
        assertTrue(str.contains("tenant=ten"));
    }
}
