package com.billing.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 优惠活动实体类测试
 */
class PromotionTest {

    @Test
    void testEntityCreation() {
        Promotion promotion = new Promotion();
        promotion.setId(1L);
        promotion.setTenantId(10L);
        promotion.setName("双11优惠");
        promotion.setType("FULL_REDUCTION");
        promotion.setConditionAmount(new BigDecimal("100.00"));
        promotion.setDiscountAmount(new BigDecimal("10.00"));
        promotion.setStatus("ACTIVE");
        promotion.setCreatedTime(LocalDateTime.now());

        assertNotNull(promotion);
        assertEquals(1L, promotion.getId());
        assertEquals("双11优惠", promotion.getName());
        assertEquals("FULL_REDUCTION", promotion.getType());
    }
}
