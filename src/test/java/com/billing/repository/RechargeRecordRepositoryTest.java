package com.billing.repository;

import com.billing.entity.recharge.RechargeRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 充值记录Repository测试
 */
@DataJpaTest
class RechargeRecordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RechargeRecordRepository repository;

    @Test
    void testSaveAndFind() {
        RechargeRecord record = new RechargeRecord();
        record.setTenantId(10L);
        record.setUserId(100L);
        record.setAmount(new BigDecimal("100.00"));
        record.setCurrency("CNY");
        record.setStatus("SUCCESS");
        record.setPaymentTime(LocalDateTime.now());

        entityManager.persist(record);
        entityManager.flush();

        RechargeRecord found = repository.findById(record.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("SUCCESS", found.getStatus());
    }
}
