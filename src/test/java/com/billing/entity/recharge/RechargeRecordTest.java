package com.billing.entity.recharge;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 充值记录实体类测试
 */
class RechargeRecordTest {

    @Test
    void testEntityCreation() {
        RechargeRecord record = new RechargeRecord();
        record.setId(1L);
        record.setTenantId(10L);
        record.setUserId(100L);
        record.setAmount(new BigDecimal("100.00"));
        record.setCurrency("CNY");
        record.setStatus("SUCCESS");
        record.setCreatedTime(LocalDateTime.now());

        assertNotNull(record);
        assertEquals(1L, record.getId());
        assertEquals("SUCCESS", record.getStatus());
    }
}
