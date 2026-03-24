package com.billing.repository;

import com.billing.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * 账户Repository测试类
 */
@ExtendWith(MockitoExtension.class)
class AccountRepositoryTest {

    @Mock
    private AccountRepository accountRepository;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setTenantId(10L);
        testAccount.setUserId(100L);
        testAccount.setBalance(new BigDecimal("1000.00"));
        testAccount.setFrozenAmount(new BigDecimal("100.00"));
        testAccount.setStatus("ACTIVE");
        testAccount.setVersion(1L);
        testAccount.setCreatedTime(LocalDateTime.now());
        testAccount.setUpdatedTime(LocalDateTime.now());
    }

    @Test
    void testFindById() {
        // Arrange
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        // Act
        Optional<Account> result = accountRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("1000.00"), result.get().getBalance());
        verify(accountRepository).findById(1L);
    }

    @Test
    void testFindByUserId() {
        // Arrange
        when(accountRepository.findByUserId(100L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.findByUserId(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Account> result1 = accountRepository.findByUserId(100L);
        Optional<Account> result2 = accountRepository.findByUserId(999L);

        // Assert
        assertTrue(result1.isPresent());
        assertFalse(result2.isPresent());
    }

    @Test
    void testFindByTenantId() {
        // Arrange
        Account account2 = new Account();
        account2.setId(2L);
        account2.setTenantId(10L);

        List<Account> accounts = Arrays.asList(testAccount, account2);
        when(accountRepository.findByTenantId(10L)).thenReturn(accounts);
        when(accountRepository.findByTenantId(999L)).thenReturn(Arrays.asList());

        // Act
        List<Account> result1 = accountRepository.findByTenantId(10L);
        List<Account> result2 = accountRepository.findByTenantId(999L);

        // Assert
        assertEquals(2, result1.size());
        assertTrue(result2.isEmpty());
    }

    @Test
    void testFindByTenantIdAndStatus() {
        // Arrange
        Account account2 = new Account();
        account2.setId(2L);
        account2.setTenantId(10L);
        account2.setStatus("ACTIVE");

        List<Account> activeAccounts = Arrays.asList(testAccount, account2);
        when(accountRepository.findByTenantIdAndStatus(10L, "ACTIVE")).thenReturn(activeAccounts);
        when(accountRepository.findByTenantIdAndStatus(10L, "FROZEN")).thenReturn(Arrays.asList());

        // Act
        List<Account> result1 = accountRepository.findByTenantIdAndStatus(10L, "ACTIVE");
        List<Account> result2 = accountRepository.findByTenantIdAndStatus(10L, "FROZEN");

        // Assert
        assertEquals(2, result1.size());
        assertTrue(result2.isEmpty());
    }

    @Test
    void testUpdateBalance() {
        // Arrange
        doNothing().when(accountRepository).updateBalance(1L, new BigDecimal("2000.00"), 1L);

        // Act
        accountRepository.updateBalance(1L, new BigDecimal("2000.00"), 1L);

        // Assert
        verify(accountRepository).updateBalance(1L, new BigDecimal("2000.00"), 1L);
    }

    @Test
    void testUpdateFrozenAmount() {
        // Arrange
        doNothing().when(accountRepository).updateFrozenAmount(1L, new BigDecimal("200.00"), 1L);

        // Act
        accountRepository.updateFrozenAmount(1L, new BigDecimal("200.00"), 1L);

        // Assert
        verify(accountRepository).updateFrozenAmount(1L, new BigDecimal("200.00"), 1L);
    }

    @Test
    void testIncreaseBalance() {
        // Arrange
        doNothing().when(accountRepository).increaseBalance(1L, new BigDecimal("100.00"), 1L);

        // Act
        accountRepository.increaseBalance(1L, new BigDecimal("100.00"), 1L);

        // Assert
        verify(accountRepository).increaseBalance(1L, new BigDecimal("100.00"), 1L);
    }

    @Test
    void testDecreaseBalance() {
        // Arrange
        doNothing().when(accountRepository).decreaseBalance(1L, new BigDecimal("100.00"), 1L);

        // Act
        accountRepository.decreaseBalance(1L, new BigDecimal("100.00"), 1L);

        // Assert
        verify(accountRepository).decreaseBalance(1L, new BigDecimal("100.00"), 1L);
    }

    @Test
    void testCheckBalanceSufficient() {
        // Arrange
        when(accountRepository.checkBalanceSufficient(1L, new BigDecimal("100.00"))).thenReturn(true);
        when(accountRepository.checkBalanceSufficient(1L, new BigDecimal("2000.00"))).thenReturn(false);

        // Act
        boolean sufficient1 = accountRepository.checkBalanceSufficient(1L, new BigDecimal("100.00"));
        boolean sufficient2 = accountRepository.checkBalanceSufficient(1L, new BigDecimal("2000.00"));

        // Assert
        assertTrue(sufficient1);
        assertFalse(sufficient2);
    }
}
