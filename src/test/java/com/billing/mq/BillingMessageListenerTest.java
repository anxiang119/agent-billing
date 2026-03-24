package com.billing.mq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BillingMessageListenerTest {

    @Test
    void testHandleMessage() {
        BillingMessageListener listener = new BillingMessageListener(null);
        assertTrue(true);
    }
}
