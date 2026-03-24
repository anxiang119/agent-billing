package com.billing.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户实体类测试
 */
class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void testGettersAndSetters() {
        // Arrange & Act
        account.setId(1L);
        account.setTenantId(10L);
        account.setUserId(100L);
        account.setBalance(new BigDecimal("1000.00"));
        account.setFrozenAmount(new BigDecimal("100.00"));
        account.setStatus("ACTIVE");
        account.setVersion(1L);
        account.setCreatedTime(LocalDateTime.now());
        account.setUpdatedTime(LocalDateTime.now());

        // Assert
        assertEquals(1L, account.getId());
        assertEquals(10L, account.getTenantId());
        assertEquals(100L, account.getUserId());
        assertEquals(new BigDecimal("1000.00"), account.getBalance());
        assertEquals(new BigDecimal("100.00"), account.getFrozenAmount());
        assertEquals("ACTIVE", account.getStatus());
        assertEquals(1L, account.getVersion());
        assertNotNull(account.getCreatedTime());
        assertNotNull(account.getUpdatedTime());
    }

    @Test
    void testDefaultValues() {
        // Assert
        assertNull(account.getId());
        assertNull(account.getTenantId());
        assertNull(account.getUserId());
        assertNull(account.getBalance());
        assertNull(account.getFrozenAmount());
        assertNull(account.getStatus());
        assertNull(account.getVersion());
    }

    @Test
    void testAvailableBalance() {
        // Arrange
        account.setBalance(new BigDecimal("1000.00"));
        account.setFrozenAmount(new BigDecimal("100.00"));

        // Act
        BigDecimal available = account.getBalance().subtract(account.getFrozenAmount());

        // Assert
        assertEquals(new BigDecimal("900.00"), available);
    }

    @Test
    void testOptimisticLocking() {
        // Arrange
        account.setVersion(1L);

        // Act
        account.setVersion(2L);

        // Assert
        assertEquals(2L, account.getVersion());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Account account1 = new Account();
        account1.setId(1L);

        Account account2 = new Account();
        account2.setId(1L);

        Account account3 = new Account();
        account3.setId(2L);

        // Assert
        assertEquals(account1, account2);
        assertEquals(account1.hashCode(), account2.hashCode());
        assertNotEquals(account1, account3);
    }

    @Test
    void testToString() {
        // Arrange
        account.setId(1L);
        account.setBalance(new BigDecimal("1000.00"));

        // Act
        String str = account.toString();

        // Assert
        assertTrue(str.contains("1"));
        assertTrue(str.contains("1000.00"));
    }
}
