package com.billing.service.account;

import com.billing.entity.Account;

import java.math.BigDecimal;

/**
 * 账户Service接口
 * 提供余额管理、充值、扣减等功能
 */
public interface AccountService {

    /**
     * 根据用户ID获取账户
     *
     * @param userId 用户ID
     * @return 账户信息
     */
    Account getAccountByUserId(Long userId);

    /**
     * 扣减余额
     *
     * @param userId 用户ID
     * @param amount 扣减金额
     * @param resourceId 资源ID（用于分布式锁）
     * @return 扣减后的余额
     */
    BigDecimal deduct(Long userId, BigDecimal amount, String resourceId);

    /**
     * 充值余额
     *
     * @param userId 用户ID
     * @param amount 充值金额
     * @return 充值后的余额
     */
    BigDecimal recharge(Long userId, BigDecimal amount);

    /**
     * 退款余额
     *
     * @@param userId 用户ID
     * @param amount 退款金额
     * @param resourceId 资源ID（用于分布式锁）
     * @return 退款后的余额
     */
    BigDecimal refund(Long userId, BigDecimal amount, String resourceId);

    /**
     * 检查余额是否充足
     *
     * @param userId 用户ID
     * @param amount 金额
     * @return 是否充足
     */
    boolean checkBalance(Long userId, BigDecimal amount);
}
