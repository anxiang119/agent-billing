package com.billing.service.billing;

import com.billing.common.Response;
import com.billing.dto.BillingDTO;
import com.billing.entity.*;
import com.billing.exception.BusinessException;
import com.billing.repository.*;
import com.billing.service.PricingConfigService;
import com.billing.service.account.AccountService;
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
 * 计费引擎Service测试
 */
@ExtendWith(MockitoExtension.class)
class BillingEngineServiceTest {

    @Mock
    private PricingConfigService pricingConfigService;

    @Mock
    private AccountService accountService;

    @Mock
    private ConsumptionRecordRepository consumptionRecordRepository;

    @Mock
    private PreDeductionRecordRepository preDeductionRecordRepository;

    @Mock
    private FeeCalculator feeCalculator;

    @InjectMocks
    private BillingEngineServiceImpl billingEngineService;

    @Test
    void testEstimateFee_Success() {
        BillingDTO.EstimateRequest request = new BillingDTO.EstimateRequest();
        request.setTenantId(10L);
        request.setUserId(100L);
        request.setResourceType("API");
        request.setResourceModel("GPT-4");
        request.setQuantity(new BigDecimal("1000"));

        PricingConfig config = new PricingConfig();
        config.setPricingMode("PER_UNIT");
        config.setUnitPrice(new BigDecimal("0.01"));

        when(pricingConfigService.getActiveConfig(any(), any(), any(), any())).thenReturn(config);
        when(feeCalculator.calculate(any(), any())).thenReturn(new BigDecimal("10.00"));

        BillingDTO.EstimateResponse response = billingEngineService.estimateFee(request);

        assertNotNull(response);
        assertEquals(new BigDecimal("10.00"), response.getEstimatedFee());
    }

    @Test
    void testPreDeduct_Success() {
        BillingDTO.PreDeductRequest request = new BillingDTO.PreDeductRequest();
        request.setTenantId(10L);
        request.setUserId(100L);
        request.setResourceId("resource-001");
        request.setResourceType("API");
        request.setResourceModel("GPT-4");
        request.setQuantity(new BigDecimal("1000"));

        PricingConfig config = new PricingConfig();
        config.setPricingMode("PER_UNIT");
        config.setUnitPrice(new BigDecimal("0.01"));

        Account account = new Account();
        account.setUserId(100L);
        account.setBalance(new BigDecimal("1000.00"));

        PreDeductionRecord preDeductionRecord = new PreDeductionRecord();
        preDeductionRecord.setId(1L);

        when(pricingConfigService.getActiveConfig(any(), any(), any(), any())).thenReturn(config);
        when(feeCalculator.calculate(any(), any())).thenReturn(new BigDecimal("10.00"));
        when(preDeductionRecordRepository.save(any(PreDeductionRecord.class))).thenReturn(preDeductionRecord);
        when(accountService.deduct(any(), any(), any())).thenReturn(account);

        BillingDTO.PreDeductResponse response = billingEngineService.preDeduct(request);

        assertNotNull(response);
        assertTrue(response.getSuccess());
    }
}
