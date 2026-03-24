package com.billing.controller;

import com.billing.common.Response;
import com.billing.dto.PaymentDTO;
import com.billing.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "支付管理", description = "支付相关接口")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "创建支付订单", description = "创建支付订单并获取支付链接")
    @PostMapping("/create")
    public Response<PaymentDTO.CreateOrderResponse> createPaymentOrder(@Valid @RequestBody PaymentDTO.CreateOrderRequest request) {
        PaymentDTO.CreateOrderResponse response = paymentService.createPaymentOrder(request);
        return Response.success(response);
    }

    @Operation(summary = "支付回调", description = "处理第三方支付回调")
    @PostMapping("/callback/{paymentMethod}")
    public Response<Void> handleCallback(
            @PathVariable String paymentMethod,
            @RequestBody String callbackData) {
        paymentService.handleCallback(paymentMethod, callbackData);
        return Response.success("处理成功", null);
    }
}
