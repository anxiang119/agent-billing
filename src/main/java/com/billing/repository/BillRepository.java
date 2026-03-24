package com.billing.repository;

import com.billing.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
     * 根据租户ID和账单周期查询账单列表
     *
     * @param tenantId 租户ID
     * @param periodStartTime 账单周期开始时间
     * @param periodEndTime 账单周期结束时间
     * @return 账单列表
     */
    @Query("SELECT b FROM Bill b WHERE b.tenantId = :tenantId AND b.periodStartTime >= :periodStartTime AND b.periodEndTime <= :periodEndTime")
    List<Bill> findByTenantIdAndPeriod(@Param("tenantId") Long tenantId, @Param("periodStartTime") LocalDateTime periodStartTime, @Param("periodEndTime") LocalDateTime periodEndTime);
}
