package com.billing.controller;

import com.billing.common.Response;
import com.billing.entity.Bill;
import com.billing.service.bill.BillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 账单Controller测试
 */
@WebMvcTest(BillController.class)
class BillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillService billService;

    @Test
    void testGetBillById_Success() throws Exception {
        Bill bill = new Bill();
        bill.setId(1L);
        bill.setTenantId(10L);
        bill.setUserId(100L);
        bill.setBillType("MONTHLY");
        bill.setTotalAmount(new BigDecimal("100.00"));

        when(billService.getBillById(1L)).thenReturn(bill);

        mockMvc.perform(get("/api/bills/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    void testGenerateBill_Success() throws Exception {
        Bill bill = new Bill();
        bill.setId(1L);
        bill.setTenantId(10L);
        bill.setUserId(100L);
        bill.setBillType("MONTHLY");

        when(billService.generateMonthlyBill(any(), any())).thenReturn(bill);

        mockMvc.perform(post("/api/bills/generate")
                        .param("tenantId", "10")
                        .param("userId", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1));
    }
}
