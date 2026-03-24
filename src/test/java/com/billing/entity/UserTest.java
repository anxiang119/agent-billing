package com.billing.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

/**
 * 用户实体类测试
 */
class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testGettersAndSetters() {
        // Arrange & Act
        user.setId(1L);
        user.setTenantId(10L);
        user.setUsername("testuser");
        user.setPassword("encrypted_password");
        user.setEmail("test@example.com");
        user.setPhone("13800138000");
        user.setRealName("测试用户");
        user.setStatus("ACTIVE");
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());
        user.setLastLoginTime(LocalDateTime.now());

        // Assert
        assertEquals(1L, user.getId());
        assertEquals(10L, user.getTenantId());
        assertEquals("testuser", user.getUsername());
        assertEquals("encrypted_password", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("13800138000", user.getPhone());
        assertEquals("测试用户", user.getRealName());
        assertEquals("ACTIVE", user.getStatus());
        assertNotNull(user.getCreatedTime());
        assertNotNull(user.getUpdatedTime());
        assertNotNull(user.getLastLoginTime());
    }

    @Test
    void testDefaultValues() {
        // Assert
        assertNull(user.getId());
        assertNull(user.getTenantId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
        assertNull(user.getPhone());
        assertNull(user.getRealName());
        assertNull(user.getStatus());
    }

    @Test
    void testTenantRelationship() {
        // Arrange
        user.setTenantId(10L);
        Tenant tenant = new Tenant();
        tenant.setId(10L);

        // Assert
        assertEquals(10L, user.getTenantId());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(1L);

        User user3 = new User();
        user3.setId(2L);

        // Assert
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1, user3);
    }

    @Test
    void testToString() {
        // Arrange
        user.setId(1L);
        user.setUsername("testuser");

        // Act
        String str = user.toString();

        // Assert
        assertTrue(str.contains("1"));
        assertTrue(str.contains("testuser"));
    }
}
