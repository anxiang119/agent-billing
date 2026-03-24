package com.billing.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 充值DTO测试
 */
class RechargeDTOTest {

    @Test
    void testCreateRequestDTO() {
        RechargeDTO.CreateRequest request = new RechargeDTO.CreateRequest();
        request.setTenantId(10L);
        request.setUserId(100L);
        request.setAmount(new BigDecimal("100.00"));
        request.setCurrency("CNY");
        request.setPaymentMethod("ALIPAY");

        assertNotNull(request);
        assertEquals(new BigDecimal("100.00"), request.getAmount());
        assertEquals("ALIPAY", request.getPaymentMethod());
    }

    @Test
    void testResponseDTO() {
        RechargeDTO.Response response = new RechargeDTO.Response();
        response.setId(1L);
        response.setAmount(new BigDecimal("100.00"));
        response.setStatus("SUCCESS");

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("SUCCESS", response.getStatus());
    }
}
