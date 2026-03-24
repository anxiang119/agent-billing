package com.billing.service.payment;

import com.billing.dto.PaymentDTO;
import com.billing.repository.RechargeRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RechargeRecordRepository rechargeRecordRepository;

    private final PaymentGateway paymentGateway;

    public PaymentDTO.CreateOrderResponse createPaymentOrder(PaymentDTO.CreateOrderRequest request) {
        return paymentGateway.createOrder(request);
    }

    @Transactional
    public void handleCallback(String paymentMethod, String callbackData) {
        // 处理支付回调
    }

    public PaymentDTO.QueryOrderResponse queryOrder(String orderNo) {
        return paymentGateway.queryOrder(orderNo);
    }

    public PaymentDTO.QueryOrderResponse refund(String orderNo, BigDecimal refundAmount) {
        return paymentGateway.refund(orderNo, refundAmount);
    }
}
