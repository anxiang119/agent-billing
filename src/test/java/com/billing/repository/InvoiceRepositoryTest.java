package com.billing.repository;

import com.billing.entity.Invoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InvoiceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InvoiceRepository repository;

    @Test
    void testSaveAndFind() {
        Invoice invoice = new Invoice();
        invoice.setTenantId(10L);
        invoice.setUserId(100L);
        invoice.setBillId(1000L);
        invoice.setInvoiceType("ELECTRONIC");
        invoice.setAmount(new BigDecimal("100.00"));
        invoice.setCurrency("CNY");

        entityManager.persist(invoice);
        entityManager.flush();

        Invoice found = repository.findById(invoice.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("ELECTRONIC", found.getInvoiceType());
    }
}
