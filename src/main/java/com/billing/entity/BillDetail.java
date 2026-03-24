package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bill_detail", indexes = {
    @Index(name = "idx_bill_detail_bill_id", columnList = "bill_id"),
    @Index(name = "idx_bill_detail_resource_type", columnList = "resource_type")
})
@Comment("Bill detail table")
public class BillDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("Detail ID")
    private Long id;

    @Column(name = "bill_id", nullable = false)
    @Comment("Bill ID")
    private Long billId;

    @Column(name = "consumption_record_id")
    @Comment("Consumption record ID")
    private Long consumptionRecordId;

    @Column(name = "resource_type", nullable = false, length = 50)
    @Comment("Resource type")
    private String resourceType;

    @Column(name = "resource_model", length = 100)
    @Comment("Resource model")
    private String resourceModel;

    @Column(name = "quantity", nullable = false, precision = 15, scale = 4)
    @Comment("Quantity")
    private BigDecimal quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 4)
    @Comment("Unit price")
    private BigDecimal unitPrice;

    @Column(name = "amount", nullable = false, precision = 15, scale = 4)
    @Comment("Amount")
    private BigDecimal amount;

    @Column(name = "discount_amount", precision = 10, scale = 4)
    @Comment("Discount amount")
    private BigDecimal discountAmount;

    @Column(name = "actual_amount", precision = 15, scale = 4)
    @Comment("Actual amount")
    private BigDecimal actualAmount;

    @Column(name = "consumption_time", nullable = false)
    @Comment("Consumption time")
    private LocalDateTime consumptionTime;

    @Column(name = "created_time", nullable = false, updatable = false)
    @Comment("Created time")
    private LocalDateTime createdTime;

    @PrePersist
    protected void onCreate() {
        if (createdTime == null) {
            createdTime = LocalDateTime.now();
        }
    }
}
