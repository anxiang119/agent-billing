package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

/**
 * 租户实体类
 * 对应数据库表：tenant
 */
@Data
@Entity
@Table(name = "tenant", indexes = {
    @Index(name = "idx_tenant_code", columnList = "code"),
    @Index(name = "idx_tenant_status", columnList = "status")
})
@SQLDelete(sql = "UPDATE tenant SET status = 'DELETED', updated_time = NOW() WHERE id = ?")
@Where(clause = "status != 'DELETED'")
@Comment("租户表")
public class Tenant {

    /**
     * 租户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("租户ID")
    private Long id;

    /**
     * 租户名称
     */
    @Column(name = "name", nullable = false, length = 100)
    @Comment("租户名称")
    private String name;

    /**
     * 租户代码，唯一标识
     */
    @Column(name = "code", nullable = false, unique = true, length = 50)
    @Comment("租户代码")
    private String code;

    /**
     * 租户状态：ACTIVE-启用，DISABLED-禁用，DELETED-已删除
     */
    @Column(name = "status", nullable = false, length = 20)
    @Comment("租户状态")
    private String status;

    /**
     * 联系人
     */
    @Column(name = "contact", length = 100)
    @Comment("联系人")
    private String contact;

    /**
     * 联系电话
     */
    @Column(name = "phone", length = 20)
    @Comment("联系电话")
    private String phone;

    /**
     * 地址
     */
    @Column(name = "address", length = 200)
    @Comment("地址")
    private String address;

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
