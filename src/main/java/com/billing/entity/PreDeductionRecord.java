package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 预扣记录实体类
 * 对应数据库表：pre_deduction_record
 */
@Data
@Entity
@Table(name = "pre_deduction_record", indexes = {
    @Index(name = "idx_pre_deduction_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_pre_deduction_user_id", columnList = "user_id"),
    @Index(name = "idx_pre_deduction_resource_id", columnList = "resource_id"),
    @Index(name = "idx_pre_deduction_status", columnList = "status")
})
@Comment("预扣记录表")
public class PreDeductionRecord {

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
     * 资源ID
     */
    @Column(name = "resource_id", nullable = false, length = 100)
    @Comment("资源ID")
    private String resourceId;

    /**
     * 预估金额
     */
    @Column(name = "estimated_amount", nullable = false, precision = 15, scale = 4)
    @Comment("预估金额")
    private BigDecimal estimatedAmount;

    /**
     * 实际预扣金额
     */
    @Column(name = "pre_deducted_amount", precision = 15, scale = 4)
    @Comment("实际预扣金额")
    private BigDecimal preDeductedAmount;

    /**
     * 实际消费金额
     */
    @Column(name = "actual_amount", precision = 15, scale = 4)
    @Comment("实际消费金额")
    private BigDecimal actualAmount;

    /**
     * 退款金额
     */
    @Column(name = "refund_amount", precision = 10, scale = 4)
    @Comment("退款金额")
    private BigDecimal refundAmount;

    /**
     * 状态：PENDING-待预扣，DEDUCTED-已预扣，SETTLED-已结算，REFUNDED-已退款
     */
    @Column(name = "status", nullable = false, length = 20)
    @Comment("状态")
    private String status;

    /**
     * 预扣时间
     */
    @Column(name = "deduction_time")
    @Comment("预预扣时间")
    private LocalDateTime deductionTime;

    /**
     * 结算时间
     */
    @Column(name = "settle_time")
    @Comment("结算时间")
    private LocalDateTime settleTime;

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
            status = "PENDING";
        }
    }
}
