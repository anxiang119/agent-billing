package com.billing.service.budget;

import com.billing.common.Response;
import com.billing.entity.UserBudget;
import com.billing.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 预算检查器
 * 提供预算检查和预警触发逻辑
 */
@Component
@RequiredArgsConstructor
public class BudgetChecker {

    /**
     * 检查结果
     */
    @lombok.Data
    public static class CheckResult {
        private boolean withinBudget;
        private boolean exceeded;
        private boolean alertTriggered;
        private BigDecimal usedAmount;
        private BigDecimal budgetAmount;
        private BigDecimal usageRate;
    }

    /**
     * 检查预算
     *
     * @param budget 预算
     * @param usedAmount 已使用金额
     * @return 检查结果
     */
    public CheckResult check(UserBudget budget, BigDecimal usedAmount) {
        if (budget == null) {
            return createDefaultResult();
        }

        if (usedAmount == null) {
            usedAmount = BigDecimal.ZERO;
        }

        CheckResult result = new CheckResult();

        BigDecimal budgetAmount = budget.getBudgetAmount();
        BigDecimal usageRate = calculateUsageRate(usedAmount, budgetAmount);

        result.setUsedAmount(usedAmount);
        result.setBudgetAmount(budgetAmount);
        result.setUsageRate(usageRate);

        boolean withinBudget = usedAmount.compareTo(budgetAmount) <= 0;
        result.setWithinBudget(withinBudget);
        result.setExceeded(!withinBudget);

        BigDecimal alertThreshold = budget.getAlertThreshold();
        if (alertThreshold != null) {
            boolean alertTriggered = usageRate.compareTo(alertThreshold) >= 0;
            result.setAlertTriggered(alertTriggered);
        } else {
            result.setAlertTriggered(!withinBudget);
        }

        return result;
    }

    /**
     * 计算使用率
     *
     * @param usedAmount 已使用金额
     * @param budgetAmount 预算金额
     * @return 使用率
     */
    private BigDecimal calculateUsageRate(BigDecimal usedAmount, BigDecimal budgetAmount) {
        if (budgetAmount == null || budgetAmount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return usedAmount.divide(budgetAmount, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
    }

    /**
     * 创建默认检查结果
     *
     * @return 检查结果
     */
    private CheckResult createDefaultResult() {
        CheckResult result = new CheckResult();
        result.setWithinBudget(true);
        result.setExceeded(false);
        result.setAlertTriggered(false);
        result.setUsedAmount(BigDecimal.ZERO);
        result.setBudgetAmount(BigDecimal.ZERO);
        result.setUsageRate(BigDecimal.ZERO);
        return result;
    }
}
