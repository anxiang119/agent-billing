package com.billing.dto;

import com.billing.entity.PricingConfig;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 定价配置DTO
 * 定义创建和查询定价配置的请求响应对象
 */
public class PricingConfigDTO {

    /**
     * 创建定价配置请求
     */
    @Data
    public static class CreateRequest {
        private Long tenantId;
        private String resourceType;
        private String resourceModel;
        private String pricingMode;
        private BigDecimal unitPrice;
        private String currency;
        private String tiers;
        private BigDecimal monthlyFee;
        private LocalDateTime effectiveStartTime;
        private LocalDateTime effectiveEndTime;
    }

    /**
     * 更新定价配置请求
     */
    @Data
    public static class UpdateRequest {
        private String pricingMode;
        private BigDecimal unitPrice;
        private String currency;
        private String tiers;
        private BigDecimal monthlyFee;
        private LocalDateTime effectiveStartTime;
        private LocalDateTime effectiveEndTime;
        private String status;
    }

    /**
     * 查询定价配置请求
     */
    @Data
    public static class QueryRequest {
        private Long tenantId;
        private String resourceType;
        private String resourceModel;
        private String status;
        private LocalDateTime effectiveStartTime;
        private LocalDateTime effectiveEndTime;
    }

    /**
     * 定价配置响应
     */
    @Data
    public static class Response {
        private Long id;
        private Long tenantId;
        private String resourceType;
        private String resourceModel;
        private String pricingMode;
        private BigDecimal unitPrice;
        private String currency;
        private String tiers;
        private BigDecimal monthlyFee;
        private LocalDateTime effectiveStartTime;
        private LocalDateTime effectiveEndTime;
        private String status;
        private LocalDateTime createdTime;
        private LocalDateTime updatedTime;

        /**
         * 从实体转换为响应DTO
         */
        public static Response fromEntity(PricingConfig config) {
            Response response = new Response();
            BeanUtils.copyProperties(config, response);
            return response;
        }
    }
}
