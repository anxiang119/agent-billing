package com.billing.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    @Test
    void testEntityCreation() {
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setTenantId(10L);
        invoice.setUserId(100L);
        invoice.setBillId(1000L);
        invoice.setInvoiceType("ELECTRONIC");
        invoice.setAmount(new BigDecimal("100.00"));
        invoice.setCurrency("CNY");
        invoice.setStatus("ISSUED");
        invoice.setCreatedTime(LocalDateTime.now());

        assertNotNull(invoice);
        assertEquals(1L, invoice.getId());
        assertEquals("ELECTRONIC", invoice.getInvoiceType());
    }
}
