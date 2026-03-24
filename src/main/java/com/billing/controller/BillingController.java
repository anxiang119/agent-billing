package com.billing.controller;

import com.billing.common.Response;
import com.billing.dto.BillingDTO;
import com.billing.service.billing.BillingEngineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 计费Controller
 * 提供计费相关的REST API
 */
@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
@Tag(name = "计费管理", description = "计费相关接口")
public class BillingController {

    private final BillingEngineService billingEngineService;

    @Operation(summary = "预估费用", description = "根据资源使用量预估费用")
    @PostMapping("/estimate")
    public Response<BillingDTO.EstimateResponse> estimateFee(@Valid @RequestBody BillingDTO.EstimateRequest request) {
        BillingDTO.EstimateResponse response = billingEngineService.estimateFee(request);
        return Response.success(response);
    }

    @Operation(summary = "预扣费用", description = "预扣预估费用并锁定余额")
    @PostMapping("/pre-deduct")
    public Response<BillingDTO.PreDeductResponse> preDeduct(@Valid @RequestBody BillingDTO.PreDeductRequest request) {
        BillingDTO.PreDeductResponse response = billingEngineService.preDeduct(request);
        return Response.success(response);
    }

    @Operation(summary = "结算费用", description = "根据实际使用量结算费用并退款差额")
    @PostMapping("/settle")
    public Response<BillingDTO.SettleResponse> settle(@Valid @RequestBody BillingDTO.SettleRequest request) {
        BillingDTO.SettleResponse response = billingEngineService.settle(request);
        return Response.success(response);
    }
}
