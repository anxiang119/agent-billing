package com.billing.service.promotion;

import com.billing.common.Response;
import com.billing.entity.Promotion;
import com.billing.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PromotionCalculator {

    /**
     * 计算优惠金额
     *
     * @param promotion 优惠活动
     * @param originalAmount 原始金额
     * @return 优惠金额
     */
    public BigDecimal calculate(Promotion promotion, BigDecimal originalAmount) {
        if (promotion == null) {
            return BigDecimal.ZERO;
        }

        if (originalAmount == null || originalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        String type = promotion.getType();

        if ("FULL_REDUCTION".equals(type)) {
            return calculateFullReduction(promotion, originalAmount);
        } else if ("PERCENTAGE".equals(type)) {
            return calculatePercentage(promotion, originalAmount);
        } else {
            throw new BusinessException(Response.ResponseCode.SYSTEM_ERROR.getCode(),
                    "不支持的优惠类型：" + type);
        }
    }

    /**
     * 计算满减优惠
     *
     * @param promotion 优惠活动
     * @param originalAmount 原始金额
     * @return 优惠金额
     */
    private BigDecimal calculateFullReduction(Promotion promotion, BigDecimal originalAmount) {
        BigDecimal conditionAmount = promotion.getConditionAmount();

        if (conditionAmount == null || originalAmount.compareTo(conditionAmount) < 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal discountAmount = promotion.getDiscountAmount();
        return discountAmount != null ? discountAmount : BigDecimal.ZERO;
    }

    /**
     * 计算百分比折扣
     *
     * @param promotion 优惠活动
     * @param originalAmount 原始金额
     * @return 优惠金额
     */
    private BigDecimal calculatePercentage(Promotion promotion, BigDecimal originalAmount) {
        BigDecimal discountRate = promotion.getDiscountRate();

        if (discountRate == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal discount = originalAmount.multiply(BigDecimal.ONE.subtract(discountRate))
                .setScale(2, RoundingMode.HALF_UP);

        return discount;
    }

    /**
     * 找出最优优惠
     *
     * @param promotions 优惠活动列表
     * @param originalAmount 原始金额
     * @return 最优优惠活动
     */
    public Promotion findBestPromotion(List<Promotion> promotions, BigDecimal originalAmount) {
        if (promotions == null || promotions.isEmpty()) {
            return null;
        }

        return promotions.stream()
                .filter(p -> p.getConditionAmount() == null ||
                        originalAmount.compareTo(p.getConditionAmount()) >= 0)
                .max(Comparator.comparing(p -> calculate(p, originalAmount)))
                .orElse(null);
    }
}
