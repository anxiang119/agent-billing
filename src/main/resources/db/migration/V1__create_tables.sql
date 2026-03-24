-- 智能体计费系统 - 数据库建表脚本
-- 版本：V1
-- 创建时间：2026-03-19

-- =====================================================
-- 用户与租户相关表
-- =====================================================

-- 租户表
CREATE TABLE tenant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '租户ID',
    tenant_code VARCHAR(64) NOT NULL COMMENT '租户编码',
    tenant_name VARCHAR(128) NOT NULL COMMENT '租户名称',
    contact_name VARCHAR(64) COMMENT '联系人姓名',
    contact_phone VARCHAR(32) COMMENT '联系电话',
    contact_email VARCHAR(128) COMMENT '联系邮箱',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0:禁用,1:启用)',
    credit_limit DECIMAL(12,2) DEFAULT 0.00 COMMENT '信用额度',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_tenant_code (tenant_code),
    KEY idx_status (status),
    KEY idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户表';

-- 用户表
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    tenant_id BIGINT NOT NULL COMMENT '所属租户ID',
    user_code VARCHAR(64) NOT NULL COMMENT '用户编码',
    username VARCHAR(64) NOT NULL COMMENT '用户名',
    email VARCHAR(128) COMMENT '邮箱',
    phone VARCHAR(32) COMMENT '手机号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0:禁用,1:启用,2:冻结)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_user_code (user_code),
    UNIQUE KEY uk_email (email),
    KEY idx_tenant_id (tenant_id),
    KEY idx_status (status),
    KEY idx_created_at (created_at),
    CONSTRAINT fk_user_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 账户表
CREATE TABLE account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '账户ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    balance DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '账户余额',
    frozen_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '冻结金额(预扣中)',
    total_recharge DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '累计充值金额',
    total_consume DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '累计消费金额',
    version BIGINT NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_user_id (user_id),
    KEY idx_tenant_id (tenant_id),
    CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_account_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户表';

-- =====================================================
-- 定价配置相关表
-- =====================================================

