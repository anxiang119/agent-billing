package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 定价配置历史实体类
 * 对应数据库表：pricing_config_history
 */
@Data
@Entity
@Table(name = "pricing_config_history", indexes = {
    @Index(name = "idx_pricing_history_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_pricing_history_config_id", columnList = "config_id"),
    @Index(name = "idx_pricing_history_operation_time", columnList = "operation_time")
})
@Comment("定价配置历史表")
public class PricingConfigHistory {

    /**
     * 历史ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("历史ID")
    private Long id;

    /**
     * 租户ID
     */
    @Column(name = "tenant_id", nullable = false)
    @Comment("租户ID")
    private Long tenantId;

    /**
     * 配置ID（原配置ID）
     */
    @Column(name = "config_id", nullable = false)
    @Comment("配置ID")
    private Long configId;

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
     * 计费模式
     */
    @Column(name = "pricing_mode", nullable = false, length = 20)
    @Comment("计费模式")
    private String pricingMode;

    /**
     * 单价
     */
    @Column(name = "unit_price", precision = 10, scale = 4)
    @Comment("单价")
    private BigDecimal unitPrice;

    /**
     * 货币单位
     */
    @Column(name = "currency", length = 10)
    @Comment("货币单位")
    private String currency;

    /**
     * 阶梯配置
     */
    @Column(name = "tiers", columnDefinition = "TEXT")
    @Comment("阶梯配置")
    private String tiers;

    /**
     * 月费
     */
    @Column(name = "monthly_fee", precision = 10, scale = 4)
    @Comment("月费")
    private BigDecimal monthlyFee;

    /**
     * 操作类型：CREATE-创建，UPDATE-更新，DELETE-删除
     */
    @Column(name = "operation_type", nullable = false, length = 20)
    @Comment("操作类型")
    private String operationType;

    /**
     * 操作人ID
     */
    @Column(name = "operator_id")
    @Comment("操作人ID")
    private Long operatorId;

    /**
     * 操作时间
     */
    @Column(name = "operation_time", nullable = false)
    @Comment("操作时间")
    private LocalDateTime operationTime;

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
     * 创建前设置时间
     */
    @PrePersist
    protected void onCreate() {
        if (createdTime == null) {
            createdTime = LocalDateTime.now();
        }
    }
}
