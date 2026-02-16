package com.demo.obfuscated;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebhookHandlerTest {

    private WebhookHandler handler;

    @BeforeEach
    void setUp() {
        handler = new WebhookHandler();
    }

    @Test
    void resolveKnownEndpoint() {
        assertEquals("/hooks/core/prod", handler.resolveEndpoint(LegacyTargetCode.CORE_PROD));
        assertEquals("/hooks/suite-a/prod", handler.resolveEndpoint(LegacyTargetCode.SUITE_ALPHA_PROD));
    }

    @Test
    void resolveNullTargetReturnsNull() {
        assertNull(handler.resolveEndpoint((TargetKey) null));
    }

    @Test
    void resolveUnmappedTargetReturnsNull() {
        assertNull(handler.resolveEndpoint(LegacyTargetCode.SUITE_GAMMA_TEST));
    }

    @Test
    void handleCreateEventSuccess() {
        WebhookHandler.WebhookResult result = handler.handle("create", LegacyTargetCode.CORE_PROD);
        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("Routed"));
    }

    @Test
    void handleDeleteBlockedForProd() {
        WebhookHandler.WebhookResult result = handler.handle("delete", LegacyTargetCode.CORE_PROD);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("blocked"));
    }

    @Test
    void handleDeleteAllowedForTest() {
        WebhookHandler.WebhookResult result = handler.handle("delete", LegacyTargetCode.CORE_TEST);
        assertTrue(result.isSuccess());
    }

    @Test
    void handleNullInputs() {
        WebhookHandler.WebhookResult result = handler.handle(null, LegacyTargetCode.CORE_PROD);
        assertFalse(result.isSuccess());

        result = handler.handle("create", (TargetKey) null);
        assertFalse(result.isSuccess());
    }
}
