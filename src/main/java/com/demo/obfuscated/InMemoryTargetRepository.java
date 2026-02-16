package com.demo.obfuscated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InMemoryTargetRepository implements TargetRepository {
    private final List<TargetKey> targets;

    public InMemoryTargetRepository() {
        List<TargetKey> list = new ArrayList<>();
        for (LegacyTargetCode legacy : LegacyTargetCode.values()) {
            list.add(new TargetKey(
                legacy.getValue(),
                legacy.getAccount(),
                legacy.getProject(),
                legacy.getEnvironmentId(),
                legacy.getTenant()
            ));
        }
        this.targets = Collections.unmodifiableList(list);
    }

    @Override
    public Optional<TargetKey> findByValue(String value) {
        if (value == null) return Optional.empty();
        return targets.stream()
                .filter(t -> value.equals(t.getValue()))
                .findFirst();
    }

    @Override
    public Optional<TargetKey> findByAccountProjectAndValue(String account, String project, String value) {
        if (value == null) return Optional.empty();
        return targets.stream()
                .filter(t -> Objects.equals(t.getAccount(), account)
                        && Objects.equals(t.getProject(), project)
                        && value.equals(t.getValue()))
                .findFirst();
    }

    @Override
    public Optional<TargetKey> findByTenantAndTarget(String tenant, String value) {
        if (tenant == null || value == null) return Optional.empty();
        return targets.stream()
                .filter(t -> tenant.equals(t.getTenant()) && value.equals(t.getValue()))
                .findFirst();
    }

    @Override
    public Collection<TargetKey> findAll() {
        return targets;
    }
}
