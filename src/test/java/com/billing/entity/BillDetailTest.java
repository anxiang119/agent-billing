package com.billing.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BillDetailTest {

    @Test
    void testEntityCreation() {
        BillDetail detail = new BillDetail();
        detail.setId(1L);
        detail.setBillId(100L);
        detail.setResourceType("API");
        detail.setResourceModel("GPT-4");
        detail.setQuantity(new BigDecimal("1000"));
        detail.setAmount(new BigDecimal("10.00"));
        detail.setCreatedTime(LocalDateTime.now());

        assertNotNull(detail);
        assertEquals(1L, detail.getId());
        assertEquals("API", detail.getResourceType());
    }
}
