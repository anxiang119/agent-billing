package com.billing.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 定价配置DTO测试
 */
class PricingConfigDTOTest {

    @Test
    void testCreateRequestDTO() {
        PricingConfigDTO.CreateRequest request = new PricingConfigDTO.CreateRequest();
        request.setTenantId(10L);
        request.setResourceType("API");
        request.setResourceModel("GPT-4");
        request.setPricingMode("PER_UNIT");
        request.setUnitPrice(new BigDecimal("0.01"));
        request.setCurrency("CNY");

        assertNotNull(request);
        assertEquals("API", request.getResourceType());
        assertEquals("PER_UNIT", request.getPricingMode());
    }

    @Test
    void testUpdateRequestDTO() {
        PricingConfigDTO.UpdateRequest request = new PricingConfigDTO.UpdateRequest();
        request.setUnitPrice(new BigDecimal("0.02"));

        assertNotNull(request);
        assertEquals(new BigDecimal("0.02"), request.getUnitPrice());
    }

    @Test
    void testQueryRequestDTO() {
        PricingConfigDTO.QueryRequest request = new PricingConfigDTO.QueryRequest();
        request.setTenantId(10L);
        request.setResourceType("API");

        assertNotNull(request);
        assertEquals(10L, request.getTenantId());
    }

    @Test
    void testResponseDTO() {
        PricingConfigDTO.Response response = new PricingConfigDTO.Response();
        response.setId(1L);
        response.setResourceType("API");
        response.setResourceModel("GPT-4");

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }
}
