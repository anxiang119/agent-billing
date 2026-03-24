package com.billing.repository;

import com.billing.entity.Bill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BillRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BillRepository repository;

    @Test
    void testSaveAndFind() {
        Bill bill = new Bill();
        bill.setTenantId(10L);
        bill.setUserId(100L);
        bill.setBillNo("BILL001");
        bill.setBillType("MONTHLY");
        bill.setPeriodStartTime(LocalDateTime.now().minusDays(30));
        bill.setPeriodEndTime(LocalDateTime.now());
        bill.setTotalAmount(new BigDecimal("100.00"));
        bill.setStatus("GENERATED");

        entityManager.persist(bill);
        entityManager.flush();

        Bill found = repository.findById(bill.getId()).orElse(null);
        assertNotNull(found, "Bill should be found");
        assertEquals("MONTHLY", found.getBillType());
    }

    @Test
    void testFindByBillNo() {
        Bill bill = new Bill();
        bill.setTenantId(10L);
        bill.setUserId(100L);
        bill.setBillNo("BILL001");
        bill.setBillType("MONTHLY");
        bill.setPeriodStartTime(LocalDateTime.now().minusDays(30));
        bill.setPeriodEndTime(LocalDateTime.now());
        bill.setTotalAmount(new BigDecimal("100.00"));
        bill.setStatus("GENERATED");

        entityManager.persist(bill);
        entityManager.flush();

        Bill found = repository.findByBillNo("BILL001");
        assertNotNull(found, "Bill should be found");
        assertEquals("BILL001", found.getBillNo());
    }

    @Test
    void testFindByTenantIdAndPeriod() {
        Bill bill = new Bill();
        bill.setTenantId(10L);
        bill.setUserId(100L);
        bill.setBillNo("BILL002");
        bill.setBillType("MONTHLY");
        bill.setPeriodStartTime(LocalDateTime.now().minusDays(30));
        bill.setPeriodEndTime(LocalDateTime.now());
        bill.setTotalAmount(new BigDecimal("200.00"));
        bill.setStatus("GENERATED");

        entityManager.persist(bill);
        entityManager.flush();

        var bills = repository.findByTenantIdAndPeriod(10L,
            LocalDateTime.now().minusDays(30),
            LocalDateTime.now());

        assertNotNull(bills, "Bill list should not be null");
        assertTrue(bills.size() >= 1, "Bill list should contain at least one element");
    }

    @Test
    void testDelete() {
        Bill bill = new Bill();
        bill.setTenantId(10L);
        bill.setUserId(100L);
        bill.setBillNo("BILL003");
        bill.setBillType("MONTHLY");
        bill.setPeriodStartTime(LocalDateTime.now().minusDays(30));
        bill.setPeriodEndTime(LocalDateTime.now());
        bill.setTotalAmount(new BigDecimal("100.00"));
        bill.setStatus("GENERATED");

        entityManager.persist(bill);
        entityManager.flush();

        Bill saved = repository.findById(bill.getId()).orElse(null);
        assertNotNull(saved, "Saved bill should not be null");

        repository.delete(saved);
        entityManager.flush();

        Bill found = repository.findById(bill.getId()).orElse(null);
        assertNull(found, "Bill should be deleted");
    }
}
