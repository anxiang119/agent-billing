package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 发票实体类
 * 对应数据库表：invoice
 */
@Data
@Entity
@Table(name = "invoice", indexes = {
    @Index(name = "idx_invoice_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_invoice_user_id", columnList = "user_id"),
    @Index(name = "idx_invoice_bill_id", columnList = "bill_id"),
    @Index(name = "idx_invoice_status", columnList = "status")
})
@Comment("发票表")
public class Invoice {

    /**
     * 发票ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("发票ID")
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
     * 账单ID
     */
    @Column(name = "bill_id", nullable = false)
    @Comment("账单ID")
    private Long billId;

    /**
     * 发票号码
     */
    @Column(name = "invoice_no", length = 50)
    @Comment("发票号码")
    private String invoiceNo;

    /**
     * 发票类型：ELECTRONIC-电子发票，PAPER-纸质发票
     */
    @Column(name = "invoice_type", nullable = false, length = 20)
    @Comment("发票类型")
    private String invoiceType;

    /**
     * 发票金额
     */
    @Column(name = "amount", nullable = false, precision = 15, scale = 4)
    @Comment("发票金额")
    private BigDecimal amount;

    /**
     * 货币单位
     */
    @Column(name = "currency", length = 10)
    @Comment("货币单位")
    private String currency;

    /**
     * 状态：PENDING-待开具，ISSUED-已开具，CANCELLED-已作废
     */
    @Column(name = "status", nullable = false, length = 20)
    @Comment("状态")
    private String status;

    /**
     * 开票时间
     */
    @Column(name = "issued_time")
    @Comment("开票时间")
    private LocalDateTime issuedTime;

    /**
     * 作废时间
     */
    @Column(name = "cancelled_time")
    @Comment("作废时间")
    private LocalDateTime cancelledTime;

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
