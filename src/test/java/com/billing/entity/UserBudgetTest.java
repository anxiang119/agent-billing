package com.billing.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户预算实体类测试
 */
class UserBudgetTest {

    @Test
    void testEntityCreation() {
        UserBudget budget = new UserBudget();
        budget.setId(1L);
        budget.setTenantId(10L);
        budget.setUserId(100L);
        budget.setBudgetType("MONTHLY");
        budget.setBudgetAmount(new BigDecimal("1000.00"));
        budget.setCurrency("CNY");
        budget.setStatus("ACTIVE");
        budget.setCreatedTime(LocalDateTime.now());

        assertNotNull(budget);
        assertEquals(1L, budget.getId());
        assertEquals("MONTHLY", budget.getBudgetType());
    }
}
