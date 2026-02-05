package com.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FeatureTest {

    @Test
    void testFeatureCreation() {
        Feature feature = new Feature("dark-mode", "Enable dark mode UI", DeploymentTarget.PROD_US, true);
        
        assertEquals("dark-mode", feature.getName());
        assertEquals("Enable dark mode UI", feature.getDescription());
        assertEquals(DeploymentTarget.PROD_US, feature.getTarget());
        assertTrue(feature.isEnabled());
    }

    @Test
    void testProductionFeature() {
        Feature prodFeature = new Feature("feature1", "desc", DeploymentTarget.PROD_US, true);
        Feature stagingFeature = new Feature("feature2", "desc", DeploymentTarget.STAGING_US, true);
        
        assertTrue(prodFeature.isProductionFeature());
        assertFalse(stagingFeature.isProductionFeature());
    }

    @Test
    void testDisabledFeature() {
        Feature feature = new Feature("disabled-feature", "desc", DeploymentTarget.PROD_EU, false);
        
        assertFalse(feature.isEnabled());
    }
}
