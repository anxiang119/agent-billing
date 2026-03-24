package com.billing.controller;

import com.billing.common.Response;
import com.billing.entity.UserBudget;
import com.billing.service.budget.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 预算Controller
 * 提供预算管理的REST API
 */
@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@Tag(name = "预算管理", description = "预算相关接口")
public class BudgetController {

    private final BudgetService budgetService;

    @Operation(summary = "设置预算", description = "设置用户预算")
    @PostMapping
    public Response<UserBudget> setBudget(@Valid @RequestBody UserBudget budget) {
        UserBudget result = budgetService.setBudget(budget);
        return Response.success(result);
    }

    @Operation(summary = "获取预算", description = "根据用户ID和预算类型查询预算")
    @GetMapping("/user/{userId}/{budgetType}")
    public Response<UserBudget> getBudget(
            @Parameter(description = "用户ID", required = true)
            @PathVariable Long userId,
            @Parameter(description = "预算类型", required = true)
            @PathVariable String budgetType) {
        UserBudget budget = budgetService.getBudget(userId, budgetType);
        return Response.success(budget);
    }

    @Operation(summary = "删除预算", description = "根据ID删除预算")
    @DeleteMapping("/{id}")
    public Response<Void> deleteBudget(
            @Parameter(description = "预算ID", required = true)
            @PathVariable Long id) {
        budgetService.deleteBudget(id);
        return Response.success("删除成功", null);
    }

    @Operation(summary = "恢复服务", description = "恢复因预算超限而停止的服务")
    @PostMapping("/user/{userId}/restore")
    public Response<Void> restoreService(
            @Parameter(description = "用户ID", required = true)
            @PathVariable Long userId) {
        budgetService.restoreService(userId);
        return Response.success("恢复成功", null);
    }
}
