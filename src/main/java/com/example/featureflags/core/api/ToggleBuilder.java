package com.example.featureflags.core.api;

public interface ToggleBuilder {
    /**
     * @deprecated Use {@link #withEnvironment(Environment)} instead.
     */
    @Deprecated
    ToggleBuilder withTarget(EnvironmentTarget target);

    ToggleBuilder withEnvironment(Environment environment);

    ToggleBuilder withEnvironment(String environmentName);
}
