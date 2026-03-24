-- 插入初始租户
INSERT INTO tenant (tenant_name, tenant_code, status) VALUES ('默认租户', 'DEFAULT', 1);

-- 插入初始定价配置
-- 使用JSON格式的定价配置
INSERT INTO pricing_config (
    resource_type,
    billing_mode,
    model,
    config_level,
    tenant_id,
    config_json,
    valid_from,
    status,
    priority,
    created_by
) VALUES (
    'API',
    'USAGE',
    'GPT-4',
    'SYSTEM',
    NULL,
    '{"unit_price": 0.01, "currency": "CNY", "description": "GPT-4 API调用定价"}',
    NOW(),
    1,
    100,
    NULL
);
