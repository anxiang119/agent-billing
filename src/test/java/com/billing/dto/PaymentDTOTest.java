package com.billing.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDTOTest {

    @Test
    void testCreateOrderRequest() {
        PaymentDTO.CreateOrderRequest request = new PaymentDTO.CreateOrderRequest();
        request.setTenantId(10L);
        request.setUserId(100L);
        request.setAmount(new BigDecimal("100.00"));
        request.setPaymentMethod("ALIPAY");

        assertNotNull(request);
        assertEquals(new BigDecimal("100.00"), request.getAmount());
        assertEquals("ALIPAY", request.getPaymentMethod());
    }
}
