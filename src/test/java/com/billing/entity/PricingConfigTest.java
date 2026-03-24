package com.billing.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 定价配置实体类测试
 */
class PricingConfigTest {

    @Test
    void testEntityCreation() {
        PricingConfig config = new PricingConfig();
        config.setId(1L);
        config.setTenantId(10L);
        config.setResourceType("API");
        config.setResourceModel("GPT-4");
        config.setPricingMode("TIERED");
        config.setUnitPrice(new BigDecimal("0.01"));
        config.setCurrency("CNY");
        config.setEffectiveStartTime(LocalDateTime.now());
        config.setEffectiveEndTime(LocalDateTime.now().plusDays(30));
        config.setStatus("ACTIVE");
        config.setCreatedTime(LocalDateTime.now());
        config.setUpdatedTime(LocalDateTime.now());

        assertNotNull(config);
        assertEquals(1L, config.getId());
        assertEquals("API", config.getResourceType());
        assertEquals("TIERED", config.getPricingMode());
    }

    @Test
    void testJsonFields() {
        PricingConfig config = new PricingConfig();
        String tiers = "[{\"min\":0,\"max\":1000,\"price\":0.01}]";
        config.setTiers(tiers);

        assertNotNull(config.getTiers());
        assertTrue(config.getTiers().contains("tiers"));
    }
}
