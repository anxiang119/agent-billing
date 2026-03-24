package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库表：user
 */
@Data
@Entity
@Table(name = "user", indexes = {
    @Index(name = "idx_user_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_user_username", columnList = "username"),
    @Index(name = "idx_user_email", columnList = "email"),
    @Index(name = "idx_user_status", columnList = "status")
})
@SQLDelete(sql = "UPDATE user SET status = 'DELETED', updated_time = NOW() WHERE id = ?")
@Where(clause = "status != 'DELETED'")
@Comment("用户表")
public class User {

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("用户ID")
    private Long id;

    /**
     * 租户ID
     */
    @Column(name = "tenant_id", nullable = false)
    @Comment("租户ID")
    private Long tenantId;

    /**
     * 用户名，唯一标识
     */
    @Column(name = "username", nullable = false, unique = true, length = 50)
    @Comment("用户名")
    private String username;

    /**
     * 密码（加密存储）
     */
    @Column(name = "password", nullable = false, length = 200)
    @Comment("密码")
    private String password;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 100)
    @Comment("邮箱")
    private String email;

    /**
     * 手机号
     */
    @Column(name = "phone", length = 20)
    @Comment("手机号")
    private String phone;

    /**
     * 真实姓名
     */
    @Column(name = "real_name", length = 50)
    @Comment("真实姓名")
    private String realName;

    /**
     * 用户状态：ACTIVE-启用，DISABLED-禁用，DELETED-已删除
     */
    @Column(name = "status", nullable = false, length = 20)
    @Comment("用户状态")
    private String status;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    @Comment("最后登录时间")
    private LocalDateTime lastLoginTime;

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
     * 创建前设置时间
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createdTime == null) {
            createdTime = now;
        }
        updatedTime = now;
        if (status == null) {
            status = "ACTIVE";
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
