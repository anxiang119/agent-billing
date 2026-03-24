package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 预警记录实体类
 * 对应数据库表：alert_record
 */
@Data
@Entity
@Table(name = "alert_record", indexes = {
    @Index(name = "idx_alert_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_alert_user_id", columnList = "user_id"),
    @Index(name = "idx_alert_type", columnList = "alert_type")
})
@Comment("预警记录表")
public class AlertRecord {

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
     * 预警类型：BUDGET_EXCEED-预算超限，BUDGET_ALERT-预算预警
     */
    @Column(name = "alert_type", nullable = false, length = 50)
    @Comment("预警类型")
    private String alertType;

    /**
     * 预算金额
     */
    @Column(name = "budget_amount", nullable = false, precision = 15, scale = 4)
    @Comment("预算金额")
    private BigDecimal budgetAmount;

    /**
     * 已使用金额
     */
    @Column(name = "used_amount", nullable = false, precision = 15, scale = 4)
    @Comment("已使用金额")
    private BigDecimal usedAmount;

    /**
     * 使用率（百分比）
     */
    @Column(name = "usage_rate", nullable = false, precision = 5, scale = 2)
    @Comment("使用率")
    private BigDecimal usageRate;

    /**
     * 是否已处理
     */
    @Column(name = "is_handled", nullable = false)
    @Comment("是否已处理")
    private Boolean isHandled;

    /**
     * 处理时间
     */
    @Column(name = "handled_time")
    @Comment("处理时间")
    private LocalDateTime handledTime;

    /**
     * 处理结果
     */
    @Column(name = "handle_result", columnDefinition = "TEXT")
    @Comment("处理结果")
    private String handleResult;

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
        if (isHandled == null) {
            isHandled = false;
        }
    }
}
