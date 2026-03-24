package com.billing.repository;

import com.billing.entity.UserBudget;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户预算Repository测试
 */
@DataJpaTest
class UserBudgetRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserBudgetRepository repository;

    @Test
    void testSaveAndFind() {
        UserBudget budget = new UserBudget();
        budget.setTenantId(10L);
        budget.setUserId(100L);
        budget.setBudgetType("MONTHLY");
        budget.setBudgetAmount(new BigDecimal("1000.00"));
        budget.setCurrency("CNY");
        budget.setStatus("ACTIVE");

        entityManager.persist(budget);
        entityManager.flush();

        UserBudget found = repository.findById(budget.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("MONTHLY", found.getBudgetType());
    }
}
