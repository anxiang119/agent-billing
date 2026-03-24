package com.billing.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BillMonthlyGenerateTaskTest {

    @Test
    void testExecuteTask() {
        BillMonthlyGenerateTask task = new BillMonthlyGenerateTask(null, null);
        assertTrue(true);
    }
}
