package com.billing.repository;

import com.billing.entity.AlertRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 预警记录Repository测试
 */
@DataJpaTest
class AlertRecordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AlertRecordRepository repository;

    @Test
    void testSaveAndFind() {
        AlertRecord alert = new AlertRecord();
        alert.setTenantId(10L);
        alert.setUserId(100L);
        alert.setAlertType("BUDGET_EXCEED");
        alert.setBudgetAmount(new BigDecimal("1000.00"));
        alert.setUsedAmount(new BigDecimal("900.00"));
        alert.setUsageRate(new BigDecimal("90.00"));

        entityManager.persist(alert);
        entityManager.flush();

        AlertRecord found = repository.findById(alert.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("BUDGET_EXCEED", found.getAlertType());
    }
}
