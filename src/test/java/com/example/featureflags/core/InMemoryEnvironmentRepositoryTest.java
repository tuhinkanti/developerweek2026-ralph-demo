package com.example.featureflags.core;

import com.example.featureflags.core.impl.InMemoryEnvironmentRepository;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryEnvironmentRepositoryTest {

    @Test
    public void testFindByName_Null() {
        InMemoryEnvironmentRepository repo = new InMemoryEnvironmentRepository();
        assertEquals(Optional.empty(), repo.findByName(null));
    }
}
