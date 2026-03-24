package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 消费费记录实体类
 * 对应数据库表：consumption_record
 */
@Data
@Entity
@Table(name = "consumption_record", indexes = {
    @Index(name = "idx_consumption_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_consumption_user_id", columnList = "user_id"),
    @Index(name = "idx_consumption_resource_id", columnList = "resource_id"),
    @Index(name = "idx_consumption_status", columnList = "status"),
    @Index(name = "idx_consumption_time", columnList = "consumption_time")
})
@Comment("消费记录表")
public class ConsumptionRecord {

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
     * 资源类型
     */
    @Column(name = "resource_type", nullable = false, length = 50)
    @Comment("资源类型")
    private String resourceType;

    /**
     * 资源型号
     */
    @Column(name = "resource_model", length = 100)
    @Comment("资源型号")
    private String resourceModel;

    /**
     * 消费数量
     */
    @Column(name = "quantity", nullable = false, precision = 15, scale = 4)
    @Comment("消费数量")
    private BigDecimal quantity;

    /**
     * 单价
     */
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 4)
    @Comment("单价")
    private BigDecimal unitPrice;

    /**
     * 金额
     */
    @Column(name = "amount", nullable = false, precision = 15, scale = 4)
    @Comment("金额")
    private BigDecimal amount;

    /**
     * 货币单位
     */
    @Column(name = "currency", length = 10)
    @Comment("货币单位")
    private String currency;

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
     * 状态：PENDING-待结算，SETTLED-已结算
     */
    @Column(name = "status", nullable = false, length = 20)
    @Comment("状态")
    private String status;

    /**
     * 消费时间
     */
    @Column(name = "consumption_time", nullable = false)
    @Comment("消费时间")
    private LocalDateTime consumptionTime;

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
