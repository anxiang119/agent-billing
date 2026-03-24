package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * 用户优惠记录实体类
 * 对应数据库表：user_promotion
 */
@Data
@Entity
@Table(name = "user_promotion", indexes = {
    @Index(name = "idx_user_promotion_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_user_promotion_user_id", columnList = "user_id"),
    @Index(name = "idx_user_promotion_promotion_id", columnList = "promotion_id"),
    @Index(name = "idx_user_promotion_status", columnList = "status")
})
@Comment("用户优惠记录表")
public class UserPromotion {

    /**
     * 记录ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("记录ID")
    private Long id;

    /**
     * 租户ID
     */
    @Column(name = "tenant_id", nullable = false)
    @Comment("租户ID")
    private Long tenantId;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    @Comment("用户ID")
    private Long userId;

    /**
     * 优惠活动ID
     */
    @Column(name = "promotion_id", nullable = false)
    @Comment("优惠活动ID")
    private Long promotionId;

    /**
     * 状态：UNUSED-未使用，USED-已使用，EXPIRED-已过期
     */
    @Column(name = "status", nullable = false, length = 20)
    @Comment("状态")
    private String status;

    /**
     * 使用时间
     */
    @Column(name = "used_time")
    @Comment("使用时间")
    private LocalDateTime usedTime;

    /**
     * 关联订单号
     */
    @Column(name = "order_no", length = 100)
    @Comment("关联订单号")
    private String orderNo;

    /**
     * 创建时间
     */
    @Column(name = "created_time", nullable = false, updatable = false)
    @Comment("创建时间")
    private LocalDateTime createdTime;

    /**
     * 创建前设置时间
     */
    @PrePersist
    protected void onCreate() {
        if (createdTime == null) {
            createdTime = LocalDateTime.now();
        }
        if (status == null) {
            status = "UNUSED";
        }
    }
}
