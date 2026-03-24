package com.billing.repository;

import com.billing.entity.PreDeductionRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 预扣记录Repository测试
 */
@DataJpaTest
class PreDeductionRecordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PreDeductionRecordRepository repository;

    @Test
    void testSaveAndFind() {
        PreDeductionRecord record = new PreDeductionRecord();
        record.setTenantId(10L);
        record.setUserId(100L);
        record.setResourceId("resource-001");
        record.setEstimatedAmount(new BigDecimal("10.00"));
        record.setPreDeductedAmount(new BigDecimal("10.00"));
        record.setStatus("DEDUCTED");
        record.setDeductionTime(LocalDateTime.now());

        entityManager.persist(record);
        entityManager.flush();

        PreDeductionRecord found = repository.findById(record.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("DEDUCTED", found.getStatus());
    }
}
