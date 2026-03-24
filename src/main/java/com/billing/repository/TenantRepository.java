package com.billing.repository;

import com.billing.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 租户Repository接口
 * 提供租户数据访问方法
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    /**
     * 根据租户代码查询租户
     *
     * @param code 租户代码
     * @return 租户
     */
    Optional<Tenant> findByCode(String code);

    /**
     * 检查租户代码是否存在
     *
     * @param code 租户代码
     * @return 是否存在
     */
    boolean existsByCode(String code);

    /**
     * 根据ID和状态查询租户
     *
     * @param id 租户ID
     * @param status 租户状态
     * @return 租户
     */
    Optional<Tenant> findByIdAndStatus(Long id, String status);

    /**
     * 根据状态查询租户列表
     *
     * @param status 租户状态
     * @return 租户列表
     */
    List<Tenant> findByStatus(String status);

    /**
     * 根据租户名称模糊查询
     *
     * @param name 租户名称
     * @return 租户列表
     */
    @Query("SELECT t FROM Tenant t WHERE t.name LIKE %:name%")
    List<Tenant> findByNameContaining(@Param("name") String name);

    /**
     * 检查租户是否存在且状态为启用
     *
     * @param id 租户ID
     * @return 是否存在且启用
     */
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Tenant t WHERE t.id = :id AND t.status = 'ACTIVE'")
    boolean existsByIdAndActive(@Param("id") Long id);

    /**
     * 统计租户数量
     *
     * @return 租户数量
     */
    @Query("SELECT COUNT(t) FROM Tenant t WHERE t.status != 'DELETED'")
    long countActiveTenants();
}
