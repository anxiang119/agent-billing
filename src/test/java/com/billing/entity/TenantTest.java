package com.billing.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

/**
 * 租户实体类测试
 */
class TenantTest {

    private Tenant tenant;

    @BeforeEach
    void setUp() {
        tenant = new Tenant();
    }

    @Test
    void testGettersAndSetters() {
        // Arrange & Act
        tenant.setId(1L);
        tenant.setName("测试租户");
        tenant.setCode("test_tenant");
        tenant.setStatus("ACTIVE");
        tenant.setContact("contact@example.com");
        tenant.setPhone("13800138000");
        tenant.setAddress("北京市朝阳区");
        tenant.setCreatedTime(LocalDateTime.now());
        tenant.setUpdatedTime(LocalDateTime.now());

        // Assert
        assertEquals(1L, tenant.getId());
        assertEquals("测试租户", tenant.getName());
        assertEquals("test_tenant", tenant.getCode());
        assertEquals("ACTIVE", tenant.getStatus());
        assertEquals("contact@example.com", tenant.getContact());
        assertEquals("13800138000", tenant.getPhone());
        assertEquals("北京市朝阳区", tenant.getAddress());
        assertNotNull(tenant.getCreatedTime());
        assertNotNull(tenant.getUpdatedTime());
    }

    @Test
    void testDefaultValues() {
        // Assert
        assertNull(tenant.getId());
        assertNull(tenant.getName());
        assertNull(tenant.getCode());
        assertNull(tenant.getStatus());
        assertNull(tenant.getContact());
        assertNull(tenant.getPhone());
        assertNull(tenant.getAddress());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Tenant tenant1 = new Tenant();
        tenant1.setId(1L);

        Tenant tenant2 = new Tenant();
        tenant2.setId(1L);

        Tenant tenant3 = new Tenant();
        tenant3.setId(2L);

        // Assert
        assertEquals(tenant1, tenant2);
        assertEquals(tenant1.hashCode(), tenant2.hashCode());
        assertNotEquals(tenant1, tenant3);
        assertNotEquals(tenant1.hashCode(), tenant3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        tenant.setId(1L);
        tenant.setName("测试租户");

        // Act
        String str = tenant.toString();

        // Assert
        assertTrue(str.contains("1"));
        assertTrue(str.contains("测试租户"));
    }
}
