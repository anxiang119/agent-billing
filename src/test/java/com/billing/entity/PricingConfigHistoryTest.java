package com.billing.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 定价配置历史实体类测试
 */
class PricingConfigHistoryTest {

    @Test
    void testEntityCreation() {
        PricingConfigHistory history = new PricingConfigHistory();
        history.setId(1L);
        history.setTenantId(10L);
        history.setResourceType("API");
        history.setPricingMode("TIERED");
        history.setUnitPrice(new BigDecimal("0.01"));
        history.setOperationType("UPDATE");
        history.setOperatorId(100L);
        history.setOperationTime(LocalDateTime.now());

        assertNotNull(history);
        assertEquals(1L, history.getId());
        assertEquals("API", history.getResourceType());
        assertEquals("UPDATE", history.getOperationType());
    }
}
