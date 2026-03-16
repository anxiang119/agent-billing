# 基础架构模块

## 模块概述

基础架构模块为整个计费系统提供基础设施支持，包括配置管理、异常处理、统一响应格式等核心组件。

## 完成任务

### 任务1-2：项目基础配置类
- **测试文件**: `src/test/java/com/billing/config/BillingConfigTest.java`
- **实现文件**: `src/main/java/com/billing/config/BillingConfig.java`
- **功能**: 加载application.yml配置，定义应用名称、版本、并发数等属性

### 任务3-4：Redis配置类
- **测试文件**: `src/test/java/com/billing/config/RedisConfigTest.java`
- **实现文件**: `src/main/java/com/billing/config/RedisConfig.java`
- **功能**: 配置Redis连接池、序列化器（StringRedisSerializer、Jackson2JsonRedisSerializer）

### 任务5-6：数据库配置类
- **测试文件**: `src/test/java/com/billing/config/DatabaseConfigTest.java`
- **实现文件**: `src/main/java/com/billing/config/DatabaseConfig.java`
- **功能**: 配置HikariCP连接池、JPA、事务管理器

### 任务7-8：统一响应类
- **测试文件**: `src/test/java/com/billing/common/ResponseTest.java`
- **实现文件**: `src/main/java/com/billing/common/Response.java`
- **功能**: 定义标准API响应格式，支持成功、失败、错误、分页等响应类型

### 任务9-10：业务异常类
- **测试文件**: `src/test/java/com/billing/exception/BusinessExceptionTest.java`
- **实现文件**: `src/main/java/com/billing/exception/BusinessException.java`
- **功能**: 定义系统业务异常，提供便捷的异常创建方法（insufficientBalance、budgetExceeded等）

### 任务11-12：全局异常处理器
- **测试文件**: `src/test/java/com/billing/exception/GlobalExceptionHandlerTest.java`
- **实现文件**: `src/main/java/com/billing/exception/GlobalExceptionHandler.java`
- **功能**: 统一处理系统异常并返回标准响应，处理业务异常、参数异常、系统异常等

## 核心组件

### 1. BillingConfig
应用配置类，加载配置文件属性：
- `appName`: 应用名称
- `appVersion`: 应用版本
- `maxConcurrency`: 最大并发数
- `requestTimeout`: 请求超时时间
- `billingEnabled`: 是否启用计费
- `auditLogEnabled`: 是否启用审计日志
- `budgetAlertEnabled`: 是否启用预算预警

### 2. RedisConfig
Redis配置：
- HikariCP连接池
- StringRedisSerializer用于Key序列化
- Jackson2JsonRedisSerializer用于Value序列化

### 3. DatabaseConfig
数据库配置：
- HikariCP连接池
- JPA配置（Hibernate方言、批处理等）
- JpaTransactionManager事务管理器

### 4. Response<T>
统一响应格式：
```json
{
  "code": 0,
  "message": "操作成功",
  "data": {}
}
```
- `success()`: 创建成功响应
- `failure()`: 创建失败响应
- `error()`: 创建错误响应
- `page()`: 创建分页响应

### 5. BusinessException
业务异常，包含错误码和错误消息：
- `insufficientBalance()`: 余额不足异常
- `budgetExceeded()`: 预算超限异常
- `userNotFound()`: 用户不存在异常
- `tenantNotFound()`: 租户不存在异常
- `pricingConfigNotFound()`: 定价配置不存在异常
- `paramError()`: 参数错误异常
- `systemError()`: 系统错误异常

### 6. GlobalExceptionHandler
全局异常处理器，统一处理：
- 业务异常（BusinessException）
- 参数校验异常（MethodArgumentNotValidException）
- 参数类型不匹配异常（MethodArgumentTypeMismatchException）
- 空指针异常（NullPointerException）
- 运行时异常（RuntimeException）
- 其他异常（Exception）

## 依赖

```xml
<dependencies>
    <!-- Spring Boot Starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Data Redis -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>

    <!-- Spring Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- HikariCP 连接池 -->
    <dependency>
        <groupId>com.zaxxer</groupId>
        <artifactId>HikariCP</artifactId>
    </dependency>

    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>

    <!-- JUnit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- Mockito Mockito -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## 配置示例

### application.yml
```yaml
billing:
  app:
    name: agent-billing
    version: 1.0.0
  max-concurrency: 100
  request-timeout: 30000
  billing-enabled: true
  audit-log-enabled: true
  budget-alert-enabled: true

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/billing_db
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000

  redis:
    host: localhost
    port: 6379
    password:
    timeout: 30000

  jpa:
    database: mysql
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: false
```

## 测试覆盖

所有类都编写了单元测试，测试覆盖：
- 配置加载和属性注入
- 异常处理和错误码
-`响应格式和工具方法
- 各类异常场景

## 后续任务

基础架构模块完成后，可以继续执行：
- 任务13-30：用户与租户模块
- 任务31-42：定价配置模块
- 任务43-58：计费引擎模块
- 其他模块...
