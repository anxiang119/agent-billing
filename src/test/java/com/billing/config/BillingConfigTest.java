package com.billing.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 项目基础配置类单元测试
 * 测试配置加载、属性注入等功能
 */
@SpringBootTest
@TestPropertySource(properties = {
        "billing.app.name=agent-billing",
        "billing.app.version=1.0.0",
        "billing.max.concurrency=100",
        "billing.request.timeout=30000"
})
class BillingConfigTest {

    @Autowired
    private BillingConfig billingConfig;

    @Test
    void testConfigInjection() {
        assertNotNull(billingConfig, "配置类应成功注入");
    }

    @Test
    void testAppName() {
        assertEquals("agent-billing", billingConfig.getAppName(), "应用名称应正确配置");
    }

    @Test
    void testAppVersion() {
        assertEquals("1.0.0", billingConfig.getAppVersion(), "应用版本应正确配置");
    }

    @Test
    void testMaxConcurrency() {
        assertEquals(100, billingConfig.getMaxConcurrency(), "最大并发数应正确配置");
    }

    @Test
    void testRequestTimeout() {
        assertEquals(30000, billingConfig.getRequestTimeout(), "请求超时时间应正确配置");
    }

    @Test
    void testDefaultValues() {
        // 测试未配置时的默认值
        BillingConfig defaultConfig = new BillingConfig();
        assertNotNull(defaultConfig.getAppName());
        assertNotNull(defaultConfig.getAppVersion());
        assertTrue(defaultConfig.getMaxConcurrency() > 0);
        assertTrue(defaultConfig.getRequestTimeout() > 0);
    }
}
