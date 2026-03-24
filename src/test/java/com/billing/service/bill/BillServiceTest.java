package com.billing.service.bill;

import com.billing.common.Response;
import com.billing.entity.Bill;
import com.billing.exception.BusinessException;
import com.billing.repository.BillRepository;
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
 * 账单Service测试
 */
@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private BillGenerator billGenerator;

    @InjectMocks
    private BillServiceImpl service;

    @Test
    void testGenerateBill_Success() {
        when(billGenerator.generateMonthlyBill(any(), any(), any(), any())).thenAnswer(invocation -> {
            Bill bill = new Bill();
            bill.setId(1L);
            bill.setTenantId(10L);
            bill.setUserId(100L);
            bill.setBillType("MONTHLY");
            return bill;
        });

        Bill result = service.generateMonthlyBill(10L, 100L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(billGenerator).generateMonthlyBill(any(), any(), any(), any());
    }

    @Test
    void testGetBillById_Success() {
        Bill bill = new Bill();
        bill.setId(1L);
        bill.setTenantId(10L);
        bill.setUserId(100L);

        when(billRepository.findById(1L)).thenReturn(Optional.of(bill));

        Bill result = service.getBillById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}
