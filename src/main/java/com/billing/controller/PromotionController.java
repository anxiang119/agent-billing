package com.billing.controller;

import com.billing.common.Response;
import com.billing.entity.Promotion;
import com.billing.service.promotion.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 优惠Controller
 * 提供优惠管理的REST API
 */
@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
@Tag(name = "优惠管理", description = "优惠相关接口")
public class PromotionController {

    private final PromotionService promotionService;

    @Operation(summary = "创建优惠活动", description = "创建新的优惠活动")
    @PostMapping
    public Response<Promotion> createPromotion(@Valid @RequestBody Promotion promotion) {
        Promotion result = promotionService.createPromotion(promotion);
        return Response.success(result);
    }

    @Operation(summary = "根据ID获取优惠活动", description = "根据活动ID查询优惠活动详情")
    @GetMapping("/{id}")
    public Response<Promotion> getPromotionById(
            @Parameter(description = "优惠活动ID", required = true)
            @PathVariable Long id) {
        Promotion promotion = promotionService.getPromotionById(id);
        return Response.success(promotion);
    }

    @Operation(summary = "查询优惠活动列表", description = "根据租户ID查询优惠活动列表")
    @GetMapping("/tenant/{tenantId}")
    public Response<List<Promotion>> getPromotionsByTenantId(
            @Parameter(description = "租户ID", required = true)
            @PathVariable Long tenantId) {
        List<Promotion> promotions = promotionService.getPromotionsByTenantId(tenantId);
        return Response.success(promotions);
    }

    @Operation(summary = "查询有效优惠活动", description = "查询指定时间点有效的优惠活动")
    @GetMapping("/tenant/{tenantId}/active")
    public Response<List<Promotion>> getActivePromotions(
            @Parameter(description = "租户ID", required = true)
            @PathVariable Long tenantId,
            @Parameter(description = "时间点")
            @RequestParam(required = false) LocalDateTime time) {
        if (time == null) {
            time = LocalDateTime.now();
        }
        List<Promotion> promotions = promotionService.getActivePromotions(tenantId, time);
        return Response.success(promotions);
    }

    @Operation(summary = "更新优惠活动", description = "更新优惠活动信息")
    @PutMapping("/{id}")
    public Response<Promotion> updatePromotion(
            @Parameter(description = "优惠活动ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody Promotion promotion) {
        Promotion result = promotionService.updatePromotion(id, promotion);
        return Response.success(result);
    }

    @Operation(summary = "删除优惠活动", description = "根据ID删除优惠活动")
    @DeleteMapping("/{id}")
    public Response<Void> deletePromotion(
            @Parameter(description = "优惠活动ID", required = true)
            @PathVariable Long id) {
        promotionService.deletePromotion(id);
        return Response.success("删除成功", null);
    }
}
