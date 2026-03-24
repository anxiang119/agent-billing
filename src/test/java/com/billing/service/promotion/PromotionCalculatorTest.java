package com.billing.service.promotion;

import com.billing.entity.Promotion;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 优惠计算器测试
 */
class PromotionCalculatorTest {

    @Test
    void testCalculateFullReductionDiscount() {
        PromotionCalculator calculator = new PromotionCalculator();

        Promotion promotion = new Promotion();
        promotion.setType("FULL_REDUCTION");
        promotion.setConditionAmount(new BigDecimal("100.00"));
        promotion.setDiscountAmount(new BigDecimal("10.00"));

        BigDecimal originalAmount = new BigDecimal("100.00");
        BigDecimal discount = calculator.calculate(promotion, originalAmount);

        assertEquals(new BigDecimal("10.00"), discount);
    }

    @Test
    void testCalculatePercentageDiscount() {
        PromotionCalculator calculator = new PromotionCalculator();

        Promotion promotion = new Promotion();
        promotion.setType("PERCENTAGE");
        promotion.setDiscountRate(new BigDecimal("0.8"));

        BigDecimal originalAmount = new BigDecimal("100.00");
        BigDecimal discount = calculator.calculate(promotion, originalAmount);

        assertEquals(new BigDecimal("20.00"), discount);
    }

    @Test
    void testFindBestPromotion() {
        PromotionCalculator calculator = new PromotionCalculator();

        Promotion promotion1 = new Promotion();
        promotion1.setType("FULL_REDUCTION");
        promotion1.setConditionAmount(new BigDecimal("100.00"));
        promotion1.setDiscountAmount(new BigDecimal("10.00"));

        Promotion promotion2 = new Promotion();
        promotion2.setType("FULL_REDUCTION");
        promotion2.setConditionAmount(new BigDecimal("50.00"));
        promotion2.setDiscountAmount(new BigDecimal("5.00"));

        List<Promotion> promotions = Arrays.asList(promotion1, promotion2);
        Promotion best = calculator.findBestPromotion(promotions, new BigDecimal("100.00"));

        assertNotNull(best);
        assertEquals(promotion1.getDiscountAmount(), best.getDiscountAmount());
    }
}
