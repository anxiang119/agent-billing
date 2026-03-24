package com.billing.service.payment;

import com.billing.dto.PaymentDTO;

import java.math.BigDecimal;

public interface PaymentGateway {
    PaymentDTO.CreateOrderResponse createOrder(PaymentDTO.CreateOrderRequest request);
    PaymentDTO.QueryOrderResponse queryOrder(String orderNo);
    PaymentDTO.QueryOrderResponse refund(String orderNo, BigDecimal refundAmount);
}
