package com.demo.obfuscated;

import java.util.Collection;
import java.util.Optional;

public interface TargetRepository {
    Optional<TargetKey> findByValue(String value);
    Optional<TargetKey> findByAccountProjectAndValue(String account, String project, String value);
    Optional<TargetKey> findByTenantAndTarget(String tenant, String value);
    Collection<TargetKey> findAll();
}
