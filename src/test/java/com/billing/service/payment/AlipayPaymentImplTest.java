package com.billing.service.payment;

import com.billing.dto.PaymentDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AlipayPaymentImplTest {

    @Test
    void testCreateOrder_Success() {
        AlipayPaymentImpl payment = new AlipayPaymentImpl();

        PaymentDTO.CreateOrderRequest request = new PaymentDTO.CreateOrderRequest();
        request.setAmount(new BigDecimal("100.00"));

        PaymentDTO.CreateOrderResponse response = payment.createOrder(request);

        assertNotNull(response);
        assertNotNull(response.getOrderNo());
        assertTrue(response.getOrderNo().startsWith("ALI"));
    }

    @Test
    void testQueryOrder_Success() {
        AlipayPaymentImpl payment = new AlipayPaymentImpl();

        PaymentDTO.QueryOrderResponse response = payment.queryOrder("TEST_ORDER_001");

        assertNotNull(response);
        assertEquals("TEST_ORDER_001", response.getOrderNo());
        assertEquals("SUCCESS", response.getStatus());
    }

    @Test
    void testRefund_Success() {
        AlipayPaymentImpl payment = new AlipayPaymentImpl();

        PaymentDTO.QueryOrderResponse response = payment.refund("TEST_ORDER_002", new BigDecimal("50.00"));

        assertNotNull(response);
        assertEquals("TEST_ORDER_002", response.getOrderNo());
        assertEquals("SUCCESS", response.getStatus());
    }
}
