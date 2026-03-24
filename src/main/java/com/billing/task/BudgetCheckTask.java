package com.billing.task;

import com.billing.repository.UserBudgetRepository;
import com.billing.service.budget.BudgetChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BudgetCheckTask {

    private final UserBudgetRepository userBudgetRepository;
    private final BudgetChecker budgetChecker;

    @Scheduled(cron = "0 0 */6 * * ?")
    public void checkBudgets() {
        // 每6小时检查一次预算
    }
}
