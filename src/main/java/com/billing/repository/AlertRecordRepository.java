package com.billing.repository;

import com.billing.entity.AlertRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 预警记录Repository接口
 * 提供预警记录数据访问方法
 */
@Repository
public interface AlertRecordRepository extends JpaRepository<AlertRecord, Long> {

    /**
     * 根据用户ID查询预警记录列表
     *
     * @param userId 用户ID
     * @return 预警记录列表
     */
    List<AlertRecord> findByUserId(Long userId);

    /**
     * 根据租户ID查询预警记录列表
     *
     * @param tenantId 租户ID
     * @return 预警记录列表
     */
    List<AlertRecord> findByTenantId(Long tenantId);

    /**
     * 根据预警类型查询预警记录列表
     *
     * @param alertType 预警类型
     * @return 预警记录列表
     */
    List<AlertRecord> findByAlertType(String alertType);

    /**
     * 查询未处理的预警记录列表
     *
     * @return 未处理的预警记录列表
     */
    @Query("SELECT a FROM AlertRecord a WHERE a.isHandled = false")
    List<AlertRecord> findUnhandledAlerts();
}
