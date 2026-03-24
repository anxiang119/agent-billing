package com.billing.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 计费DTO
 * 定义计费相关的请求响应对象
 */
public class BillingDTO {

    /**
     * 预估费用请求
     */
    @Data
    public static class EstimateRequest {
        private Long tenantId;
        private Long userId;
        private String resourceId;
        private String resourceType;
        private String resourceModel;
        private BigDecimal quantity;
    }

    /**
     * 预估费用响应
     */
    @Data
    public static class EstimateResponse {
        private Long tenantId;
        private Long userId;
        private String resourceId;
        private String resourceType;
        private String resourceModel;
        private BigDecimal quantity;
        private BigDecimal estimatedFee;
        private String currency;
    }

    /**
     * 预扣费用请求
     */
    @Data
    public static class PreDeductRequest extends EstimateRequest {
    }

    /**
     * 预扣费用响应
     */
    @Data
    public static class PreDeductResponse {
        private Long preDeductionId;
        private Boolean success;
        private BigDecimal deductedAmount;
        private BigDecimal balance;
        private String currency;
    }

    /**
     * 结算费用请求
     */
    @Data
    public static class SettleRequest extends EstimateRequest {
    }

    /**
     * 结算费用响应
     */
    @Data
    public static class SettleResponse {
        private Long consumptionRecordId;
        private Long preDeductionId;
        private BigDecimal actualAmount;
        private BigDecimal refundAmount;
        private Boolean success;
    }
}
