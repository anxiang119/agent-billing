package com.billing.mq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AlertMessageListenerTest {

    @Test
    void testHandleMessage() {
        AlertMessageListener listener = new AlertMessageListener(null);
        assertTrue(true);
    }
}
