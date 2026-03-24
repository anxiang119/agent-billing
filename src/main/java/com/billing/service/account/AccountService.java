package com.billing.service.account;

import com.billing.entity.Account;

import java.math.BigDecimal;

/**
 * 账户服务接口
 * 提供账户管理相关功能
 */
public interface AccountService {

    /**
     * 根据ID获取账户
     *
     * @param id 账户ID
     * @return 账户
     */
    Account getAccountById(Long id);

    /**
     * 根据租户ID和用户ID获取账户
     *
     * @param tenantId 租户ID
     * @param userId   用户ID
     * @return 账户
     */
    Account getAccountByTenantAndUser(Long tenantId, Long userId);

    /**
     * 检查账户余额
     *
     * @param accountId 账户ID
     * @param amount    需要的金额
     * @return 是否有足够余额
     */
    boolean checkBalance(Long accountId, BigDecimal amount);

    /**
     * 扣除账户余额
     *
     * @param accountId 账户ID
     * @param amount    扣除金额
     * @return 更新后的账户
     */
    Account deductBalance(Long accountId, BigDecimal amount);

    /**
     * 增加账户余额
     *
     * @param accountId 账户ID
     * @param amount    增加金额
     * @return 更新后的账户
     */
    Account addBalance(Long accountId, BigDecimal amount);

    /**
     * 创建账户
     *
     * @param account 账户信息
     * @return 创建的账户
     */
    Account createAccount(Account account);

    /**
     * 扣除账户余额
     *
     * @param accountId 账户ID
     * @param amount    扣除金额
     * @param remark    备注
     * @return 更新后的账户
     */
    Account deduct(Long accountId, BigDecimal amount, String remark);

    /**
     * 增加账户余额（退款）
     *
     * @param accountId 账户ID
     * @param amount    增加金额
     * @param remark    备注
     * @return 更新后的账户
     */
    Account refund(Long accountId, BigDecimal amount, String remark);

    /**
     * 根据用户ID获取账户
     *
     * @param userId 用户ID
     * @return 账户
     */
    Account getAccountByUserId(Long userId);

    /**
     * 充值
     *
     * @param accountId 账户ID
     * @param amount    充值金额
     * @return 充值后的账户
     */
    Account recharge(Long accountId, BigDecimal amount);
}
