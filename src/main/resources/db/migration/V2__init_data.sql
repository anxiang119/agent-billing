-- 插入初始租户
INSERT INTO tenant (name, code, status) VALUES ('默认租户', 'DEFAULT', 'ACTIVE');

-- 插入初始定价配置
INSERT INTO pricing_config (tenant_id, resource_type, resource_model, pricing_mode, unit_price, currency, effective_start_time, status)
VALUES (1, 'API', 'GPT-4', 'PER_UNIT', 0.01, 'CNY', NOW(), 'ACTIVE');