-- 定价配置表
CREATE TABLE pricing_config (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '配置ID',
    resource_type VARCHAR(32) NOT NULL COMMENT '资源类型(TOKEN,OSS,API)',
    billing_mode VARCHAR(16) NOT NULL COMMENT '计费模式(MONTHLY,USAGE)',
    model VARCHAR(64) COMMENT '模型名称',
    config_level VARCHAR(16) DEFAULT 'SYSTEM' COMMENT '配置级别(SYSTEM,TENANT)',
    tenant_id BIGINT COMMENT '租户ID(租户级配置)',
    config_json TEXT NOT NULL COMMENT '定价配置JSON',
    valid_from DATETIME NOT NULL COMMENT '生效开始时间',
    valid_to DATETIME COMMENT '生效结束时间',
    status TINYINT DEFAULT 1 COMMENT '状态(0:禁用,1:启用)',
    priority INT DEFAULT 0 COMMENT '优先级',
    created_by BIGINT COMMENT '创建人ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_resource_type (resource_type),
    KEY idx_billing_mode (billing_mode),
    KEY idx_model (model),
    KEY idx_tenant_id (tenant_id),
    KEY idx_valid_period (valid_from, valid_to),
    KEY idx_status (status),
    CONSTRAINT fk_pricing_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定价配置表';

-- 定价配置历史表
CREATE TABLE pricing_config_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '历史ID',
    config_id BIGINT COMMENT '原配置ID',
    config_json TEXT NOT NULL COMMENT '定价配置JSON',
    change_type VARCHAR(16) NOT NULL COMMENT '变更类型(CREATE,UPDATE,DELETE)',
    changed_by BIGINT COMMENT '变更人ID',
    changed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '变更时间',
    reason VARCHAR(512) COMMENT '变更原因',
    KEY idx_config_id (config_id),
    KEY idx_change_type (change_type),
    KEY idx_changed_at (changed_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定价配置历史表';

-- =====================================================
-- 消费记录相关表
-- =====================================================

-- 消费记录表
CREATE TABLE consumption_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
    record_no VARCHAR(64) NOT NULL COMMENT '记录编号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    resource_type VARCHAR(32) NOT NULL COMMENT '资源类型',
    model VARCHAR(64) COMMENT '模型名称',
    billing_month VARCHAR(7) NOT NULL COMMENT '计费月份(YYYY-MM)',
    usage_amount BIGINT NOT NULL COMMENT '使用量',
    unit VARCHAR(16) NOT NULL COMMENT '计量单位',
    unit_price DECIMAL(10,4) NOT NULL COMMENT '单价',
    amount DECIMAL(12,2) NOT NULL COMMENT '金额',
    discount_amount DECIMAL(12,2) DEFAULT 0.00 COMMENT '优惠减免金额',
    actual_amount DECIMAL(12,2) NOT NULL COMMENT '实际金额',
    pre_deduct_id VARCHAR(64) COMMENT '预扣记录ID',
    status TINYINT DEFAULT 0 COMMENT '状态(0:预扣,1:已结算)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_record_no (record_no),
    KEY idx_user_id (user_id),
    KEY idx_tenant_id (tenant_id),
    KEY idx_resource_type (resource_type),
    KEY idx_billing_month (billing_month),
    KEY idx_status (status),
    KEY idx_created_at (created_at),
    CONSTRAINT fk_consumption_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_consumption_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消费记录表';

-- 预扣记录表
CREATE TABLE pre_deduction_record (
    id VARCHAR(64) NOT NULL COMMENT '预扣ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    agent_call_id VARCHAR(64) NOT NULL COMMENT '智能体调用ID',
    estimated_amount DECIMAL(12,2) NOT NULL COMMENT '预估金额',
    actual_amount DECIMAL(12,2) COMMENT '实际金额',
    refund_amount DECIMAL(12,2) DEFAULT 0.00 COMMENT '退款金额',
    supplement_amount DECIMAL(12,2) DEFAULT 0.00 COMMENT '补扣金额',
    status TINYINT DEFAULT 0 COMMENT '状态(0:预扣中,1:已结算,2:已退款)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    settled_at DATETIME COMMENT '结算时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_tenant_id (tenant_id),
    KEY idx_agent_call_id (agent_call_id),
    KEY idx_status (status),
    CONSTRAINT fk_pre_deduct_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_pre_deduct_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预扣记录表';

-- 充值记录表
CREATE TABLE recharge_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '充值ID',
    record_no VARCHAR(64) NOT NULL COMMENT '充值单号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    amount DECIMAL(12,2) NOT NULL COMMENT '充值金额',
    payment_method VARCHAR(32) NOT NULL COMMENT '支付方式',
    payment_no VARCHAR(128) COMMENT '第三方支付单号',
    status TINYINT DEFAULT 0 COMMENT '状态(0:待支付,1:已支付,2:已取消,3:已退款)',
    paid_at DATETIME COMMENT '支付时间',
    callback_data TEXT COMMENT '支付回调数据',
    remark VARCHAR(512) COMMENT '备注',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_record_no (record_no),
    KEY idx_user_id (user_id),
    KEY idx_tenant_id (tenant_id),
    KEY idx_payment_no (payment_no),
    KEY idx_status (status),
    CONSTRAINT fk_recharge_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_recharge_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值记录表';

-- =====================================================
-- 优惠相关表
-- =====================================================

-- 优惠活动表
CREATE TABLE promotion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '优惠ID',
    promotion_code VARCHAR(64) NOT NULL COMMENT '优惠编码',
    promotion_name VARCHAR(128) NOT NULL COMMENT '优惠名称',
    promotion_type VARCHAR(16) NOT NULL COMMENT '优惠类型(MONEY_OFF)',
    resource_types VARCHAR(128) NOT NULL COMMENT '适用资源类型(逗号分隔)',
    tenant_ids TEXT COMMENT '适用租户ID(JSON数组)',
    rules_json TEXT NOT NULL COMMENT '优惠规则JSON',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    status TINYINT DEFAULT 1 COMMENT '状态(0:禁用,1:启用)',
    created_by BIGINT COMMENT '创建人ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_promotion_code (promotion_code),
    KEY idx_promotion_type (promotion_type),
    KEY idx_status (status),
    KEY idx_time_range (start_time, end_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠活动表';

-- 用户优惠记录表
CREATE TABLE user_promotion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    promotion_id BIGINT NOT NULL COMMENT '优惠ID',
    billing_month VARCHAR(7) NOT NULL COMMENT '计费月份(YYYY-MM)',
    total_discount DECIMAL(12,2) NOT NULL COMMENT '累计优惠金额',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    KEY idx_user_id (user_id),
    KEY idx_promotion_id (promotion_id),
    KEY idx_billing_month (billing_month),
    CONSTRAINT fk_user_promotion_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_user_promotion_promotion FOREIGN KEY (promotion_id) REFERENCES promotion(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户优惠记录表';

-- =====================================================
-- 预算与预警相关表
-- =====================================================

-- 用户预算表
CREATE TABLE user_budget (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '预算ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    monthly_limit DECIMAL(12,2) NOT NULL COMMENT '月度预算上限',
    alert_threshold INT DEFAULT 80 COMMENT '预警阈值(百分比)',
    alert_methods VARCHAR(32) DEFAULT 'EMAIL' COMMENT '预警方式(EMAIL)',
    service_status TINYINT DEFAULT 0 COMMENT '服务状态(0:正常,1:已停止)',
    stop_reason VARCHAR(256) COMMENT '停止原因',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_user_id (user_id),
    KEY idx_tenant_id (tenant_id),
    CONSTRAINT fk_budget_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_budget_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户预算表';

-- 预警记录表
CREATE TABLE alert_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    alert_type VARCHAR(16) NOT NULL COMMENT '预警类型(BUDGET_WARN,BUDGET_EXCEEDED)',
    alert_level INT NOT NULL COMMENT '预警级别',
    current_amount DECIMAL(12,2) NOT NULL COMMENT '当前消费金额',
    budget_limit DECIMAL(12,2) NOT NULL COMMENT '预算上限',
    message TEXT NOT NULL COMMENT '预警消息',
    is_sent TINYINT DEFAULT 0 COMMENT '是否已发送',
    sent_at DATETIME COMMENT '发送时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    KEY idx_user_id (user_id),
    KEY idx_tenant_id (tenant_id),
    KEY idx_alert_type (alert_type),
    KEY idx_created_at (created_at),
    CONSTRAINT fk_alert_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_alert_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预警记录表';

-- =====================================================
-- 账单相关表
-- =====================================================

-- 账单表
CREATE TABLE bill (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '账单ID',
    bill_no VARCHAR(64) NOT NULL COMMENT '账单编号',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    billing_month VARCHAR(7) NOT NULL COMMENT '计费月份(YYYY-MM)',
    total_amount DECIMAL(12,2) NOT NULL COMMENT '总金额',
    discount_amount DECIMAL(12,2) DEFAULT 0.00 COMMENT '优惠减免金额',
    actual_amount DECIMAL(12,2) NOT NULL COMMENT '实际金额',
    paid_amount DECIMAL(12,2) DEFAULT 0.00 COMMENT '已支付金额',
    recharge_amount DECIMAL(12,2) DEFAULT 0.00 COMMENT '充值金额',
    status TINYINT DEFAULT 0 COMMENT '状态(0:待支付,1:已支付,2:已作废)',
    generated_at DATETIME NOT NULL COMMENT '生成时间',
    paid_at DATETIME COMMENT '支付时间',
    remark VARCHAR(512) COMMENT '备注',
    UNIQUE KEY uk_bill_no (bill_no),
    KEY idx_tenant_id (tenant_id),
    KEY idx_billing_month (billing_month),
    KEY idx_status (status),
    CONSTRAINT fk_bill_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账单表';

-- 账单明细表
CREATE TABLE bill_detail (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '明细ID',
    bill_id BIGINT NOT NULL COMMENT '账单ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    resource_type VARCHAR(32) NOT NULL COMMENT '资源类型',
    usage_amount BIGINT NOT NULL COMMENT '使用量',
    unit VARCHAR(16) NOT NULL COMMENT '计量单位',
    amount DECIMAL(12,2) NOT NULL COMMENT '金额',
    discount_amount DECIMAL(12,2) DEFAULT 0.00 COMMENT '优惠减免金额',
    actual_amount DECIMAL(12,2) NOT NULL COMMENT '实际金额',
    KEY idx_bill_id (bill_id),
    KEY idx_user_id (user_id),
    KEY idx_resource_type (resource_type),
    CONSTRAINT fk_bill_detail_bill FOREIGN KEY (bill_id) REFERENCES bill(id),
    CONSTRAINT fk_bill_detail_user FOREIGN KEY (user_id) REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账单明细表';

-- =====================================================
-- 发票相关表
-- =====================================================

-- 发票表
CREATE TABLE invoice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '发票ID',
    invoice_no VARCHAR(64) NOT NULL COMMENT '发票编号',
    bill_id BIGINT NOT NULL COMMENT '账单ID',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    title VARCHAR(256) NOT NULL COMMENT '发票抬头',
    tax_no VARCHAR(32) COMMENT '税号',
    address_phone VARCHAR(256) COMMENT '地址电话',
    bank_account VARCHAR(256) COMMENT '开户行及账号',
    amount DECIMAL(12,2) NOT NULL COMMENT '发票金额',
    status TINYINT DEFAULT 0 COMMENT '状态(0:开具中,1:已开具,2:已作废)',
    invoice_url VARCHAR(512) COMMENT '发票下载地址',
    invoice_data TEXT COMMENT '第三方发票数据',
    applied_by BIGINT COMMENT '申请人ID',
    applied_at DATETIME NOT NULL COMMENT '申请时间',
    issued_at DATETIME COMMENT '开具时间',
    remark VARCHAR(512) COMMENT '备注',
    UNIQUE KEY uk_invoice_no (invoice_no),
    KEY idx_bill_id (bill_id),
    KEY idx_tenant_id (tenant_id),
    KEY idx_status (status),
    CONSTRAINT fk_invoice_bill FOREIGN KEY (bill_id) REFERENCES bill(id),
    CONSTRAINT fk_invoice_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='发票表';

-- =====================================================
-- 审计日志相关表
-- =====================================================

-- 审计日志表
CREATE TABLE audit_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
    user_id BIGINT COMMENT '操作人ID',
    tenant_id BIGINT COMMENT '租户ID',
    module VARCHAR(64) NOT NULL COMMENT '操作模块',
    action VARCHAR(64) NOT NULL COMMENT '操作动作',
    resource_id VARCHAR(64) COMMENT '资源ID',
    resource_type VARCHAR(64) COMMENT '资源类型',
    request_data TEXT COMMENT '请求参数',
    result_data TEXT COMMENT '返回结果',
    ip_address VARCHAR(64) COMMENT 'IP地址',
    user_agent VARCHAR(512) COMMENT '用户代理',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    KEY idx_user_id (user_id),
    KEY idx_tenant_id (tenant_id),
    KEY idx_module (module),
    KEY idx_action (action),
    KEY idx_created_at (created_at),
    CONSTRAINT fk_audit_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_audit_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审计日志表';
