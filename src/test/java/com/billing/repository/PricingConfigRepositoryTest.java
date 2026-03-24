package com.billing.repository;

import com.billing.entity.PricingConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 定价配置Repository测试
 */
@DataJpaTest
class PricingConfigRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PricingConfigRepository repository;

    @Test
    void testFindByTenantIdAndResourceTypeAndModel() {
        PricingConfig config = new PricingConfig();
        config.setTenantId(10L);
        config.setResourceType("API");
        config.setResourceModel("GPT-4");
        config.setPricingMode("PER_UNIT");
        config.setUnitPrice(new BigDecimal("0.01"));
        config.setCurrency("CNY");
        config.setEffectiveStartTime(LocalDateTime.now());
        config.setStatus("ACTIVE");

        entityManager.persist(config);
        entityManager.flush();

        List<PricingConfig> result = repository.findByTenantIdAndResourceTypeAndResourceModelAndStatus(
                10L, "API", "GPT-4", "ACTIVE");

        assertFalse(result.isEmpty());
        assertEquals("API", result.get(0).getResourceType());
    }
}
