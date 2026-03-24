package com.billing.service.bill;

import com.billing.entity.Bill;
import com.billing.exception.BusinessException;
import com.billing.repository.BillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * 账单生成器测试
 */
@ExtendWith(MockitoExtension.class)
class BillGeneratorTest {

    @Mock
    private BillRepository billRepository;

    @InjectMocks
private BillGenerator billGenerator;

    @Test
    void testGenerateMonthlyBill_Success() {
        Bill expectedBill = new Bill();
        expectedBill.setId(1L);
        expectedBill.setTenantId(10L);
        expectedBill.setUserId(100L);
        expectedBill.setBillType("MONTHLY");

        when(billRepository.save(any(Bill.class))).thenAnswer(invocation -> {
            Bill bill = invocation.getArgument(0);
            bill.setId(1L);
            return bill;
        });

        Bill bill = billGenerator.generateMonthlyBill(10L, 100L, LocalDateTime.now().minusDays(30), LocalDateTime.now());

        assertNotNull(bill);
        assertEquals("MONTHLY", bill.getBillType());
        assertEquals(10L, bill.getTenantId());
        assertEquals(100L, bill.getUserId());
    }

    @Test
    void testGenerateMonthlyBill_NullTenantId() {
        assertThrows(BusinessException.class, () -> {
            billGenerator.generateMonthlyBill(null, 100L, LocalDateTime.now().minusDays(30), LocalDateTime.now());
        });
    }

    @Test
    void testGenerateMonthlyBill_NullUserId() {
        assertThrows(BusinessException.class, () -> {
            billGenerator.generateMonthlyBill(10L, null, LocalDateTime.now().minusDays(30), LocalDateTime.now());
        });
    }
}
