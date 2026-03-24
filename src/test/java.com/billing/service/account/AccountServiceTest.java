package com.billing.service.account;

import com.billing.entity.Account;
import com.billing.exception.BusinessException;
import com.billing.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 账户Service测试
 */
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setUserId(100L);
        testAccount.setBalance(new BigDecimal("100.00"));
        testAccount.setCurrency("CNY");
        testAccount.setStatus("ACTIVE");
        testAccount.setVersion(1L);
        testAccount.setCreatedTime(LocalDateTime.now());
        testAccount.setUpdatedTime(LocalDateTime.now());
    }

    @Test
    void testGetAccountByUserId_Success() {
        when(accountRepository.findByUserId(100L)).thenReturn(Optional.of(testAccount));

        Account result = accountService.getAccountByUserId(100L);

        assertNotNull(result);
        assertEquals(100L, result.getUserId());
        verify(accountRepository).findByUserId(100L);
    }

    @Test
    void testDeduct_Success() {
        when(accountRepository.findByUserId(100L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BigDecimal newBalance = accountService.deduct(100L, new BigDecimal("10.00"), "resource-001");

        assertEquals(new BigDecimal("90.00"), newBalance);
        verify(accountRepository).save(any(Account.class));
    }
}
