package com.billing.service.billing;

import com.billing.common.Response;
import com.billing.entity.PricingConfig;
import com.billing.exception.BusinessException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * 阶梯计费计算器
 * 提供阶梯计费的计算逻辑
 */
@Component
@RequiredArgsConstructor
public class TieredFeeCalculator {

    private final ObjectMapper objectMapper;

    /**
     * 阶梯配置
     */
    @lombok.Data
    public static class Tier {
        private BigDecimal min;
        private BigDecimal max;
        private BigDecimal price;
    }

    /**
     * 计算阶梯计费
     *
     * @param config 定价配置
     * @param quantity 消费数量
     * @return 费用
     */
    public BigDecimal calculate(PricingConfig config, BigDecimal quantity) {
        if (config == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "定价配置不能为空");
        }

        if (config.getTiers() == null || config.getTiers().trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "阶梯配置不能为空");
        }

        try {
            List<Tier> tiers = objectMapper.readValue(config.getTiers(), new TypeReference<List<Tier>>() {});

            BigDecimal totalFee = BigDecimal.ZERO;

            for (Tier tier : tiers) {
                if (quantity.compareTo(tier.getMin()) <= 0) {
                    continue;
                }

                BigDecimal tierStart = tier.getMin();
                BigDecimal tierEnd = tier.getMax() != null ? tier.getMax() : quantity;

                if (quantity.compareTo(tierStart) <= 0) {
                    continue;
                }

                BigDecimal tierQuantity = quantity.min(tierEnd).subtract(tierStart);
                BigDecimal tierFee = tierQuantity.multiply(tier.getPrice());

                totalFee = totalFee.add(tierFee);

                if (quantity.compareTo(tierEnd) <= 0) {
                    break;
                }
            }

            return totalFee;
        } catch (Exception e) {
            throw new BusinessException(Response.ResponseCode.SYSTEM_ERROR.getCode(),
                    "解析阶梯配置失败：" + e.getMessage(), e);
        }
    }
}
