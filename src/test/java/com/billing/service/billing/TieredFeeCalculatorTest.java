package com.billing.service.billing;

import com.billing.entity.PricingConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * 阶梯计费计算器测试
 */
@ExtendWith(MockitoExtension.class)
class TieredFeeCalculatorTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TieredFeeCalculator tieredFeeCalculator;

    @Test
    void testCalculateTiered() throws Exception {
        PricingConfig config = new PricingConfig();
        config.setTiers("[{\"min\":0,\"max\":1000,\"price\":0.01}]");

        TieredFeeCalculator.Tier tier = new TieredFeeCalculator.Tier();
        tier.setMin(new BigDecimal("0"));
        tier.setMax(new BigDecimal("1000"));
        tier.setPrice(new BigDecimal("0.01"));

        List<TieredFeeCalculator.Tier> tiers = Arrays.asList(tier);

        when(objectMapper.readValue(anyString(), any(TypeReference.class))).thenReturn(tiers);

        BigDecimal quantity = new BigDecimal("500");
        BigDecimal fee = tieredFeeCalculator.calculate(config, quantity);

        assertEquals(new BigDecimal("5.00"), fee);
    }

    @Test
    void testCalculateTieredFee_MultipleTiers() throws Exception {
        PricingConfig config = new PricingConfig();
        config.setTiers("[{\"min\":0,\"max\":1000,\"price\":0.01}," +
                       "{\"min\":1000,\"max\":10000,\"price\":0.008}]");

        TieredFeeCalculator.Tier tier1 = new TieredFeeCalculator.Tier();
        tier1.setMin(new BigDecimal("0"));
        tier1.setMax(new BigDecimal("1000"));
        tier1.setPrice(new BigDecimal("0.01"));

        TieredFeeCalculator.Tier tier2 = new TieredFeeCalculator.Tier();
        tier2.setMin(new BigDecimal("1000"));
        tier2.setMax(new BigDecimal("10000"));
        tier2.setPrice(new BigDecimal("0.008"));

        List<TieredFeeCalculator.Tier> tiers = Arrays.asList(tier1, tier2);

        when(objectMapper.readValue(anyString(), any(TypeReference.class))).thenReturn(tiers);

        BigDecimal quantity = new BigDecimal("1500");
        BigDecimal fee = tieredFeeCalculator.calculate(config, quantity);

        // 第一个阶梯：1000 * 0.01 = 10.00
        // 第二个阶梯：500 * 0.008 = 4.00
        // 总计：14.00
        assertEquals(0, new BigDecimal("14.00").compareTo(fee));
    }
}
