package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户实体类
 * 对应数据库表：account
 */
@Data
@Entity
@Table(name = "account", indexes = {
    @Index(name = "idx_account_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_account_user_id", columnList = "user_id")
})
@SQLDelete(sql = "UPDATE account SET status = 'DELETED', updated_time = NOW() WHERE id = ?")
@Where(clause = "status != 'DELETED'")
@Comment("账户表")
public class Account {

    /**
     * 账户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("账户ID")
    private Long id;

    /**
     * * 租户ID
     */
    @Column(name = "tenant_id", nullable = false)
    @Comment("租户ID")
    private Long tenantId;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false, unique = true)
    @Comment("用户ID")
    private Long userId;

    /**
     * 账户余额
     */
    @Column(name = "balance", nullable = false, precision = 19, scale = 4)
    @Comment("账户余额")
    private BigDecimal balance;

    /**
     * 冻结金额
     */
    @Column(name = "frozen_amount", nullable = false, precision = 19, scale = 4)
    @Comment("冻结金额")
    private BigDecimal frozenAmount;

    /**
     * 账户状态：ACTIVE-正常，FROZEN-冻结，DELETED-已删除
     */
    @Column(name = "status", nullable = false, length = 20)
    @Comment("账户状态")
    private String status;

    /**
     * 乐观锁版本号
     */
    @Version
    @Comment("乐观锁版本号")
    private Long version;

    /**
     * 创建时间
     */
    @Column(name = "created_time", nullable = false, updatable = false)
    @Comment("创建时间")
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @Column(name = "updated_time", nullable = false)
    @Comment("更新时间")
    private LocalDateTime updatedTime;

    /**
     * 创建前设置时间和默认值
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createdTime == null) {
            createdTime = now;
        }
        updatedTime = now;
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
        if (frozenAmount == null) {
            frozenAmount = BigDecimal.ZERO;
        }
        if (status == null) {
            status = "ACTIVE";
        }
        if (version == null) {
            version = 0L;
        }
    }

    /**
     * 更新前设置时间
     */
    @PreUpdate
    protected void onUpdate() {
        updatedTime = LocalDateTime.now();
    }
}
