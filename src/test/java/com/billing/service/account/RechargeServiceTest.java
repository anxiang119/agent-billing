package com.billing.service.account;

import com.billing.common.Response;
import com.billing.dto.RechargeDTO;
import com.billing.entity.Account;
import com.billing.entity.recharge.RechargeRecord;
import com.billing.exception.BusinessException;
import com.billing.repository.RechargeRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 充值Service测试
 */
@ExtendWith(MockitoExtension.class)
class RechargeServiceTest {

    @Mock
    private RechargeRecordRepository rechargeRecordRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private RechargeServiceImpl rechargeService;

    @Test
    void testCreateRecharge_Success() {
        RechargeDTO.CreateRequest request = new RechargeDTO.CreateRequest();
        request.setTenantId(10L);
        request.setUserId(100L);
        request.setAmount(new BigDecimal("100.00"));
        request.setCurrency("CNY");
        request.setPaymentMethod("ALIPAY");

        when(rechargeRecordRepository.save(any(RechargeRecord.class))).thenAnswer(invocation -> {
            RechargeRecord record = invocation.getArgument(0);
            record.setId(1L);
            return record;
        });

        RechargeRecord result = rechargeService.createRecharge(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(rechargeRecordRepository).save(any(RechargeRecord.class));
    }

    @Test
    void testHandleCallback_Success() {
        RechargeRecord record = new RechargeRecord();
        record.setId(1L);
        record.setUserId(100L);
        record.setAmount(new BigDecimal("100.00"));
        record.setStatus("PENDING");

        when(rechargeRecordRepository.findByPaymentOrderNo("order-001")).thenReturn(record);

        Account account = new Account();
        account.setId(1L);
        account.setUserId(100L);
        account.setBalance(new BigDecimal("100.00"));
        when(accountService.recharge(any(), any())).thenReturn(account);

        RechargeDTO.CallbackRequest request = new RechargeDTO.CallbackRequest();
        request.setPaymentOrderNo("order-001");
        request.setStatus("SUCCESS");

        rechargeService.handleCallback(request);

        verify(rechargeRecordRepository).save(any(RechargeRecord.class));
        verify(accountService).recharge(any(), any());
    }
}
