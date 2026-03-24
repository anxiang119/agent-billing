package com.billing.service.payment;

import com.billing.dto.PaymentDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AlipayPaymentImplTest {

    @Test
    void testCreateOrder_Success() {
        AlipayPaymentImpl payment = new AlipayPaymentImpl(null);

        PaymentDTO.CreateOrderRequest request = new PaymentDTO.CreateOrderRequest();
        request.setAmount(new BigDecimal("100.00"));

        PaymentDTO.CreateOrderResponse response = payment.createOrder(request);

        assertNotNull(response);
        assertNotNull(response.getOrderNo());
    }
}
