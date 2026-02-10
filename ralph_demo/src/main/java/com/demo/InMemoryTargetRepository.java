package com.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of TargetRepository.
 */
public class InMemoryTargetRepository implements TargetRepository {
    private final Map<String, Target> targets = new ConcurrentHashMap<>();

    @Override
    public Optional<Target> findByName(String name) {
        if (name == null) return Optional.empty();
        return targets.values().stream()
                .filter(t -> t.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Target> findByEnvironment(String environment) {
        if (environment == null) return List.of();
        return targets.values().stream()
                .filter(t -> t.getEnvironment().equals(environment))
                .collect(Collectors.toList());
    }

    @Override
    public List<Target> findAll() {
        return new ArrayList<>(targets.values());
    }

    @Override
    public void save(Target target) {
        targets.put(target.getId(), target);
    }
}
