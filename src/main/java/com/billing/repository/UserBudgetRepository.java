package com.billing.repository;

import com.billing.entity.UserBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户预算Repository接口
 * 提供用户预算数据访问方法
 */
@Repository
public interface UserBudgetRepository extends JpaRepository<UserBudget, Long> {

    /**
     * 根据用户ID查询预算列表
     *
     * @param userId 用户ID
     * @return 预算列表
     */
    List<UserBudget> findByUserId(Long userId);

    /**
     * 根据租户ID查询预算列表
     *
     * @param tenantId 租户ID
     * @return 预算列表
     */
    List<UserBudget> findByTenantId(Long tenantId);

    /**
     * 根据用户ID和预算类型查询预算
     *
     * @param userId 用户ID
     * @param budgetType 预算类型
     * @return 预算
     */
    UserBudget findByUserIdAndBudgetType(Long userId, String budgetType);

    /**
     * 查询有效的预算
     *
     * @param userId 用户ID
     * @param budgetType 预算类型
     * @param currentTime 当前时间
     * @return 预算
     */
    @Query("SELECT b FROM UserBudget b WHERE b.userId = :userId " +
           "AND b.budgetType = :budgetType " +
           "AND b.status = 'ACTIVE' " +
           "AND (b.startTime IS NULL OR b.startTime <= :currentTime) " +
           "AND (b.endTime IS NULL OR b.endTime > :currentTime)")
    UserBudget findActiveBudget(
            @Param("userId") Long userId,
            @Param("budgetType") String budgetType,
            @Param("currentTime") java.time.LocalDateTime currentTime);
}
