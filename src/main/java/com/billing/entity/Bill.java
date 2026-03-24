package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账单实体类
 * 对应数据库表：bill
 */
@Data
@Entity
@Table(name = "bill", indexes = {
    @Index(name = "idx_bill_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_bill_user_id", columnList = "user_id"),
    @Index(name = "idx_bill_type", columnList = "bill_type"),
    @Index(name = "idx_bill_status", columnList = "status")
})
@Comment("账单表")
public class Bill {

    /**
     * 账单ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("账单ID")
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
     * 账单编号
     */
    @Column(name = "bill_no", nullable = false, length = 50, unique = true)
    @Comment("账单编号")
    private String billNo;

    /**
     * 账单类型：MONTHLY-月度，QUARTERLY-季度，YEARLY-年度
     */
    @Column(name = "bill_type", nullable = false, length = 20)
    @Comment("账单类型")
    private String billType;

    /**
     * 账单周期开始时间
     */
    @Column(name = "period_start_time", nullable = false)
    @Comment("账单周期开始时间")
    private LocalDateTime periodStartTime;

    /**
     * 账单周期结束时间
     */
    @Column(name = "period_end_time", nullable = false)
    @Comment("账单周期结束时间")
    private LocalDateTime periodEndTime;

    /**
     * 总金额
     */
    @Column(name = "total_amount", nullable = false, precision = 15, scale = 4)
    @Comment("总金额")
    private BigDecimal totalAmount;

    /**
     * 优惠金额
     */
    @Column(name = "discount_amount", precision = 10, scale = 4)
    @Comment("优惠金额")
    private BigDecimal discountAmount;

    /**
     * 实际金额
     */
    @Column(name = "actual_amount", precision = 15, scale = 4)
    @Comment("实际金额")
    private BigDecimal actualAmount;

    /**
     * 货币单位
     */
    @Column(name = "currency", length = 10)
    @Comment("货币单位")
    private String currency;

    /**
     * 状态：GENERATED-已生成，PAID-已支付，OVERDUE-逾期，CANCELLED-已取消
     */
    @Column(name = "status", nullable = false, length = 20)
    @Comment("状态")
    private String status;

    /**
     * 支付时间
     */
    @Column(name = "paid_time")
    @Comment("支付时间")
    private LocalDateTime paidTime;

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
            status = "GENERATED";
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
