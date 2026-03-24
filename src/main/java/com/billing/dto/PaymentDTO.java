package com.billing.dto;

import lombok.Data;

import java.math.BigDecimal;

public class PaymentDTO {

    @Data
    public static class CreateOrderRequest {
        private Long tenantId;
        private Long userId;
        private BigDecimal amount;
        private String currency;
        private String paymentMethod;
        private String subject;
        private String remark;
    }

    @Data
    public static class CreateOrderResponse {
        private String orderNo;
        private String paymentUrl;
        private String qrCode;
    }

    @Data
    public static class QueryOrderResponse {
        private String orderNo;
        private String status;
        private BigDecimal amount;
    }
}
