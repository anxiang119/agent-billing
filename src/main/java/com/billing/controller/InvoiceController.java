package com.billing.controller;

import com.billing.common.Response;
import com.billing.entity.Invoice;
import com.billing.service.invoice.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@Tag(name = "发票管理", description = "发票相关接口")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Operation(summary = "申请发票", description = "申请开具发票")
    @PostMapping
    public Response<Invoice> applyInvoice(@Valid @RequestBody Invoice invoice) {
        Invoice result = invoiceService.applyInvoice(invoice);
        return Response.success(result);
    }

    @Operation(summary = "获取发票", description = "根据ID查询发票详情")
    @GetMapping("/{id}")
    public Response<Invoice> getInvoiceById(
            @Parameter(description = "发票ID", required = true)
            @PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        return Response.success(invoice);
    }

    @Operation(summary = "作废发票", description = "作废指定发票")
    @DeleteMapping("/{id}/cancel")
    public Response<Void> cancelInvoice(
            @Parameter(description = "发票ID", required = true)
            @PathVariable Long id) {
        invoiceService.cancelInvoice(id);
        return Response.success("作废成功", null);
    }

    @Operation(summary = "重新开票", description = "重新开具发票")
    @PostMapping("/{id}/reissue")
    public Response<Invoice> reissueInvoice(
            @Parameter(description = "发票ID", required = true)
            @PathVariable Long id) {
        Invoice invoice = invoiceService.reissueInvoice(id);
        return Response.success(invoice);
    }
}
