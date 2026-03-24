package com.billing.controller;

import com.billing.common.Response;
import com.billing.entity.UserBudget;
import com.billing.service.budget.BudgetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

/**
 * 预算Controller测试
 */
@WebMvcTest(BudgetController.class)
class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetService budgetService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSetBudget_Success() throws Exception {
        UserBudget budget = new UserBudget();
        budget.setTenantId(10L);
        budget.setUserId(100L);
        budget.setBudgetType("MONTHLY");
        budget.setBudgetAmount(new BigDecimal("1000.00"));
        budget.setCurrency("CNY");

        when(budgetService.setBudget(any())).thenAnswer(invocation -> {
            UserBudget b = invocation.getArgument(0);
            b.setId(1L);
            return b;
        });

        mockMvc.perform(post("/api/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(budget)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    void testGetBudget_Success() throws Exception {
        UserBudget budget = new UserBudget();
        budget.setId(1L);
        budget.setUserId(100L);
        budget.setBudgetType("MONTHLY");
        budget.setBudgetAmount(new BigDecimal("1000.00"));

        when(budgetService.getBudget(100L, "MONTHLY")).thenReturn(budget);

        mockMvc.perform(get("/api/budgets/user/100/MONTHLY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1));
    }
}
