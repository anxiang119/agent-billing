package com.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "audit_log", indexes = {
    @Index(name = "idx_audit_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_audit_user_id", columnList = "user_id"),
    @Index(name = "idx_audit_module", columnList = "module")
})
@Comment("审计日志表")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("日志ID")
    private Long id;

    @Column(name = "tenant_id")
    @Comment("租户ID")
    private Long tenantId;

    @Column(name = "user_id")
    @Comment("用户ID")
    private Long userId;

    @Column(name = "operation", length = 50)
    @Comment("操作类型")
    private String operation;

    @Column(name = "module", length = 50)
    @Comment("模块")
    private String module;

    @Column(name = "request_data", columnDefinition = "TEXT")
    @Comment("请求数据")
    private String requestData;

    @Column(name = "response_data", columnDefinition = "TEXT")
    @Comment("响应数据")
    private String responseData;

    @Column(name = "ip_address", length = 50)
    @Comment("IP地址")
    private String ipAddress;

    @Column(name = "created_time", nullable = false, updatable = false)
    @Comment("创建时间")
    private LocalDateTime createdTime;

    @PrePersist
    protected void onCreate() {
        if (createdTime == null) {
            createdTime = LocalDateTime.now();
        }
    }
}
