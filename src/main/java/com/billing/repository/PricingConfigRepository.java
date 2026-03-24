package com.billing.repository;

import com.billing.entity.PricingConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定价配置Repository接口
 * 提供定价配置数据访问方法
 */
@Repository
public interface PricingConfigRepository extends JpaRepository<PricingConfig, Long> {

    /**
     * 根据租户ID、资源类型和资源型号查询生效的定价配置
     *
     * @param tenantId 租户ID
     * @param resourceType 资源类型
     * @param resourceModel 资源型号
     * @param status 状态
     * @return 定价配置列表
     */
    List<PricingConfig> findByTenantIdAndResourceTypeAndResourceModelAndStatus(
            Long tenantId, String resourceType, String resourceModel, String status);

    /**
     * 根据租户ID查询所有定价配置
     *
     * @param tenantId 租户ID
     * @return 定价配置列表
     */
    List<PricingConfig> findByTenantId(Long tenantId);

    /**
     * 查询指定时间点生效的定价配置
     *
     * @param tenantId 租户ID
     * @param resourceType 资源类型
     * @param resourceModel 资源型号
     * @param time 时间点
     * @return 定价配置
     */
    @Query("SELECT p FROM PricingConfig p WHERE p.tenantId = :tenantId " +
           "AND p.resourceType = :resourceType " +
           "AND p.resourceModel = :resourceModel " +
           "AND p.status = 'ACTIVE' " +
           "AND p.effectiveStartTime <= :time " +
           "AND (p.effectiveEndTime IS NULL OR p.effectiveEndTime > :time) " +
           "ORDER BY p.effectiveStartTime DESC")
    List<PricingConfig> findActiveConfig(
            @Param("tenantId") Long tenantId,
            @Param("resourceType") String resourceType,
            @Param("resourceModel") String resourceModel,
            @Param("time") LocalDateTime time);

    /**
     * 检查时间范围内是否存在冲突的定价配置
     *
     * @param tenantId 租户ID
     * @param resourceType 资源类型
     * @param resourceModel 资源型号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 是否存在冲突
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PricingConfig p " +
           "WHERE p.tenantId = :tenantId " +
           "AND p.resourceType = :resourceType " +
           "AND p.resourceModel = :resourceModel " +
           "AND p.status != 'DELETED' " +
           "AND ((p.effectiveStartTime <= :startTime AND (p.effectiveEndTime IS NULL OR p.effectiveEndTime > :startTime)) OR " +
           "(p.effectiveStartTime < :endTime AND (p.effectiveEndTime IS NULL OR p.effectiveEndTime >= :endTime)))")
    boolean hasConflict(
            @Param("tenantId") Long tenantId,
            @Param("resourceType") String resourceType,
            @Param("resourceModel") String resourceModel,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
