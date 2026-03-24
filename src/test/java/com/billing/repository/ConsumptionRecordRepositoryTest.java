package com.billing.repository;

import com.billing.entity.ConsumptionRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 消费记录Repository测试
 */
@DataJpaTest
class ConsumptionRecordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ConsumptionRecordRepository repository;

    @Test
    void testSaveAndFind() {
        ConsumptionRecord record = new ConsumptionRecord();
        record.setTenantId(10L);
        record.setUserId(100L);
        record.setResourceId("resource-001");
        record.setResourceType("API");
        record.setResourceModel("GPT-4");
        record.setQuantity(new BigDecimal("1000"));
        record.setUnitPrice(new BigDecimal("0.01"));
        record.setAmount(new BigDecimal("10.00"));
        record.setCurrency("CNY");
        record.setStatus("PENDING");
        record.setConsumptionTime(LocalDateTime.now());

        entityManager.persist(record);
        entityManager.flush();

        ConsumptionRecord found = repository.findById(record.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("API", found.getResourceType());
    }
}
