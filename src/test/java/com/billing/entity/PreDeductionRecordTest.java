package com.billing.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 预扣记录实体类测试
 */
class PreDeductionRecordTest {

    @Test
    void testEntityCreation() {
        PreDeductionRecord record = new PreDeductionRecord();
        record.setId(1L);
        record.setTenantId(10L);
        record.setUserId(100L);
        record.setResourceId("resource-001");
        record.setEstimatedAmount(new BigDecimal("10.00"));
        record.setPreDeductedAmount(new BigDecimal("10.00"));
        record.setStatus("DEDUCTED");
        record.setCreatedTime(LocalDateTime.now());

        assertNotNull(record);
        assertEquals(1L, record.getId());
        assertEquals("DEDUCTED", record.getStatus());
    }
}
