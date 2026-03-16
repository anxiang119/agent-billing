package com.billing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目基础配置类
 * 加载application.yml配置，定义各种配置属性
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "billing")
public class BillingConfig {

    /**
     * 应用名称
     */
    private String appName = "agent-billing";

    /**
     * 应用版本
     */
    private String appVersion = "1.0.0";

    /**
     * 最大并发数
     */
    private int maxConcurrency = 100;

    /**
     * 请求超时时间（毫秒）
     */
    private int requestTimeout = 30000;

    /**
     * 是否启用计费
     */
    private boolean billingEnabled = true;

    /**
     * 是否启用审计日志
     */
    private boolean auditLogEnabled = true;

    /**
     * 是否启用预算预警
     */
    private boolean budgetAlertEnabled = true;
}
