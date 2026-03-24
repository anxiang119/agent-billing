package com.billing.service.bill;

import com.billing.entity.Bill;

import java.io.File;
import java.time.LocalDateTime;

/**
 * 账单Service接口
 * 提供账单管理业务逻辑
 */
public interface BillService {

    /**
     * 生成月度账单
     *
     * @param tenantId 租户ID
     * @param userId 用户ID
     * @return 账单
     */
    Bill generateMonthlyBill(Long tenantId, Long userId);

    /**
     * 根据ID获取账单
     *
     * @param id 账单ID
     * @return 账单
     */
    Bill getBillById(Long id);

    /**
     * 根据账单编号获取账单
     *
     * @param billNoNo 账单编号
     * @return 账单
     */
    Bill getBillByBillNo(String billNo);

    /**
     * 删除账单
     *
     * @param id 账单ID
     */
    void deleteBill(Long id);

    /**
     * 生成账单PDF文件
     *
     * @param billId 账单ID
     * @return PDF文件
     */
    File generatePdfFile(Long billId);

    /**
     * 生成账单Excel文件
     *
     * @param billId 账单ID
     * @return Excel文件
     */
    File generateExcelFile(Long billId);
}
