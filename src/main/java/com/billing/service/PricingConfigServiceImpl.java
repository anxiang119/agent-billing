package com.billing.service;

import com.billing.common.Response;
import com.billing.dto.PricingConfigDTO;
import com.billing.entity.PricingConfig;
import com.billing.exception.BusinessException;
import com.billing.repository.PricingConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定价配置Service实现
 */
@Service
@RequiredArgsConstructor
public class PricingConfigServiceImpl implements PricingConfigService {

    private final PricingConfigRepository repository;

    @Override
    @Transactional
    public PricingConfig createConfig(PricingConfigDTO.CreateRequest request) {
        if (request == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "请求参数不能为空");
        }

        if (request.getTenantId() == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "租户ID不能为空");
        }

        if (request.getResourceType() == null || request.getResourceType().trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "资源类型不能为空");
        }

        if (request.getPricingMode() == null || request.getPricingMode().trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "计费模式不能为空");
        }

        LocalDateTime startTime = request.getEffectiveStartTime();
        if (startTime == null) {
            startTime = LocalDateTime.now();
            request.setEffectiveStartTime(startTime);
        }

        LocalDateTime endTime = request.getEffectiveEndTime();

        if (endTime != null && endTime.isBefore(startTime)) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "结束时间不能早于开始时间");
        }

        if (repository.hasConflict(request.getTenantId(), request.getResourceType(),
                request.getResourceModel(), startTime, endTime)) {
            throw new BusinessException(Response.ResponseCode.BUSINESS_ERROR.getCode(),
                    "时间范围与已有配置冲突");
        }

        PricingConfig config = new PricingConfig();
        BeanUtils.copyProperties(request, config);

        return repository.save(config);
    }

    @Override
    public PricingConfig getConfigById(Long id) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "配置ID不能为空");
        }

        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.NOT_FOUND.getCode(), "定价配置不存在"));
    }

    @Override
    public List<PricingConfig> queryConfigs(PricingConfigDTO.QueryRequest request) {
        if (request == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "请求参数不能为空");
        }

        if (request.getTenantId() != null) {
            return repository.findByTenantId(request.getTenantId());
        }

        return repository.findAll();
    }

    @Override
    @Transactional
    public PricingConfig updateConfig(Long id, PricingConfigDTO.UpdateRequest request) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "配置ID不能为空");
        }

        if (request == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "请求参数不能为空");
        }

        PricingConfig config = getConfigById(id);

        if (request.getPricingMode() != null) {
            config.setPricingMode(request.getPricingMode());
        }

        if (request.getUnitPrice() != null) {
            config.setUnitPrice(request.getUnitPrice());
        }

        if (request.getCurrency() != null) {
            config.setCurrency(request.getCurrency());
        }

        if (request.getTiers() != null) {
            config.setTiers(request.getTiers());
        }

        if (request.getMonthlyFee() != null) {
            config.setMonthlyFee(request.getMonthlyFee());
        }

        if (request.getEffectiveStartTime() != null) {
            config.setEffectiveStartTime(request.getEffectiveStartTime());
        }

        if (request.getEffectiveEndTime() != null) {
            config.setEffectiveEndTime(request.getEffectiveEndTime());
        }

        if (request.getStatus() != null) {
            config.setStatus(request.getStatus());
        }

        return repository.save(config);
    }

    @Override
    @Transactional
    public void deleteConfig(Long id) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "配置ID不能为空");
        }

        PricingConfig config = getConfigById(id);
        repository.delete(config);
    }

    @Override
    public PricingConfig getActiveConfig(Long tenantId, String resourceType, String resourceModel, LocalDateTime time) {
        if (tenantId == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "租户ID不能为空");
        }

        if (resourceType == null || resourceType.trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "资源类型不能为空");
        }

        if (time == null) {
            time = LocalDateTime.now();
        }

        List<PricingConfig> configs = repository.findActiveConfig(tenantId, resourceType, resourceModel, time);

        if (configs.isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PRICING_CONFIG_NOT_FOUND.getCode(),
                    "找不到生效的定价配置");
        }

        return configs.get(0);
    }
}
