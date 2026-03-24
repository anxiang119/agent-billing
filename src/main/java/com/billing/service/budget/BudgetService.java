package com.billing.service.budget;

import com.billing.entity.UserBudget;

import java.math.BigDecimal;

/**
 * 预算Service接口
 * 提供预算管理业务逻辑
 */
public interface BudgetService {

    /**
     * 设置预算
     *
     * @param budget 预算信息
     * @return 预算
     */
    UserBudget setBudget(UserBudget budget);

    /**
     * 获取预算
     *
     * @param userId 用户ID
     * @param budgetType 预算类型
     * @return 预算
     */
    UserBudget getBudget(Long userId, String budgetType);

    /**
     * 删除预算
     *
     * @param id 预算ID
     */
    void deleteBudget(Long id);

    /**
     * 恢复服务（当服务因预算超限被停止时）
     *
     * @param userId 用户ID
     */
    void restoreService(Long userId);
}
