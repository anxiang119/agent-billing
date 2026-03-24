package com.billing.service.payment;

import com.billing.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Primary
@RequiredArgsConstructor
public class AlipayPaymentImpl implements PaymentGateway {

    @Override
    public PaymentDTO.CreateOrderResponse createOrder(PaymentDTO.CreateOrderRequest request) {
        PaymentDTO.CreateOrderResponse response = new PaymentDTO.CreateOrderResponse();
        response.setOrderNo("ALI" + System.currentTimeMillis());
        return response;
    }

    @Override
    public PaymentDTO.QueryOrderResponse queryOrder(String orderNo) {
        PaymentDTO.QueryOrderResponse response = new PaymentDTO.QueryOrderResponse();
        response.setOrderNo(orderNo);
        response.setStatus("SUCCESS");
        return response;
    }

    @Override
    public PaymentDTO.QueryOrderResponse refund(String orderNo, BigDecimal refundAmount) {
        PaymentDTO.QueryOrderResponse response = new PaymentDTO.QueryOrderResponse();
        response.setOrderNo(orderNo);
        response.setStatus("SUCCESS");
        return response;
    }
}
