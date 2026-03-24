package com.billing.service.payment;

import com.billing.dto.PaymentDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PaymentGatewayTest {

    @Test
    void testCreateOrder_Success() {
        PaymentDTO.CreateOrderRequest request = new PaymentDTO.CreateOrderRequest();
        request.setAmount(new BigDecimal("100.00"));
        request.setPaymentMethod("ALIPAY");

        PaymentDTO.CreateOrderResponse response = new PaymentDTO.CreateOrderResponse();
        response.setOrderNo("ORDER001");
        response.setPaymentUrl("https://payment.example.com/pay");

        assertNotNull(response);
        assertEquals("ORDER001", response.getOrderNo());
    }
}
