# Agent Billing System 安装部署手册

## 系统要求

- Java 17+
- Maven 3.9+
- MySQL 8.0+
- Redis 6.0+
- RabbitMQ 3.9+ (可选，用于消息队列)

## 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/your-org/agent-billing.git
cd agent-billing
```

### 2. 配置数据库

**方式1：使用环境变量（推荐）**

复制 `.env.example` 为 `.env` 并修改配置：

```bash
cp .env.example .env
# 编辑 .env 文件，设置正确的数据库连接信息
```

**方式2：修改配置文件**

编辑 `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/agent_billing?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root
    password: your_password
  flyway:
    url: jdbc:mysql://localhost:3306/agent_billing?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    user: root
    password: your_password
```

### 3. 创建数据库

```bash
mysql -u root -p
CREATE DATABASE agent_billing CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

**设置数据库用户权限（重要）：**

```sql
-- 创建专用用户
CREATE USER 'billing_user'@'localhost' IDENTIFIED BY 'your_secure_password';

-- 授权访问数据库
GRANT ALL PRIVILEGES ON agent_billing.* TO 'billing_user'@'localhost';

-- 刷新权限
FLUSH PRIVILEGES;
```

### 4. 运行应用

**使用环境变量：**

```bash
export DB_USERNAME=billing_user
export DB_PASSWORD=your_secure_password
mvn clean spring-boot:run
```

**或使用 Spring Profile：**

```bash
mvn clean spring-boot:run -Dspring-boot.run.profiles=dev
```

**禁用 Flyway（如果不需要数据库迁移）：**

```bash
export FLYWAY_ENABLED=false
mvn clean spring-boot:run
```

访问: http://localhost:8080

## 部署

### 开发环境

直接使用 `mvn spring-boot:run`

### 生产环境

```bash
mvn clean package
java -jar target/agent-billing-1.0.0.jar
```

使用 Nginx 反向代理到 8080 端口。

## 配置说明

### 环境变量

| 变量名 | 描述 | 默认值 |
|--------|------|--------|
| DB_URL | 数据库JDBC URL | jdbc:mysql://localhost:3306/agent_billing |
| DB_USERNAME | 数据库用户名 | root |
| DB_PASSWORD | 数据库密码 | root |
| REDIS_HOST | Redis 主机 | localhost |
| REDIS_PORT | Redis 端口 | 6379 |
| REDIS_PASSWORD | Redis 密码 | (空) |
| RABBITMQ_HOST | RabbitMQ 主机 | localhost |
| RABBITMQ_PORT | RabbitMQ 端口 | 5672 |
| FLYWAY_ENABLED | 是否启用 Flyway | true |
| SERVER_PORT | 服务端口 | 8080 |

## API 文档

启动后访问: http://localhost:8080/swagger-ui.html

## 监控

- Health Check: http://localhost:8080/actuator/health
- Metrics: http://localhost:8080/actuator/metrics

## 故障排查

### 数据库连接失败

错误：`Access denied for user 'root'@'localhost'`

**解决方案：**

1. 检查 MySQL 用户密码是否正确
2. 确认数据库存在：`mysql -u root -p -e "SHOW DATABASES;"`
3. 检查用户权限：`mysql -u root -p -e "SHOW GRANTS FOR 'username'@'localhost';"`
4. 创建专用用户并授权（见上方步骤3）

### 其他常见问题

1. 查看日志: `tail -f logs/agent-billing.log`
2. 数据库连接测试: `mysql -u username -p -D agent_billing`
3. Redis 连接测试: `redis-cli ping`
4. 端口占用检查: `netstat -tlnp | grep 8080`

## 定时任务

- 月度账单生成: 每月1日凌晨2点自动执行
- 预算检查: 每小时检查一次
- 数据归档: 每周日凌晨3点自动执行

## 安全建议

1. 使用专用数据库用户，避免使用 root
2. 设置强密码
3. 配置防火墙规则
4. 启用 SSL/TLS 连接
5. 定期备份数据库
