package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠活动实体类
 * 对应数据库表：promotion
 */
@Data
@Entity
@Table(name = "promotion", indexes = {
    @Index(name = "idx_promotion_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_promotion_type", columnList = "type"),
    @Index(name = "idx_promotion_status", columnList = "status")
})
@SQLDelete(sql = "UPDATE promotion SET status = 'DELETED', updated_time = NOW() WHERE id = ?")
@Where(clause = "status != 'DELETED'")
@Comment("优惠活动表")
public class Promotion {

    /**
     * 活动ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("活动ID")
    private Long id;

    /**
     * 租户ID
     */
    @Column(name = "tenant_id", nullable = false)
    @Comment("租户ID")
    private Long tenantId;

    /**
     * 活动名称
     */
    @Column(name = "name", nullable = false, length = 100)
    @Comment("活动名称")
    private String name;

    /**
     * 优惠类型：FULL_REDUCTION-满减，PERCENTAGE-百分比折扣
     */
    @Column(name = "type", nullable = false, length = 50)
    @Comment("优惠类型")
    private String type;

    /**
     * 满足条件金额
     */
    @Column(name = "condition_amount", precision = 15, scale = 4)
    @Comment("满足条件金额")
    private BigDecimal conditionAmount;

    /**
     * 优惠金额
     */
    @Column(name = "discount_amount", precision = 10, scale = 4)
    @Comment("优惠金额")
    private BigDecimal discountAmount;

    /**
     * 折扣比例（百分比折扣时使用，如0.8表示8折）
     */
    @Column(name = "discount_rate", precision = 5, scale = 4)
    @Comment("折扣比例")
    private BigDecimal discountRate;

    /**
     * 生效开始时间
     */
    @Column(name = "effective_start_time", nullable = false)
    @Comment("生效开始时间")
    private LocalDateTime effectiveStartTime;

    /**
     * 生效结束时间
     */
    @Column(name = "effective_end_time")
    @Comment("生效结束时间")
    private LocalDateTime effectiveEndTime;

    /**
     * 状态：ACTIVE-生效中，INACTIVE-未生效，EXPIRED-已过期，DELETED-已删除
     */
    @Column(name = "status", nullable = false, length = 20)
    @Comment("状态")
    private String status;

    /**
     * 描述信息
     */
    @Column(name = "description", columnDefinition = "TEXT")
    @Comment("描述信息")
    private String description;

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
