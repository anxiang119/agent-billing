package com.billing.repository;

import com.billing.entity.Tenant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * 租户Repository测试类
 */
@ExtendWith(MockitoExtension.class)
class TenantRepositoryTest {

    @Mock
    private TenantRepository tenantRepository;

    private Tenant testTenant;

    @BeforeEach
    void setUp() {
        testTenant = new Tenant();
        testTenant.setId(1L);
        testTenant.setName("测试租户");
        testTenant.setCode("test_tenant");
        testTenant.setStatus("ACTIVE");
        testTenant.setCreatedTime(LocalDateTime.now());
        testTenant.setUpdatedTime(LocalDateTime.now());
    }

    @Test
    void testFindById() {
        // Arrange
        when(tenantRepository.findById(1L)).thenReturn(Optional.of(testTenant));

        // Act
        Optional<Tenant> result = tenantRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("测试租户", result.get().getName());
        verify(tenantRepository).findById(1L);
    }

    @Test
    void testFindAll() {
        // Arrange
        Tenant tenant2 = new Tenant();
        tenant2.setId(2L);
        tenant2.setName("租户2");

        List<Tenant> tenants = Arrays.asList(testTenant, tenant2);
        when(tenantRepository.findAll()).thenReturn(tenants);

        // Act
        List<Tenant> result = tenantRepository.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(tenantRepository).findAll();
    }

    @Test
    void testSave() {
        // Arrange
        Tenant newTenant = new Tenant();
        newTenant.setName("新租户");
        newTenant.setCode("new_tenant");

        when(tenantRepository.save(any(Tenant.class))).thenAnswer(invocation -> {
            Tenant tenant = invocation.getArgument(0);
            tenant.setId(2L);
            return tenant;
        });

        // Act
        Tenant result = tenantRepository.save(newTenant);

        // Assert
        assertEquals(2L, result.getId());
        assertEquals("新租户", result.getName());
        verify(tenantRepository).save(any(Tenant.class));
    }

    @Test
    void testDelete() {
        // Arrange
        doNothing().when(tenantRepository).delete(testTenant);

        // Act
        tenantRepository.delete(testTenant);

        // Assert
        verify(tenantRepository).delete(testTenant);
    }

    @Test
    void testFindByCode() {
        // Arrange
        when(tenantRepository.findByCode("test_tenant")).thenReturn(Optional.of(testTenant));

        // Act
        Optional<Tenant> result = tenantRepository.findByCode("test_tenant");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("test_tenant", result.get().getCode());
        verify(tenantRepository).findByCode("test_tenant");
    }

    @Test
    void testExistsByCode() {
        // Arrange
        when(tenantRepository.existsByCode("test_tenant")).thenReturn(true);
        when(tenantRepository.existsByCode("nonexistent")).thenReturn(false);

        // Act
        boolean exists1 = tenantRepository.existsByCode("test_tenant");
        boolean exists2 = tenantRepository.existsByCode("nonexistent");

        // Assert
        assertTrue(exists1);
        assertFalse(exists2);
    }

    @Test
    void testFindByIdAndStatus() {
        // Arrange
        when(tenantRepository.findByIdAndStatus(1L, "ACTIVE")).thenReturn(Optional.of(testTenant));
        when(tenantRepository.findByIdAndStatus(1L, "DISABLED")).thenReturn(Optional.empty());

        // Act
        Optional<Tenant> result1 = tenantRepository.findByIdAndStatus(1L, "ACTIVE");
        Optional<Tenant> result2 = tenantRepository.findByIdAndStatus(1L, "DISABLED");

        // Assert
        assertTrue(result1.isPresent());
        assertFalse(result2.isPresent());
    }

    @Test
    void testFindByStatus() {
        // Arrange
        Tenant tenant2 = new Tenant();
        tenant2.setId(2L);
        tenant2.setStatus("ACTIVE");

        List<Tenant> activeTenants = Arrays.asList(testTenant, tenant2);
        when(tenantRepository.findByStatus("ACTIVE")).thenReturn(activeTenants);
        when(tenantRepository.findByStatus("DISABLED")).thenReturn(Arrays.asList());

        // Act
        List<Tenant> result1 = tenantRepository.findByStatus("ACTIVE");
        List<Tenant> result2 = tenantRepository.findByStatus("DISABLED");

        // Assert
        assertEquals(2, result1.size());
        assertTrue(result2.isEmpty());
    }
}
