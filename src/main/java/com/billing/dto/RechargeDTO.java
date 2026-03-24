package com.billing.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 充值DTO
 * 定义充值相关的请求响应对象
 */
public class RechargeDTO {

    /**
     * 创建充值请求
     */
    @Data
    public static class CreateRequest {
        private Long tenantId;
        private Long userId;
        private BigDecimal amount;
        private String currency;
        private String paymentMethod;
        private String remark;
    }

    /**
     * 充值响应
     */
    @Data
    public static class Response {
        private Long id;
        private Long tenantId;
        private Long userId;
        private BigDecimal amount;
        private String currency;
        private String paymentMethod;
        private String paymentOrderNo;
        private String status;
        private LocalDateTime paymentTime;
        private LocalDateTime createdTime;
        private LocalDateTime updatedTime;
    }

    /**
     * 充值回调请求
     */
    @Data
    public static class CallbackRequest {
        private String paymentOrderNo;
        private String status;
        private String transactionId;
    }
}
