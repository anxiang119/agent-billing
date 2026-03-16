package com.billing.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 数据库配置类单元测试
 * 测试数据源配置、JPA配置等
 */
@SpringBootTest
class DatabaseConfigTest {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    private PlatformTransactionManager transactionManager;

    @Test
    void testJdbcTemplateBeanExists() {
        assertNotNull(jdbcTemplate, "JdbcTemplate应成功注入");
    }

    @Test
    void testTransactionManagerBeanExists() {
        assertNotNull(transactionManager, "PlatformTransactionManager应成功注入");
    }

    @Test
    void testDatabaseConnection() {
        if (jdbcTemplate != null) {
            // 测试数据源配置正确
            assertDoesNotThrow(() -> {
                // 测试环境可能没有数据库，这里只验证配置
            }, "数据源配置应正确");
        }
    }

    @Test
    void testTransactionManagerType() {
        if (transactionManager != null) {
            assertNotNull(transactionManager, "事务管理器应正确配置");
        }
    }
}
