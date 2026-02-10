package com.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FeatureServiceTest {
    
    private FeatureService service;

    @BeforeEach
    void setUp() {
        service = new FeatureService();
    }

    @Test
    void testIsEnabledFor() {
        Feature feature = new Feature("test", "desc", DeploymentTarget.PROD_US, true);
        
        assertTrue(service.isEnabledFor(feature, DeploymentTarget.PROD_US));
        assertFalse(service.isEnabledFor(feature, DeploymentTarget.PROD_EU));
    }

    @Test
    void testIsEnabledForDisabledFeature() {
        Feature feature = new Feature("test", "desc", DeploymentTarget.PROD_US, false);
        
        assertFalse(service.isEnabledFor(feature, DeploymentTarget.PROD_US));
    }

    @Test
    void testIsEnabledForNullInputs() {
        Feature feature = new Feature("test", "desc", DeploymentTarget.PROD_US, true);
        
        assertFalse(service.isEnabledFor(null, DeploymentTarget.PROD_US));
        assertFalse(service.isEnabledFor(feature, (DeploymentTarget) null));
        assertFalse(service.isEnabledFor(feature, (Target) null));
    }

    @Test
    void testGetTargetsForEnvironment() {
        List<DeploymentTarget> prodTargets = service.getTargetsForEnvironment("production");
        
        assertEquals(3, prodTargets.size());
        assertTrue(prodTargets.contains(DeploymentTarget.PROD_US));
        assertTrue(prodTargets.contains(DeploymentTarget.PROD_EU));
        assertTrue(prodTargets.contains(DeploymentTarget.PROD_APAC));
    }

    @Test
    void testGetTargetsForEnvironmentNull() {
        List<DeploymentTarget> targets = service.getTargetsForEnvironment(null);
        assertTrue(targets.isEmpty());
    }

    @Test
    void testGetFeaturesForTarget() {
        Feature f1 = new Feature("f1", "desc", DeploymentTarget.PROD_US, true);
        Feature f2 = new Feature("f2", "desc", DeploymentTarget.PROD_US, true);
        Feature f3 = new Feature("f3", "desc", DeploymentTarget.STAGING_US, true);
        
        service.addFeature(f1);
        service.addFeature(f2);
        service.addFeature(f3);
        
        List<Feature> prodUsFeatures = service.getFeaturesForTarget(DeploymentTarget.PROD_US);
        assertEquals(2, prodUsFeatures.size());
    }

    @Test
    void testGetProductionFeatures() {
        Feature f1 = new Feature("f1", "desc", DeploymentTarget.PROD_US, true);
        Feature f2 = new Feature("f2", "desc", DeploymentTarget.PROD_EU, true);
        Feature f3 = new Feature("f3", "desc", DeploymentTarget.STAGING_US, true);
        
        service.addFeature(f1);
        service.addFeature(f2);
        service.addFeature(f3);
        
        List<Feature> prodFeatures = service.getProductionFeatures();
        assertEquals(2, prodFeatures.size());
    }
}
