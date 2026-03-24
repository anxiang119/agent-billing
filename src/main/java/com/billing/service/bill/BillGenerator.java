package com.billing.service.bill;

import com.billing.common.Response;
import com.billing.entity.Bill;
import com.billing.exception.BusinessException;
import com.billing.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 账单生成器
 * 提供账单生成逻辑
 */
@Service
@RequiredArgsConstructor
public class BillGenerator {

    private final BillRepository billRepository;

    /**
     * 生成月度账单
     *
     * @param tenantId 租户ID
     * @param userId 用户ID
     * @param periodStart 周期开始时间
     * @param periodEnd 周期结束时间
     * @return 账单
     */
    @Transactional
    public Bill generateMonthlyBill(Long tenantId, Long userId, LocalDateTime periodStart, LocalDateTime periodEnd) {
        if (tenantId == null || userId == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "租户ID和用户ID不能为空");
        }

        Bill bill = new Bill();
        bill.setTenantId(tenantId);
        bill.setUserId(userId);
        bill.setBillNo(generateBillNo(tenantId, userId));
        bill.setBillType("MONTHLY");
        bill.setPeriodStartTime(periodStart);
        bill.setPeriodEndTime(periodEnd);

        return billRepository.save(bill);
    }

    /**
     * 生成账单编号
     *
     * @param tenantId 租户ID
     * @param userId 用户ID
     * @return 账单编号
     */
    private String generateBillNo(Long tenantId, Long userId) {
        return "BILL" + tenantId + userId + System.currentTimeMillis();
    }
}
