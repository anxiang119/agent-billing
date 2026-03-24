package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 定价配置实体类
 * 对应数据库表：pricing_config
 */
@Data
@Entity
@Table(name = "pricing_config", indexes = {
    @Index(name = "idx_pricing_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_pricing_resource_type", columnList = "resource_type"),
    @Index(name = "idx_pricing_status", columnList = "status")
})
@SQLDelete(sql = "UPDATE pricing_config SET status = 'DELETED', updated_time = NOW() WHERE id = ?")
@Where(clause = "status != 'DELETED'")
@Comment("定价配置表")
public class PricingConfig {

    /**
     * 配置ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("配置ID")
    private Long id;

    /**
     * 租户ID
     */
    @Column(name = "tenant_id", nullable = false)
    @Comment("租户ID")
    private Long tenantId;

    /**
     * 资源类型：API、MODEL、STORAGE等
     */
    @Column(name = "resource_type", nullable = false, length = 50)
    @Comment("资源类型")
    private String resourceType;

    /**
     * 资源型号：GPT-4、GPT-3.5、Claude等
     */
    @Column(name = "resource_model", length = 100)
    @Comment("资源型号")
    private String resourceModel;

    /**
     * 计费模式：PER_UNIT（按量计费）、TIERED（阶梯计费）、MONTHLY（包月计费）
     */
    @Column(name = "pricing_mode", nullable = false, length = 20)
    @Comment("计费模式")
    private String pricingMode;

    /**
     * 单价（按量计费时使用）
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
     * 阶梯配置（阶梯计费时使用），JSON格式
     */
    @Column(name = "tiers", columnDefinition = "TEXT")
    @Comment("阶梯配置")
    private String tiers;

    /**
     * 月费（包月计费时使用）
     */
    @Column(name = "monthly_fee", precision = 10, scale = 4)
    @Comment("月费")
    private BigDecimal monthlyFee;

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
