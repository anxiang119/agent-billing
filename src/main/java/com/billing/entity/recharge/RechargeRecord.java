package com.billing.entity.recharge;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 充值记录实体类
 * 对应数据库表：recharge_record
 */
@Data
@Entity
@Table(name = "recharge_record", indexes = {
    @Index(name = "idx_recharge_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_recharge_user_id", columnList = "user_id"),
    @Index(name = "idx_recharge_status", columnList = "status")
})
@Comment("充值记录表")
public class RechargeRecord {

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
     * 充值金额
     */
    @Column(name = "amount", nullable = false, precision = 15, scale = 4)
    @Comment("充值金额")
    private BigDecimal amount;

    /**
     * 货币单位
     */
    @Column(name = "currency", length = 10)
    @Comment("货币单位")
    private String currency;

    /**
     * 支付方式：ALIPAY、WECHAT、BANK等
     */
    @Column(name = "payment_method", length = 20)
    @Comment("支付方式")
    private String paymentMethod;

    /**
     * 第三方支付订单号
     */
    @Column(name = "payment_order_no", length = 100)
    @Comment("第三方支付订单号")
    private String paymentOrderNo;

    /**
     * 状态：PENDING-待支付，SUCCESS-成功，FAILED-失败，REFUNDED-已退款
     */
    @Column(name = "status", nullable = false, length = 20)
    @Comment("状态")
    private String status;

    /**
     * 支付时间
     */
    @Column(name = "payment_time")
    @Comment("支付时间")
    private LocalDateTime paymentTime;

    /**
     * 备注信息
     */
    @Column(name = "remark", columnDefinition = "TEXT")
    @Comment("备注信息")
    private String remark;

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
            status = "PENDING";
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
