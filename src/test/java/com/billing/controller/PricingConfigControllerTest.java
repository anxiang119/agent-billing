package com.billing.controller;

import com.billing.dto.PricingConfigDTO;
import com.billing.entity.PricingConfig;
import com.billing.service.PricingConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 定价配置Controller测试
 */
@WebMvcTest(PricingConfigController.class)
class PricingConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PricingConfigService pricingConfigService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testGetConfigById_Success() throws Exception {
        when(pricingConfigService.getConfigById(1L)).thenReturn(testConfig);

        mockMvc.perform(get("/api/pricing-configs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.resourceType").value("API"));
    }

    @Test
    void testCreateConfig_Success() throws Exception {
        PricingConfigDTO.CreateRequest request = new PricingConfigDTO.CreateRequest();
        request.setTenantId(10L);
        request.setResourceType("API");
        request.setResourceModel("GPT-4");
        request.setPricingMode("PER_UNIT");
        request.setUnitPrice(new BigDecimal("0.01"));
        request.setCurrency("CNY");
        request.setEffectiveStartTime(LocalDateTime.now());

        when(pricingConfigService.createConfig(any())).thenAnswer(invocation -> {
            PricingConfig config = new PricingConfig();
            config.setId(2L);
            config.setResourceType("API");
            return config;
        });

        mockMvc.perform(post("/api/pricing-configs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(2));
    }

    @Test
    void testUpdateConfig_Success() throws Exception {
        PricingConfigDTO.UpdateRequest request = new PricingConfigDTO.UpdateRequest();
        request.setUnitPrice(new BigDecimal("0.02"));

        PricingConfig updatedConfig = new PricingConfig();
        updatedConfig.setId(1L);
        updatedConfig.setTenantId(10L);
        updatedConfig.setResourceType("API");
        updatedConfig.setResourceModel("GPT-4");
        updatedConfig.setPricingMode("PER_UNIT");
        updatedConfig.setUnitPrice(new BigDecimal("0.02"));
        updatedConfig.setCurrency("CNY");
        updatedConfig.setEffectiveStartTime(LocalDateTime.now());
        updatedConfig.setStatus("ACTIVE");

        when(pricingConfigService.updateConfig(anyLong(), any())).thenReturn(updatedConfig);

        mockMvc.perform(put("/api/pricing-configs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.unitPrice").value(0.02));
    }

    @Test
    void testDeleteConfig_Success() throws Exception {
        mockMvc.perform(delete("/api/pricing-configs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }
}
