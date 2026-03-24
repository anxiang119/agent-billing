package com.billing.controller;

import com.billing.common.Response;
import com.billing.dto.PricingConfigDTO;
import com.billing.entity.PricingConfig;
import com.billing.service.PricingConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定价配置Controller
 * 提供定价配置管理的REST API
 */
@RestController
@RequestMapping("/api/pricing-configs")
@RequiredArgsConstructor
@Tag(name = "定价配置管理", description = "定价配置相关接口")
public class PricingConfigController {

    private final PricingConfigService pricingConfigService;

    @Operation(summary = "创建定价配置", description = "创建新的定价配置")
    @PostMapping
    public Response<PricingConfigDTO.Response> createConfig(@Valid @RequestBody PricingConfigDTO.CreateRequest request) {
        PricingConfig config = pricingConfigService.createConfig(request);
        return Response.success(PricingConfigDTO.Response.fromEntity(config));
    }

    @Operation(summary = "根据ID获取定价配置", description = "根据配置ID查询定价配置详情")
    @GetMapping("/{id}")
    public Response<PricingConfigDTO.Response> getConfigById(
            @Parameter(description = "配置ID", required = true)
            @PathVariable Long id) {
        PricingConfig config = pricingConfigService.getConfigById(id);
        return Response.success(PricingConfigDTO.Response.fromEntity(config));
    }

    @Operation(summary = "查询定价配置列表", description = "根据条件查询定价配置")
    @GetMapping
    public Response<List<PricingConfigDTO.Response>> queryConfigs(
            @Parameter(description = "租户ID")
            @RequestParam(required = false) Long tenantId,
            @Parameter(description = "资源类型")
            @RequestParam(required = false) String resourceType,
            @Parameter(description = "资源型号")
            @RequestParam(required = false) String resourceModel,
            @Parameter(description = "状态")
            @RequestParam(required = false) String status) {
        PricingConfigDTO.QueryRequest request = new PricingConfigDTO.QueryRequest();
        request.setTenantId(tenantId);
        request.setResourceType(resourceType);
        request.setResourceModel(resourceModel);
        request.setStatus(status);

        List<PricingConfig> configs = pricingConfigService.queryConfigs(request);
        List<PricingConfigDTO.Response> responses = configs.stream()
                .map(PricingConfigDTO.Response::fromEntity)
                .collect(Collectors.toList());
        return Response.success(responses);
    }

    @Operation(summary = "更新定价配置", description = "更新定价配置信息")
    @PutMapping("/{id}")
    public Response<PricingConfigDTO.Response> updateConfig(
            @Parameter(description = "配置ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody PricingConfigDTO.UpdateRequest request) {
        PricingConfig config = pricingConfigService.updateConfig(id, request);
        return Response.success(PricingConfigDTO.Response.fromEntity(config));
    }

    @Operation(summary = "删除定价配置", description = "根据ID删除定价配置")
    @DeleteMapping("/{id}")
    public Response<Void> deleteConfig(
            @Parameter(description = "配置ID", required = true)
            @PathVariable Long id) {
        pricingConfigService.deleteConfig(id);
        return Response.success("删除成功", null);
    }

    @Operation(summary = "获取生效配置", description = "获取指定时间点生效的定价配置")
    @GetMapping("/active")
    public Response<PricingConfigDTO.Response> getActiveConfig(
            @Parameter(description = "租户ID", required = true)
            @RequestParam Long tenantId,
            @Parameter(description = "资源类型", required = true)
            @RequestParam String resourceType,
            @Parameter(description = "资源型号", required = true)
            @RequestParam String resourceModel,
            @Parameter(description = "时间点")
            @RequestParam(required = false) LocalDateTime time) {
        if (time == null) {
            time = LocalDateTime.now();
        }
        PricingConfig config = pricingConfigService.getActiveConfig(tenantId, resourceType, resourceModel, time);
        return Response.success(PricingConfigDTO.Response.fromEntity(config));
    }
}
