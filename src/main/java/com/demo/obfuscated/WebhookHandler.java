package com.demo.obfuscated;

import java.util.HashMap;
import java.util.Map;

/**
 * Processes incoming webhook events and routes them to the appropriate target handler.
 */
public class WebhookHandler {

    private final Map<LegacyTargetCode, String> targetEndpoints = new HashMap<>();

    public WebhookHandler() {
        targetEndpoints.put(LegacyTargetCode.CORE_PROD, "/hooks/core/prod");
        targetEndpoints.put(LegacyTargetCode.CORE_TEST, "/hooks/core/test");
        targetEndpoints.put(LegacyTargetCode.CORE_EDGE, "/hooks/core/edge");
        targetEndpoints.put(LegacyTargetCode.AUX_PROD, "/hooks/aux/prod");
        targetEndpoints.put(LegacyTargetCode.AUX_TEST, "/hooks/aux/test");
        targetEndpoints.put(LegacyTargetCode.SUITE_ALPHA_PROD, "/hooks/suite-a/prod");
        targetEndpoints.put(LegacyTargetCode.SUITE_BETA_PROD, "/hooks/suite-b/prod");
        targetEndpoints.put(LegacyTargetCode.SUITE_GAMMA_PROD, "/hooks/suite-c/prod");
    }

    public String resolveEndpoint(LegacyTargetCode target) {
        if (target == null) {
            return null;
        }
        return targetEndpoints.get(target);
    }

    public WebhookResult handle(String eventType, LegacyTargetCode target) {
        if (eventType == null || target == null) {
            return new WebhookResult(false, "Missing event type or target");
        }
        String endpoint = resolveEndpoint(target);
        if (endpoint == null) {
            return new WebhookResult(false, "No endpoint configured for target: " + target.name());
        }
        if (target.isProduction() && "delete".equalsIgnoreCase(eventType)) {
            return new WebhookResult(false, "Delete events blocked for production targets");
        }
        return new WebhookResult(true, "Routed " + eventType + " to " + endpoint);
    }

    public static class WebhookResult {
        private final boolean success;
        private final String message;

        public WebhookResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
