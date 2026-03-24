package com.billing.controller;

import com.billing.common.Response;
import com.billing.dto.RechargeDTO;
import com.billing.entity.Account;
import com.billing.entity.recharge.RechargeRecord;
import com.billing.service.account.AccountService;
import com.billing.service.account.RechargeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 账户Controller
 * 提供账户管理的REST API
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "账户管理", description = "账户相关接口")
public class AccountController {

    private final AccountService accountService;
    private final RechargeService rechargeService;

    @Operation(summary = "获取账户信息", description = "根据用户ID查询账户信息")
    @GetMapping("/user/{userId}")
    public Response<Account> getAccount(
            @Parameter(description = "用户ID", required = true)
            @PathVariable Long userId) {
        Account account = accountService.getAccountByUserId(userId);
        return Response.success(account);
    }

    @Operation(summary = "创建充值订单", description = "创建充值订单")
    @PostMapping("/recharge")
    public Response<RechargeRecord> createRecharge(@Valid @RequestBody RechargeDTO.CreateRequest request) {
        RechargeRecord record = rechargeService.createRecharge(request);
        return Response.success(record);
    }

    @Operation(summary = "处理支付回调", description = "处理第三方支付回调")
    @PostMapping("/recharge/callback")
    public Response<Void> handleCallback(@RequestBody RechargeDTO.CallbackRequest request) {
        rechargeService.handleCallback(request);
        return Response.success("处理成功", null);
    }

    @Operation(summary = "检查余额", description = "检查余额是否充足")
    @GetMapping("/user/{userId}/balance/check")
    public Response<Boolean> checkBalance(
            @Parameter(description = "用户ID", required = true)
            @PathVariable Long userId,
            @Parameter(description = "金额", required = true)
            @RequestParam java.math.BigDecimal amount) {
        boolean sufficient = accountService.checkBalance(userId, amount);
        return Response.success(sufficient);
    }
}
