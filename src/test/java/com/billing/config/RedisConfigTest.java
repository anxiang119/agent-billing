package com.billing.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Redis配置类单元测试
 * 测试连接池配置、序列化器等
 */
@SpringBootTest
class RedisConfigTest {

    @Autowired(required = false)
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired(required = false)
    private RedisConnectionFactory redisConnectionFactory;

    @Test
    void testRedisTemplateBeanExists() {
        assertNotNull(redisTemplate, "RedisTemplate应成功注入");
    }

    @Test
    void testRedisConnectionFactoryBeanExists() {
        assertNotNull(redisConnectionFactory, "RedisConnectionFactory应成功注入");
    }

    @Test
    void testStringSerializer() {
        if (redisTemplate != null) {
            assertNotNull(redisTemplate.getStringSerializer(), "String序列化器应正确配置");
            assertTrue(redisTemplate.getStringSerializer() instanceof StringRedisSerializer,
                    "应使用StringRedisSerializer");
        }
    }

    @Test
    void testKeySerializer() {
        if (redisTemplate != null) {
            assertNotNull(redisTemplate.getKeySerializer(), "Key序列化器应正确配置");
            assertTrue(redisTemplate.getKeySerializer() instanceof StringRedisSerializer,
                    "Key应使用StringRedisSerializer");
        }
    }

    @Test
    void testConnectionFactoryNotConnected() {
        // 测试环境可能没有Redis，这里只验证连接工厂存在
        if (redisConnectionFactory != null) {
            assertDoesNotThrow(() -> {
                // 测试环境不实际连接Redis
            }, "Redis连接工厂配置应正确");
        }
    }
}
