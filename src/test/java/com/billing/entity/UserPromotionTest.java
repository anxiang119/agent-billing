package com.billing.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户优惠记录实体类测试
 */
class UserPromotionTest {

    @Test
    void testEntityCreation() {
        UserPromotion userPromotion = new UserPromotion();
        userPromotion.setId(1L);
        userPromotion.setTenantId(10L);
        userPromotion.setUserId(100L);
        userPromotion.setPromotionId(1000L);
        userPromotion.setStatus("userd");
        userPromotion.setUsedTime(LocalDateTime.now());
        userPromotion.setCreatedTime(LocalDateTime.now());

        assertNotNull(userPromotion);
        assertEquals(1L, userPromotion.getId());
        assertEquals("USED", userPromotion.getStatus());
    }
}
