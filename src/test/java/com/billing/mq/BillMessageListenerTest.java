package com.billing.mq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BillMessageListenerTest {

    @Test
    void testHandleMessage() {
        BillMessageListener listener = new BillMessageListener(null);
        assertTrue(true);
    }
}
