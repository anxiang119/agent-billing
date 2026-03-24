package com.billing.repository;

import com.billing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户Repository接口
 * 提供用户数据访问方法
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据租户ID查询用户列表
     *
     * @param tenantId 租户ID
     * @return 用户列表
     */
    List<User> findByTenantId(Long tenantId);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    Optional<User> findByUsername(String username);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户
     */
    Optional<User> findByEmail(String email);

    /**
     * 根据租户ID和用户名查询用户
     *
     * @param tenantId 租户ID
     * @param username 用户名
     * @return 用户
     */
    Optional<User> findByTenantIdAndUsername(Long tenantId, String username);

    /**
     * 根据租户ID和状态查询用户列表
     *
     * @param tenantId 租户ID
     * @param status 用户状态
     * @return 用户列表
     */
    List<User> findByTenantIdAndStatus(Long tenantId, String status);

    /**
     * 根据租户ID和用户名模糊查询
     *
     * @param tenantId 租户ID
     * @param username 用户名
     * @return 用户列表
     */
    @Query("SELECT u FROM User u WHERE u.tenantId = :tenantId AND u.username LIKE %:username%")
    List<User> findByTenantIdAndUsernameContaining(@Param("tenantId") Long tenantId, @Param("username") String username);

    /**
     * 更新用户最后登录时间
     *
     * @param id 用户ID
     * @param lastLoginTime 最后登录时间
     */
    @Modifying
    @Query("UPDATE User u SET u.lastLoginTime = :lastLoginTime WHERE u.id = :id")
    void updateLastLoginTime(@Param("id") Long id, @Param("lastLoginTime") LocalDateTime lastLoginTime);

    /**
     * 根据租户ID统计用户数量
     *
     * @param tenantId 租户ID
     * @return 用户数量
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.tenantId = :tenantId AND u.status != 'DELETED'")
    long countByTenantId(@Param("tenantId") Long tenantId);

    /**
     * 检查用户是否存在且状态为启用
     *
     * @param id 用户ID
     * @return 是否存在且启用
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.id = :id AND u.status = 'ACTIVE'")
    boolean existsByIdAndActive(@Param("id") Long id);
}
