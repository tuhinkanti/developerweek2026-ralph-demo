package com.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TargetTest {

    @Test
    void testTargetCreation() {
        Target target = Target.of("PROD_US", "production", "us", 1);
        assertEquals("PROD_US", target.getId());
        assertEquals("PROD_US", target.getName());
        assertEquals("production", target.getEnvironment());
        assertEquals("us", target.getRegion());
        assertEquals(1, target.getPriority());
    }

    @Test
    void testTargetEquals() {
        Target t1 = new Target("ID1", "Name1", "Env1", "Reg1", 1);
        Target t2 = new Target("ID1", "Name2", "Env2", "Reg2", 2);
        Target t3 = new Target("ID2", "Name1", "Env1", "Reg1", 1);

        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
        assertEquals(t1.hashCode(), t2.hashCode());
    }
}
