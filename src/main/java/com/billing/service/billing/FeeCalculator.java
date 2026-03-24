package com.billing.service.billing;

import com.billing.common.Response;
import com.billing.entity.PricingConfig;
import com.billing.exception.BusinessException;
import com.billing.service.promotion.PromotionCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class FeeCalculator {

    private final TieredFeeCalculator tieredFeeCalculator;
    private final PromotionCalculator promotionCalculator;

    public BigDecimal calculate(PricingConfig config, BigDecimal quantity) {
        if (config == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "定价配置不能为空");
        }

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "消费数量不能为空或小于0");
        }

        String pricingMode = config.getPricingMode();

        if ("PER_UNIT".equals(pricingMode)) {
            return calculatePerUnitFee(config, quantity);
        } else if ("TIERED".equals(pricingMode)) {
            return tieredFeeCalculator.calculate(config, quantity);
        } else if ("MONTHLY".equals(pricingMode)) {
            return calculateMonthlyFee(config);
        } else {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(),
                    "不支持的计费模式：" + pricingMode);
        }
    }

    private BigDecimal calculatePerUnitFee(PricingConfig config, BigDecimal quantity) {
        if (config.getUnitPrice() == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "单价不能为空");
        }

        return config.getUnitPrice().multiply(quantity);
    }

    private BigDecimal calculateMonthlyFee(PricingConfig config) {
        if (config.getMonthlyFee() == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "月费不能为空");
        }

        return config.getMonthlyFee();
    }
}
