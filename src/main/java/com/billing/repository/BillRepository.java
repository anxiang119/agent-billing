package com.billing.repository;

import com.billing.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 账单Repository接口
 * 提供账单数据访问方法
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    /**
     * 根据用户ID查询账单列表
     *
     * @param userId 用户ID
     * @return 账单列表
     */
    List<Bill> findByUserId(Long userId);

    /**
     * 根据租户ID查询账单列表
     *
     * @param tenantId 租户ID
     * @return 账单列表
     */
    List<Bill> findByTenantId(Long tenantId);

    /**
     * 根据账单编号查询账单
     *
     * @param billNo 账单编号
     * @return 账单
     */
    Bill findByBillNo(String billNo);

    /**
     * 根据账单类型查询账单列表
     *
     * @param userId 用户ID
     * @param billType 账单类型
     * @return 账单列表
     */
    List<Bill> findByUserIdAndBillType(Long userId, String billType);

    /**
     * 根据租户ID和查询期间查询账单列表
     *
     * @param tenantId 租户ID
     * @param periodStart 开始时间
     * @param periodEnd 结束时间
     * @return 账单列表
     */
    List<Bill> findByTenantIdAndPeriod(Long tenantId, LocalDateTime periodStart, LocalDateTime periodEnd);
}
