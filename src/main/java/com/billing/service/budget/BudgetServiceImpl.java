package com.billing.service.budget;

import com.billing.common.Response;
import com.billing.entity.UserBudget;
import com.billing.exception.BusinessException;
import com.billing.repository.UserBudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 预算Service实现
 */
@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final UserBudgetRepository repository;

    @Override
    @Transactional
    public UserBudget setBudget(UserBudget budget) {
        if (budget == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "预算信息不能为空");
        }

        if (budget.getUserId() == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户ID不能为空");
        }

        if (budget.getBudgetType() == null || budget.getBudgetType().trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "预算类型不能为空");
        }

        if (budget.getBudgetAmount() == null || budget.getBudgetAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "预算金额必须大于0");
        }

        return repository.save(budget);
    }

    @Override
    public UserBudget getBudget(Long userId, String budgetType) {
        if (userId == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户ID不能为空");
        }

        if (budgetType == null || budgetType.trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "预算类型不能为空");
        }

        return repository.findByUserIdAndBudgetType(userId, budgetType);
    }

    @Override
    @Transactional
    public void deleteBudget(Long id) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "预算ID不能为空");
        }

        UserBudget budget = repository.findById(id)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.NOT_FOUND.getCode(), "预算不存在"));

        repository.delete(budget);
    }

    @Override
    @Transactional
    public void restoreService(Long userId) {
        if (userId == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户ID不能为空");
        }

        // 实际实现中，这里会恢复因预算超限而停止的服务
        // 目前只是简单实现，返回成功
    }
}
