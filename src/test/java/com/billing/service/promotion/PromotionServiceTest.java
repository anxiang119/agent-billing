package com.billing.service.promotion;

import com.billing.entity.Promotion;
import com.billing.repository.PromotionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 优惠Service测试
 */
@ExtendWith(MockitoExtension.class)
class PromotionServiceTest {

    @Mock
    private PromotionRepository repository;

    @InjectMocks
    private PromotionServiceImpl service;

    @Test
    void testCreatePromotion_Success() {
        Promotion promotion = new Promotion();
        promotion.setTenantId(10L);
        promotion.setName("双11优惠");
        promotion.setType("FULL_REDUCTION");
        promotion.setConditionAmount(new BigDecimal("100.00"));
        promotion.setDiscountAmount(new BigDecimal("10.00"));
        promotion.setEffectiveStartTime(LocalDateTime.now());

        when(repository.save(any(Promotion.class))).thenAnswer(invocation -> {
            Promotion p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

        Promotion result = service.createPromotion(promotion);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(any(Promotion.class));
    }

    @Test
    void testGetActivePromotions_Success() {
        Promotion promotion1 = new Promotion();
        promotion1.setId(1L);
        promotion1.setTenantId(10L);
        promotion1.setName("优惠1");
        promotion1.setType("FULL_REDUCTION");
        promotion1.setConditionAmount(new BigDecimal("100.00"));
        promotion1.setDiscountAmount(new BigDecimal("10.00"));

        Promotion promotion2 = new Promotion();
        promotion2.setId(2L);
        promotion2.setTenantId(10L);
        promotion2.setName("优惠2");
        promotion2.setType("FULL_REDUCTION");
        promotion2.setConditionAmount(new BigDecimal("50.00"));
        promotion2.setDiscountAmount(new BigDecimal("5.00"));

        List<Promotion> promotions = Arrays.asList(promotion1, promotion2);

        when(repository.findActivePromotions(any(), any())).thenReturn(promotions);

        List<Promotion> result = service.getActivePromotions(10L, LocalDateTime.now());

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository).findActivePromotions(any(), any());
    }
}
