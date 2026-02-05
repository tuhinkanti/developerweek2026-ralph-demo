package com.example.featureflags.core.api;

public interface ReleaseStrategy {
    /**
     * @deprecated Use {@link #getEnvironment()} instead.
     */
    @Deprecated
    EnvironmentTarget getTarget();

    Environment getEnvironment();
}
