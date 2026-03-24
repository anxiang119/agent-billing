package com.billing.service;

import com.billing.common.Response;
import com.billing.dto.PricingConfigDTO;
import com.billing.entity.PricingConfig;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定价配置Service接口
 * 提供定价配置业务逻辑
 */
public interface PricingConfigService {

    /**
     * 创建定价配置
     *
     * @param request 创建请求
     * @return 定价配置
     */
    PricingConfig createConfig(PricingConfigDTO.CreateRequest request);

    /**
     * 根据ID获取定价配置
     *
     * @param id 配置ID
     * @return 定价配置
     */
    PricingConfig getConfigById(Long id);

    /**
     * 查询定价配置列表
     *
     * @param request 查询请求
     * @return 定价配置列表
     */
    List<PricingConfig> queryConfigs(PricingConfigDTO.QueryRequest request);

    /**
     * 更新定价配置
     *
     * @param id 配置ID
     * @param request 更新请求
     * @return 更新后的定价配置
     */
    PricingConfig updateConfig(Long id, PricingConfigDTO.UpdateRequest request);

    /**
     * 删除定价配置
     *
     * @param id 配置ID
     */
    void deleteConfig(Long id);

    /**
     * 获取指定时间点生效的定价配置
     *
     * @param tenantId 租户ID
     * @param resourceType 资源类型
     * @param resourceModel 资源型号
     * @param time 时间点
     * @return 生效的定价配置
     */
    PricingConfig getActiveConfig(Long tenantId, String resourceType, String resourceModel, LocalDateTime time);
}
