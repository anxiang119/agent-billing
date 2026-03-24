package com.billing.service;

import com.billing.entity.Tenant;
import com.billing.exception.BusinessException;
import com.billing.repository.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
 * 租户Service测试类
 * 测试租户业务逻辑
 */
@ExtendWith(MockitoExtension.class)
class TenantServiceTest {

    @Mock
    private TenantRepository tenantRepository;

    @InjectMocks
    private TenantServiceImpl tenantService;

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
    void testCreateTenant_Success() {
        // Arrange
        Tenant newTenant = new Tenant();
        newTenant.setName("新租户");
        newTenant.setCode("new_tenant");

        when(tenantRepository.existsByCode(anyString())).thenReturn(false);
        when(tenantRepository.save(any(Tenant.class))).thenAnswer(invocation -> {
            Tenant tenant = invocation.getArgument(0);
            tenant.setId(2L);
            return tenant;
        });

        // Act
        Tenant result = tenantService.createTenant(newTenant);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("新租户", result.getName());
        verify(tenantRepository).existsByCode("new_tenant");
        verify(tenantRepository).save(any(Tenant.class));
    }

    @Test
    void testCreateTenant_DuplicateCode() {
        // Arrange
        Tenant newTenant = new Tenant();
        newTenant.setName("新租户");
        newTenant.setCode("test_tenant");

        when(tenantRepository.existsByCode(anyString())).thenReturn(true);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            tenantService.createTenant(newTenant);
        });

        assertTrue(exception.getMessage().contains("租户代码已存在"));
        verify(tenantRepository).existsByCode("test_tenant");
        verify(tenantRepository, never()).save(any(Tenant.class));
    }

    @Test
    void testGetTenantById_Success() {
        // Arrange
        when(tenantRepository.findById(1L)).thenReturn(Optional.of(testTenant));

        // Act
        Tenant result = tenantService.getTenantById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("测试租户", result.getName());
        verify(tenantRepository).findById(1L);
    }

    @Test
    void testGetTenantById_NotFound() {
        // Arrange
        when(tenantRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            tenantService.getTenantById(999L);
        });

        assertTrue(exception.getMessage().contains("租户不存在"));
        verify(tenantRepository).findById(999L);
    }

    @Test
    void testGetTenantByCode_Success() {
        // Arrange
        when(tenantRepository.findByCode("test_tenant")).thenReturn(Optional.of(testTenant));

        // Act
        Tenant result = tenantService.getTenantByCode("test_tenant");

        // Assert
        assertNotNull(result);
        assertEquals("test_tenant", result.getCode());
        verify(tenantRepository).findByCode("test_tenant");
    }

    @Test
    void testGetTenantByCode_NotFound() {
        // Arrange
        when(tenantRepository.findByCode("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            tenantService.getTenantByCode("nonexistent");
        });

        assertTrue(exception.getMessage().contains("租户不存在"));
        verify(tenantRepository).findByCode("nonexistent");
    }

    @Test
    void testGetAllTenants_Success() {
        // Arrange
        Tenant tenant2 = new Tenant();
        tenant2.setId(2L);
        tenant2.setName("租户2");
        tenant2.setCode("tenant_2");

        List<Tenant> tenants = Arrays.asList(testTenant, tenant2);
        when(tenantRepository.findAll()).thenReturn(tenants);

        // Act
        List<Tenant> result = tenantService.getAllTenants();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(tenantRepository).findAll();
    }

    @Test
    void testUpdateTenant_Success() {
        // Arrange
        Tenant updateTenant = new Tenant();
        updateTenant.setName("更新后租户");

        when(tenantRepository.findById(1L)).thenReturn(Optional.of(testTenant));
        when(tenantRepository.save(any(Tenant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Tenant result = tenantService.updateTenant(1L, updateTenant);

        // Assert
        assertNotNull(result);
        assertEquals("更新后租户", result.getName());
        verify(tenantRepository).findById(1L);
        verify(tenantRepository).save(any(Tenant.class));
    }

    @Test
    void testUpdateTenant_NotFound() {
        // Arrange
        Tenant updateTenant = new Tenant();
        when(tenantRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            tenantService.updateTenant(999L, updateTenant);
        });

        assertTrue(exception.getMessage().contains("租户不存在"));
        verify(tenantRepository).findById(999L);
        verify(tenantRepository, never()).save(any(Tenant.class));
    }

    @Test
    void testDeleteTenant_Success() {
        // Arrange
        when(tenantRepository.findById(1L)).thenReturn(Optional.of(testTenant));
        doNothing().when(tenantRepository).delete(testTenant);

        // Act
        tenantService.deleteTenant(1L);

        // Assert
        verify(tenantRepository).findById(1L);
        verify(tenantRepository).delete(testTenant);
    }

    @Test
    void testDeleteTenant_NotFound() {
        // Arrange
        when(tenantRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            tenantService.deleteTenant(999L);
        });

        assertTrue(exception.getMessage().contains("租户不存在"));
        verify(tenantRepository).findById(999L);
        verify(tenantRepository, never()).delete(any(Tenant.class));
    }

    @Test
    void testValidateTenant_Success() {
        // Arrange
        when(tenantRepository.findByIdAndStatus(1L, "ACTIVE")).thenReturn(Optional.of(testTenant));

        // Act
        boolean result = tenantService.validateTenant(1L);

        // Assert
        assertTrue(result);
        verify(tenantRepository).findByIdAndStatus(1L, "ACTIVE");
    }

    @Test
    void testValidateTenant_Inactive() {
        // Arrange
        when(tenantRepository.findByIdAndStatus(1L, "ACTIVE")).thenReturn(Optional.empty());

        // Act
        boolean result = tenantService.validateTenant(1L);

        // Assert
        assertFalse(result);
        verify(tenantRepository).findByIdAndStatus(1L, "ACTIVE");
    }
}
