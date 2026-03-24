package com.billing.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 预警记录实体类测试
 */
class AlertRecordTest {

    @Test
    void testEntityCreation() {
        AlertRecord alert = new AlertRecord();
        alert.setId(1L);
        alert.setTenantId(10L);
        alert.setUserId(100L);
        alert.setAlertType("BUDGET_EXCEED");
        alert.setBudgetAmount(new BigDecimal("1000.00"));
        alert.setUsedAmount(new BigDecimal("900.00"));
        alert.setUsageRate(new BigDecimal("90.00"));
        alert.setCreatedTime(LocalDateTime.now());

        assertNotNull(alert);
        assertEquals(1L, alert.getId());
        assertEquals("BUDGET_EXCEED", alert.getAlertType());
    }
}
