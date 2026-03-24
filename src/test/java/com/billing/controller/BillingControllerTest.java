package com.billing.controller;

import com.billing.dto.BillingDTO;
import com.billing.service.billing.BillingEngineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 计费Controller测试
 */
@WebMvcTest(BillingController.class)
class BillingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillingEngineService billingEngineService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testEstimateFee_Success() throws Exception {
        BillingDTO.EstimateRequest request = new BillingDTO.EstimateRequest();
        request.setTenantId(10L);
        request.setUserId(100L);
        request.setResourceType("API");
        request.setResourceModel("GPT-4");
        request.setQuantity(new BigDecimal("1000"));

        BillingDTO.EstimateResponse response = new BillingDTO.EstimateResponse();
        response.setEstimatedFee(new BigDecimal("10.00"));
        response.setCurrency("CNY");

        when(billingEngineService.estimateFee(any())).thenReturn(response);

        mockMvc.perform(post("/api/billing/estimate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.estimatedFee").value(10.00));
    }

    @Test
    void testPreDeduct_Success() throws Exception {
        BillingDTO.PreDeductRequest request = new BillingDTO.PreDeductRequest();
        request.setTenantId(10L);
        request.setUserId(100L);
        request.setResourceId("resource-001");
        request.setResourceType("API");
        request.setResourceModel("GPT-4");
        request.setQuantity(new BigDecimal("1000"));

        BillingDTO.PreDeductResponse response = new BillingDTO.PreDeductResponse();
        response.setPreDeductionId(1L);
        response.setSuccess(true);
        response.setDeductedAmount(new BigDecimal("10.00"));
        response.setBalance(new BigDecimal("90.00"));
        response.setCurrency("CNY");

        when(billingEngineService.preDeduct(any())).thenReturn(response);

        mockMvc.perform(post("/api/billing/pre-deduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.success").value(true));
    }

    @Test
    void testSettle_Success() throws Exception {
        BillingDTO.SettleRequest request = new BillingDTO.SettleRequest();
        request.setTenantId(10L);
        request.setUserId(100L);
        request.setResourceId("resource-001");
        request.setResourceType("API");
        request.setResourceModel("GPT-4");
        request.setQuantity(new BigDecimal("1000"));

        BillingDTO.SettleResponse response = new BillingDTO.SettleResponse();
        response.setConsumptionRecordId(1L);
        response.setPreDeductionId(1L);
        response.setActualAmount(new BigDecimal("10.00"));
        response.setSuccess(true);

        when(billingEngineService.settle(any())).thenReturn(response);

        mockMvc.perform(post("/api/billing/settle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.success").value(true));
    }
}
