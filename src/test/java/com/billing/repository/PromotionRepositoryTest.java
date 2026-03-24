package com.billing.repository;

import com.billing.entity.Promotion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 优惠活动Repository测试
 */
@DataJpaTest
class PromotionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PromotionRepository repository;

    @Test
    void testSaveAndFind() {
        Promotion promotion = new Promotion();
        promotion.setTenantId(10L);
        promotion.setName("双11优惠");
        promotion.setType("FULL_REDUCTION");
        promotion.setConditionAmount(new BigDecimal("100.00"));
        promotion.setDiscountAmount(new BigDecimal("10.00"));
        promotion.setEffectiveStartTime(LocalDateTime.now());
        promotion.setStatus("ACTIVE");

        entityManager.persist(promotion);
        entityManager.flush();

        Promotion found = repository.findById(promotion.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("双11优惠", found.getName());
    }
}
