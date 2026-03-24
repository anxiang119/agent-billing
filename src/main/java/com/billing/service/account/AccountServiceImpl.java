package com.billing.service.account;

import com.billing.common.Response;
import com.billing.entity.Account;
import com.billing.exception.BusinessException;
import com.billing.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 账户Service实现
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account getAccountById(Long id) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "账户ID不能为空");
        }

        return accountRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.NOT_FOUND.getCode(), "账户不存在"));
    }

    @Override
    public Account getAccountByTenantAndUser(Long tenantId, Long userId) {
        if (tenantId == null || userId == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "租户ID和用户ID不能为空");
        }

        return accountRepository.findByTenantIdAndUserId(tenantId, userId)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.NOT_FOUND.getCode(), "账户不存在"));
    }

    @Override
    public Account getAccountByUserId(Long userId) {
        if (userId == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户ID不能为空");
        }

        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.NOT_FOUND.getCode(), "账户不存在"));
    }

    @Override
    public boolean checkBalance(Long accountId, BigDecimal amount) {
        if (accountId == null || amount == null) {
            return false;
        }

        Optional<Account> accountOpt = accountRepository.findById(accountId);

        return accountOpt.map(account -> account.getBalance().compareTo(amount) >= 0).orElse(false);
    }

    @Override
    @Transactional
    public Account deductBalance(Long accountId, BigDecimal amount) {
        if (accountId == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "账户ID不能为空");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "扣减金额必须大于0");
        }

        Account account = getAccountById(accountId);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new BusinessException(Response.ResponseCode.INSUFFICIENT_BALANCE.getCode(), "账户余额不足");
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);

        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account addBalance(Long accountId, BigDecimal amount) {
        if (accountId == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "账户ID不能为空");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "增加金额必须大于0");
        }

        Account account = getAccountById(accountId);

        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);

        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account createAccount(Account account) {
        if (account == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "账户信息不能为空");
        }

        if (account.getTenantId() == null || account.getUserId() == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "租户ID和用户ID不能为空");
        }

        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account deduct(Long accountId, BigDecimal amount, String remark) {
        return deductBalance(accountId, amount);
    }

    @Override
    @Transactional
    public Account refund(Long accountId, BigDecimal amount, String remark) {
        return addBalance(accountId, amount);
    }

    @Override
    @Transactional
    public Account recharge(Long accountId, BigDecimal amount) {
        return addBalance(accountId, amount);
    }
}
