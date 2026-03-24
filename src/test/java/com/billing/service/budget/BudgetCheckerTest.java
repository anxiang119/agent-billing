package com.billing.service.budget;

import com.billing.entity.UserBudget;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 预算检查器测试
 */
class BudgetCheckerTest {

    @Test
    void testCheckBudget_Sufficient() {
        BudgetChecker checker = new BudgetChecker();

        UserBudget budget = new UserBudget();
        budget.setBudgetAmount(new BigDecimal("1000.00"));
        budget.setAlertThreshold(new BigDecimal("80.00"));
        budget.setExceedAction("ALERT");

        BudgetChecker.CheckResult result = checker.check(budget, new BigDecimal("500.00"));

        assertTrue(result.isWithinBudget());
        assertFalse(result.isExceeded());
        assertFalse(result.isAlertTriggered());
    }

    @Test
    void testCheckBudget_AlertTriggered() {
        BudgetChecker checker = new BudgetChecker();

        UserBudget budget = new UserBudget();
        budget.setBudgetAmount(new BigDecimal("1000.00"));
        budget.setAlertThreshold(new BigDecimal("80.00"));
        budget.setExceedAction("ALERT");

        BudgetChecker.CheckResult result = checker.check(budget, new BigDecimal("850.00"));

        assertTrue(result.isWithinBudget());
        assertFalse(result.isExceeded());
        assertTrue(result.isAlertTriggered());
    }

    @Test
    void testCheckBudget_Exceeded() {
        BudgetChecker checker = new BudgetChecker();

        UserBudget budget = new UserBudget();
        budget.setBudgetAmount(new BigDecimal("1000.00"));
        budget.setAlertThreshold(new BigDecimal("80.00"));
        budget.setExceedAction("STOP");

        BudgetChecker.CheckResult result = checker.check(budget, new BigDecimal("1100.00"));

        assertFalse(result.isWithinBudget());
        assertTrue(result.isExceeded());
        assertTrue(result.isAlertTriggered());
    }
}
