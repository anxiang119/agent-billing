package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户预算实体类
 * 对应数据库表：user_budget
 */
@Data
@Entity
@Table(name = "user_budget", indexes = {
    @Index(name = "idx_user_budget_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_user_budget_user_id", columnList = "user_id"),
    @Index(name = "idx_user_budget_type", columnList = "budget_type"),
    @Index(name = "idx_user_budget_status", columnList = "status")
})
@SQLDelete(sql = "UPDATE user_budget SET status = 'DELETED', updated_time = NOW() WHERE id = ?")
@Where(clause = "status != 'DELETED'")
@Comment("用户预算表")
public class UserBudget {

    /**
     * 预算ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("预算ID")
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
     * 预算类型：MONTHLY-月度，QUARTERLY-季度，YEARLY-年度
     */
    @Column(name = "budget_type", nullable = false, length = 20)
    @Comment("预算类型")
    private String budgetType;

    /**
     * 预算金额
     */
    @Column(name = "budget_amount", nullable = false, precision = 15, scale = 4)
    @Comment("预算金额")
    private BigDecimal budgetAmount;

    /**
     * 货币单位
     */
    @Column(name = "currency", length = 10)
    @Comment("货币单位")
    private String currency;

    /**
     * 预警阈值（百分比，如80表示达到80%时预警）
     */
    @Column(name = "alert_threshold", precision = 5, scale = 2)
    @Comment("预警阈值")
    private BigDecimal alertThreshold;

    /**
     * 超限处理方式：STOP-停止服务，ALERT-仅预警
     */
    @Column(name = "exceed_action", length = 20)
    @Comment("超限处理方式")
    private String exceedAction;

    /**
     * 状态：ACTIVE-启用，INACTIVE-停用，DELETED-已删除
     */
    @Column(name = "status", nullable = false, length = 20)
    @Comment("状态")
    private String status;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    @Comment("开始时间")
    private LocalDateTime startTime;

    /**
    * 结束时间
    */
    @Column(name = "end_time")
    @Comment("结束时间")
    private LocalDateTime endTime;

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
