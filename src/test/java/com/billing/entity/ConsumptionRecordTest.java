package com.billing.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 消费记录实体类测试
 */
class ConsumptionRecordTest {

    @Test
    void testEntityCreation() {
        ConsumptionRecord record = new ConsumptionRecord();
        record.setId(1L);
        record.setTenantId(10L);
        record.setUserId(100L);
        record.setResourceId("resource-001");
        record.setResourceType("API");
        record.setResourceModel("GPT-4");
        record.setQuantity(new BigDecimal("1000"));
        record.setUnitPrice(new BigDecimal("0.01"));
        record.setAmount(new BigDecimal("10.00"));
        record.setCurrency("CNY");
        record.setStatus("SETTLED");
        record.setCreatedTime(LocalDateTime.now());

        assertNotNull(record);
        assertEquals(1L, record.getId());
        assertEquals("API", record.getResourceType());
        assertEquals("SETTLED", record.getStatus());
    }
}
