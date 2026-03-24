package com.billing.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BillTest {

    @Test
    void testEntityCreation() {
        Bill bill = new Bill();
        bill.setId(1L);
        bill.setTenantId(10L);
        bill.setUserId(100L);
        bill.setBillType("MONTHLY");
        bill.setTotalAmount(new BigDecimal("100.00"));
        bill.setCurrency("CNY");
        bill.setStatus("GENERATED");
        bill.setCreatedTime(LocalDateTime.now());

        assertNotNull(bill);
        assertEquals(1L, bill.getId());
        assertEquals("MONTHLY", bill.getBillType());
    }
}
