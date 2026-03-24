package com.billing.service.billing;

import com.billing.entity.PricingConfig;
import com.billing.service.promotion.PromotionCalculator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 费用计算器测试
 */
@ExtendWith(MockitoExtension.class)
class FeeCalculatorTest {

    @Mock
    private TieredFeeCalculator tieredFeeCalculator;

    @Mock
    private PromotionCalculator promotionCalculator;

    @InjectMocks
    private FeeCalculator feeCalculator;

    @Test
    void testCalculatePerUnitFee() {
        PricingConfig config = new PricingConfig();
        config.setPricingMode("PER_UNIT");
        config.setUnitPrice(new BigDecimal("0.01"));

        BigDecimal quantity = new BigDecimal("1000");
        BigDecimal fee = feeCalculator.calculate(config, quantity);

        assertEquals(new BigDecimal("10.00"), fee);
    }

    @Test
    void testCalculateMonthlyFee() {
        PricingConfig config = new PricingConfig();
        config.setPricingMode("MONTHLY");
        config.setMonthlyFee(new BigDecimal("100.00"));

        BigDecimal quantity = new BigDecimal("1000");
        BigDecimal fee = feeCalculator.calculate(config, quantity);

        assertEquals(new BigDecimal("100.00"), fee);
    }
}
