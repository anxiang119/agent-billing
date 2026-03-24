package com.billing.service;

import com.billing.common.Response;
import com.billing.dto.PricingConfigDTO;
import com.billing.entity.PricingConfig;
import com.billing.exception.BusinessException;
import com.billing.repository.PricingConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 定价配置Service测试
 */
@ExtendWith(MockitoExtension.class)
class PricingConfigServiceTest {

    @Mock
    private PricingConfigRepository repository;

    @InjectMocks
    private PricingConfigServiceImpl service;

    private PricingConfig testConfig;

    @BeforeEach
    void setUp() {
        testConfig = new PricingConfig();
        testConfig.setId(1L);
        testConfig.setTenantId(10L);
        testConfig.setResourceType("API");
        testConfig.setResourceModel("GPT-4");
        testConfig.setPricingMode("PER_UNIT");
        testConfig.setUnitPrice(new BigDecimal("0.01"));
        testConfig.setCurrency("CNY");
        testConfig.setEffectiveStartTime(LocalDateTime.now());
        testConfig.setStatus("ACTIVE");
    }

    @Test
    void testCreateConfig_Success() {
        PricingConfigDTO.CreateRequest request = new PricingConfigDTO.CreateRequest();
        request.setTenantId(10L);
        request.setResourceType("API");
        request.setResourceModel("GPT-4");
        request.setPricingMode("PER_UNIT");
        request.setUnitPrice(new BigDecimal("0.01"));
        request.setCurrency("CNY");
        request.setEffectiveStartTime(LocalDateTime.now());

        when(repository.hasConflict(any(), any(), any(), any(), any())).thenReturn(false);
        when(repository.save(any(PricingConfig.class))).thenAnswer(invocation -> {
            PricingConfig config = invocation.getArgument(0);
            config.setId(2L);
            return config;
        });

        PricingConfig result = service.createConfig(request);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        verify(repository).hasConflict(any(), any(), any(), any(), any());
        verify(repository).save(any(PricingConfig.class));
    }

    @Test
    void testCreateConfig_Conflict() {
        PricingConfigDTO.CreateRequest request = new PricingConfigDTO.CreateRequest();
        request.setTenantId(10L);
        request.setResourceType("API");
        request.setResourceModel("GPT-4");
        request.setPricingMode("PER_UNIT");
        request.setEffectiveStartTime(LocalDateTime.now());

        when(repository.hasConflict(any(), any(), any(), any(), any())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.createConfig(request);
        });

        assertTrue(exception.getMessage().contains("时间范围与已有配置冲突"));
    }
}
