package com.billing.repository;

import com.billing.entity.recharge.RechargeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 充值记录Repository接口
 * 提供充值记录数据访问方法
 */
@Repository
public interface RechargeRecordRepository extends JpaRepository<RechargeRecord, Long> {

    /**
     * 根据用户ID查询充值记录
     *
     * @param userId 用户ID
     * @return 充值记录列表
     */
    List<RechargeRecord> findByUserId(Long userId);

    /**
     * 根据租户ID查询充值记录
     *
     * @param tenantId 租户ID
     * @return 充值记录列表
     */
    List<RechargeRecord> findByTenantId(Long tenantId);

    /**
     * 根据状态查询充值记录
     *
     * @param status 状态
     * @return 充值记录列表
     */
    List<RechargeRecord> findByStatus(String status);

    /**
     * 根据第三方支付订单号查询充值记录
     *
     * @param paymentOrderNo 支付订单号
     * @return 充值记录
     */
    RechargeRecord findByPaymentOrderNo(String paymentOrderNo);
}
