package com.billing.controller;

import com.billing.common.Response;
import com.billing.dto.RechargeDTO;
import com.billing.entity.Account;
import com.billing.entity.recharge.RechargeRecord;
import com.billing.service.account.AccountService;
import com.billing.service.account.RechargeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 账户Controller测试
 */
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private RechargeService rechargeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setUserId(100L);
        testAccount.setBalance(new BigDecimal("100.00"));
        testAccount.setStatus("ACTIVE");
    }

    @Test
    void testGetAccount_Success() throws Exception {
        when(accountService.getAccountByUserId(100L)).thenReturn(testAccount);

        mockMvc.perform(get("/api/accounts/user/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.userId").value(100))
                .andExpect(jsonPath("$.data.balance").value(100.00));
    }

    @Test
    void testCreateRecharge_Success() throws Exception {
        RechargeDTO.CreateRequest request = new RechargeDTO.CreateRequest();
        request.setTenantId(10L);
        request.setUserId(100L);
        request.setAmount(new BigDecimal("100.00"));
        request.setCurrency("CNY");
        request.setPaymentMethod("ALIPAY");

        RechargeRecord record = new RechargeRecord();
        record.setId(1L);
        record.setUserId(100L);
        record.setAmount(new BigDecimal("100.00"));
        record.setStatus("PENDING");
        record.setCreatedTime(LocalDateTime.now());

        when(rechargeService.createRecharge(any())).thenReturn(record);

        mockMvc.perform(post("/api/accounts/recharge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1));
    }
}
