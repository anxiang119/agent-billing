package com.billing.repository;

import com.billing.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 账户Repository接口
 * 提供账户数据访问方法
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * 根据用户ID查询账户
     *
     * @param userId 用户ID
     * @return 账户
     */
    Optional<Account> findByUserId(Long userId);

    /**
     * 根据租户ID和用户ID查询账户
     *
     * @param tenantId 租户ID
     * @param userId 用户ID
     * @return 账户
     */
    Optional<Account> findByTenantIdAndUserId(Long tenantId, Long userId);

    /**
     * 根据租户ID查询账户列表
     *
     * @param tenantId 租户ID
     * @return 账户列表
     */
    List<Account> findByTenantId(Long tenantId);

    /**
     * 根据租户ID和状态查询账户列表
     *
     * @param tenantId 租户ID
     * @param status 账户状态
     * @return 账户列表
     */
    List<Account> findByTenantIdAndStatus(Long tenantId, String status);

    /**
     * 更新账户余额（乐观锁）
     *
     * @param id 账户ID
     * @param balance 新余额
     * @param version 版本号
     */
    @Modifying
    @Query("UPDATE Account a SET a.balance = :balance, a.version = a.version + 1 WHERE a.id = :id AND a.version = :version")
    void updateBalance(@Param("id") Long id, @Param("balance") BigDecimal balance, @Param("version") Long version);

    /**
     * 更新冻结金额（乐观锁）
     *
     * @param id 账户ID
     * @param frozenAmount 冻结金额
     * @param version 版本号
     */
    @Modifying
    @Query("UPDATE Account a SET a.frozenAmount = :frozenAmount, a.version = a.version + 1 WHERE a.id = :id AND a.version = :version")
    void updateFrozenAmount(@Param("id") Long id, @Param("frozenAmount") BigDecimal frozenAmount, @Param("version") Long version);

    /**
     * 增加账户余额（乐观锁）
     *
     * @param id 账户ID
     * @param amount 增加金额
     * @param version 版本号
     */
    @Modifying
    @Query("UPDATE Account a SET a.balance = a.balance + :amount, a.version = a.version + 1 WHERE a.id = :id AND a.version = :version")
    void increaseBalance(@Param("id") Long id, @Param("amount") BigDecimal amount, @Param("version") Long version);

    /**
     * 减少账户余额（乐观锁）
     *
     * @param id 账户ID
     * @param amount 减少金额
     * @param version 版本号
     */
    @Modifying
    @Query("UPDATE Account a SET a.balance = a.balance - :amount, a.version = a.version + 1 WHERE a.id = :id AND a.version = :version AND a.balance >= :amount")
    void decreaseBalance(@Param("id") Long id, @Param("amount") BigDecimal amount, @Param("version") Long version);

    /**
     * 检查余额是否足够
     *
     * @param id 账户ID
     * @param amount 金额
     * @return 是否足够
     */
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Account a WHERE a.id = :id AND a.balance >= :amount AND a.status = 'ACTIVE'")
    boolean checkBalanceSufficient(@Param("id") Long id, @Param("amount") BigDecimal amount);

    /**
     * 根据租户ID统计账户数量
     *
     * @param tenantId 租户ID
     * @return 账户数量
     */
    @Query("SELECT COUNT(a) FROM Account a WHERE a.tenantId = :tenantId AND a.status != 'DELETED'")
    long countByTenantId(@Param("tenantId") Long tenantId);

    /**
     * 获取账户可用余额
     *
     * @param id 账户ID
     * @return 可用余额
     */
    @Query("SELECT a.balance - a.frozenAmount FROM Account a WHERE a.id = :id")
    BigDecimal getAvailableBalance(@Param("id") Long id);
}
