package com.billing.controller;

import com.billing.common.Response;
import com.billing.entity.Bill;
import com.billing.service.bill.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 账单Controller
 * 提供账单管理的REST API
 */
@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
@Tag(name = "账单管理", description = "账单相关接口")
public class BillController {

    private final BillService billService;

    @Operation(summary = "生成月度账单", description = "为用户生成月度账单")
    @PostMapping("/generate")
    public Response<Bill> generateMonthlyBill(
            @Parameter(description = "租户ID", required = true)
            @RequestParam Long tenantId,
            @Parameter(description = "用户ID", required = true)
            @RequestParam Long userId) {
        Bill bill = billService.generateMonthlyBill(tenantId, userId);
        return Response.success(bill);
    }

    @Operation(summary = "根据ID获取账单", description = "根据账单ID查询账单详情")
    @GetMapping("/{id}")
    public Response<Bill> getBillById(
            @Parameter(description = "账单ID", required = true)
            @PathVariable Long id) {
        Bill bill = billService.getBillById(id);
        return Response.success(bill);
    }

    @Operation(summary = "根据账单编号获取账单", description = "根据账单编号查询账单详情")
    @GetMapping("/no/{billNo}")
    public Response<Bill> getBillByBillNo(
            @Parameter(description = "账单编号", required = true)
            @PathVariable String billNo) {
        Bill bill = billService.getBillByBillNo(billNo);
        return Response.success(bill);
    }

    @Operation(summary = "删除账单", description = "根据ID删除账单")
    @DeleteMapping("/{id}")
    public Response<Void> deleteBill(
            @Parameter(description = "账单ID", required = true)
            @PathVariable Long id) {
        billService.deleteBill(id);
        return Response.success("删除成功", null);
    }

    @Operation(summary = "下载账单PDF", description = "生成并下载账单PDF文件")
    @GetMapping("/{id}/download/pdf")
    public ResponseEntity<byte[]> downloadPdf(
            @Parameter(description = "账单ID", required = true)
            @PathVariable Long id) {
        File file = billService.generatePdfFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        try {
            headers.setContentDispositionFormData("attachment",
                    URLEncoder.encode(file.getName(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("文件名编码失败", e);
        }

        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new FileUrlResource(file.getAbsolutePath()).getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败", e);
        }
    }

    @Operation(summary = "下载账单Excel", description = "生成并下载账单Excel文件")
    @GetMapping("/{id}/download/excel")
    public ResponseEntity<byte[]> downloadExcel(
            @Parameter(description = "账单ID", required = true)
            @PathVariable Long id) {
        File file = billService.generateExcelFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            headers.setContentDispositionFormData("attachment",
                    URLEncoder.encode(file.getName(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("文件名编码失败", e);
        }

        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new FileUrlResource(file.getAbsolutePath()).getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败", e);
        }
    }
}
