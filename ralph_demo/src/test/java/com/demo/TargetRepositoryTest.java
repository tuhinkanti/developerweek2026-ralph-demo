package com.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class TargetRepositoryTest {
    private TargetRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTargetRepository();
    }

    @Test
    void testSaveAndFindByName() {
        Target target = Target.of("TEST", "env", "reg", 1);
        repository.save(target);

        Optional<Target> found = repository.findByName("TEST");
        assertTrue(found.isPresent());
        assertEquals(target, found.get());
    }

    @Test
    void testFindByEnvironment() {
        repository.save(Target.of("T1", "env1", "reg1", 1));
        repository.save(Target.of("T2", "env1", "reg2", 2));
        repository.save(Target.of("T3", "env2", "reg3", 3));

        List<Target> env1Targets = repository.findByEnvironment("env1");
        assertEquals(2, env1Targets.size());
    }

    @Test
    void testFindAll() {
        repository.save(Target.of("T1", "env1", "reg1", 1));
        repository.save(Target.of("T2", "env1", "reg2", 2));

        List<Target> all = repository.findAll();
        assertEquals(2, all.size());
    }
}
