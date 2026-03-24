package com.billing.service.budget;

import com.billing.entity.UserBudget;
import com.billing.repository.UserBudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 预算Service测试
 */
@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    @Mock
    private UserBudgetRepository repository;

    @InjectMocks
    private BudgetServiceImpl service;

    @Test
    void testSetBudget_Success() {
        UserBudget budget = new UserBudget();
        budget.setTenantId(10L);
        budget.setUserId(100L);
        budget.setBudgetType("MONTHLY");
        budget.setBudgetAmount(new BigDecimal("1000.00"));
        budget.setCurrency("CNY");

        when(repository.save(any(UserBudget.class))).thenAnswer(invocation -> {
            UserBudget b = invocation.getArgument(0);
            b.setId(1L);
            return b;
        });

        UserBudget result = service.setBudget(budget);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(any(UserBudget.class));
    }

    @Test
    void testGetBudget_Success() {
        UserBudget budget = new UserBudget();
        budget.setId(1L);
        budget.setTenantId(10L);
        budget.setUserId(100L);
        budget.setBudgetType("MONTHLY");
        budget.setBudgetAmount(new BigDecimal("1000.00"));

        when(repository.findByUserIdAndBudgetType(100L, "MONTHLY")).thenReturn(budget);

        UserBudget result = service.getBudget(100L, "MONTHLY");

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}
