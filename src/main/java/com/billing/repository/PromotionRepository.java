package com.billing.repository;

import com.billing.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 优惠活动Repository接口
 * 提供优惠活动数据访问方法
 */
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    /**
     * 根据租户ID查询优惠活动列表
     *
     * @param tenantId 租户ID
     * @return 优惠活动列表
     */
    List<Promotion> findByTenantId(Long tenantId);

    /**
     * 查询有效的优惠活动
     *
     * @param tenantId 租户ID
     * @param time 时间点
     * @return 优惠活动列表
     */
    @Query("SELECT p FROM Promotion p WHERE p.tenantId = :tenantId " +
           "AND p.status = 'ACTIVE' " +
           "AND p.effectiveStartTime <= :time " +
           "AND (p.effectiveEndTime IS NULL OR p.effectiveEndTime > :time) " +
           "ORDER BY p.conditionAmount DESC")
    List<Promotion> findActivePromotions(
            @Param("tenantId") Long tenantId,
            @Param("time") LocalDateTime time);

    /**
     * 根据类型查询优惠活动
     *
     * @param tenantId 租户ID
     * @param type 优惠类型
     * @return 优惠活动列表
     */
    List<Promotion> findByTenantIdAndType(Long tenantId, String type);
}
