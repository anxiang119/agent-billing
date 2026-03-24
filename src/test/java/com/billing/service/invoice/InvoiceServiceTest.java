package com.billing.service.invoice;

import com.billing.common.Response;
import com.billing.entity.Invoice;
import com.billing.exception.BusinessException;
import com.billing.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository repository;

    @InjectMocks
    private InvoiceServiceImpl service;

    @Test
    void testApplyInvoice_Success() {
        Invoice invoice = new Invoice();
        invoice.setTenantId(10L);
        invoice.setUserId(100L);
        invoice.setBillId(1000L);
        invoice.setInvoiceType("ELECTRONIC");
        invoice.setAmount(new BigDecimal("100.00"));

        when(repository.save(any(Invoice.class))).thenAnswer(invocation -> {
            Invoice i = invocation.getArgument(0);
            i.setId(1L);
            return i;
        });

        Invoice result = service.applyInvoice(invoice);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}
