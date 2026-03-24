package com.billing.repository;

import com.billing.entity.ConsumptionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 消费记录Repository接口
 * 提供消费记录数据访问方法
 */
@Repository
public interface ConsumptionRecordRepository extends JpaRepository<ConsumptionRecord, Long> {

    /**
     * 根据用户ID查询消费记录
     *
     * @param userId 用户ID
     * @return 消费记录列表
     */
    List<ConsumptionRecord> findByUserId(Long userId);

    /**
     * 根据租户ID查询消费记录
     *
     * @param tenantId 租户ID
     * @return 消费记录列表
     */
    List<ConsumptionRecord> findByTenantId(Long tenantId);

    /**
     * 根据资源ID查询消费记录
     *
     * @param resourceId 资源ID
     * @return 消费记录列表
     */
    List<ConsumptionRecord> findByResourceId(String resourceId);

    /**
     * 根据状态查询消费记录
     *
     * @param status 状态
     * @return 消费记录列表
     */
    List<ConsumptionRecord> findByStatus(String status);
}
