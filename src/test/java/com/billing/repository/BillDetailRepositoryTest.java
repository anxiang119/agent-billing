package com.billing.repository;

import com.billing.entity.BillDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 账单明细Repository测试
 */
@DataJpaTest
class BillDetailRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BillDetailRepository repository;

    @Test
    void testSaveAndFind() {
        BillDetail detail = new BillDetail();
        detail.setBillId(100L);
        detail.setResourceType("API");
        detail.setResourceModel("GPT-4");
        detail.setQuantity(new BigDecimal("1000"));
        detail.setUnitPrice(new BigDecimal("0.01"));
        detail.setAmount(new BigDecimal("10.00"));
        detail.setActualAmount(new BigDecimal("10.00"));
        detail.setConsumptionTime(LocalDateTime.now());

        entityManager.persist(detail);
        entityManager.flush();

        BillDetail found = repository.findById(detail.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("API", found.getResourceType());
    }
}
