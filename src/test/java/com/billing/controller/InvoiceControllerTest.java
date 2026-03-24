package com.billing.controller;

import com.billing.common.Response;
import com.billing.entity.Invoice;
import com.billing.service.invoice.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testApplyInvoice_Success() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setTenantId(10L);
        invoice.setUserId(100L);
        invoice.setBillId(1000L);
        invoice.setInvoiceType("ELECTRONIC");
        invoice.setAmount(new BigDecimal("100.00"));

        when(invoiceService.applyInvoice(any())).thenAnswer(invocation -> {
            Invoice i = invocation.getArgument(0);
            i.setId(1L);
            return i;
        });

        mockMvc.perform(post("/api/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoice)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1));
    }
}
