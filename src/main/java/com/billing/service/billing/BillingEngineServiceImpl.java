package com.billing.service.billing;

import com.billing.common.Response;
import com.billing.dto.BillingDTO;
import com.billing.entity.*;
import com.billing.exception.BusinessException;
import com.billing.repository.*;
import com.billing.service.PricingConfigService;
import com.billing.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 计费引擎Service实现
 */
@Service
@RequiredArgsConstructor
public class BillingEngineServiceImpl implements BillingEngineService {

    private final PricingConfigService pricingConfigService;
    private final AccountService accountService;
    private final ConsumptionRecordRepository consumptionRecordRepository;
    private final PreDeductionRecordRepository preDeductionRecordRepository;
    private final FeeCalculator feeCalculator;

    @Override
    public BillingDTO.EstimateResponse estimateFee(BillingDTO.EstimateRequest request) {
        validateEstimateRequest(request);

        PricingConfig config = pricingConfigService.getActiveConfig(
                request.getTenantId(),
                request.getResourceType(),
                request.getResourceModel(),
                LocalDateTime.now()
        );

        BigDecimal estimatedFee = feeCalculator.calculate(config, request.getQuantity());

        BillingDTO.EstimateResponse response = new BillingDTO.EstimateResponse();
        response.setTenantId(request.getTenantId());
        response.setUserId(request.getUserId());
        response.setResourceId(request.getResourceId());
        response.setResourceType(request.getResourceType());
        response.setResourceModel(request.getResourceModel());
        response.setQuantity(request.getQuantity());
        response.setEstimatedFee(estimatedFee);
        response.setCurrency(config.getCurrency());

        return response;
    }

    @Override
    @Transactional
    public BillingDTO.PreDeductResponse preDeduct(BillingDTO.PreDeductRequest request) {
        validatePreDeductRequest(request);

        BillingDTO.EstimateRequest estimateRequest = new BillingDTO.EstimateRequest();
        BeanUtils.copyProperties(request, estimateRequest);
        BillingDTO.EstimateResponse estimateResponse = estimateFee(estimateRequest);

        PreDeductionRecord record = new PreDeductionRecord();
        record.setTenantId(request.getTenantId());
        record.setUserId(request.getUserId());
        record.setResourceId(request.getResourceId());
        record.setEstimatedAmount(estimateResponse.getEstimatedFee());
        record.setPreDeductedAmount(estimateResponse.getEstimatedFee());
        record.setStatus("PENDING");

        PreDeductionRecord savedRecord = preDeductionRecordRepository.save(record);

        Account account = accountService.deduct(request.getUserId(), estimateResponse.getEstimatedFee(), request.getResourceId());

        savedRecord.setStatus("DEDUCTED");
        savedRecord.setDeductionTime(LocalDateTime.now());
        preDeductionRecordRepository.save(savedRecord);

        BillingDTO.PreDeductResponse response = new BillingDTO.PreDeductResponse();
        response.setPreDeductionId(savedRecord.getId());
        response.setSuccess(true);
        response.setDeductedAmount(estimateResponse.getEstimatedFee());
        response.setBalance(account.getBalance());
        response.setCurrency(estimateResponse.getCurrency());

        return response;
    }

    @Override
    @Transactional
    public BillingDTO.SettleResponse settle(BillingDTO.SettleRequest request) {
        validateSettleRequest(request);

        PreDeductionRecord preDeductRecord = preDeductionRecordRepository.findByResourceIdAndStatus(
                request.getResourceId(), "DEDUCTED");

        if (preDeductRecord == null) {
            throw new BusinessException(Response.ResponseCode.NOT_FOUND.getCode(), "找不到预扣记录");
        }

        BillingDTO.EstimateRequest estimateRequest = new BillingDTO.EstimateRequest();
        estimateRequest.setTenantId(request.getTenantId());
        estimateRequest.setUserId(request.getUserId());
        estimateRequest.setResourceId(request.getResourceId());
        estimateRequest.setResourceType(request.getResourceType());
        estimateRequest.setResourceModel(request.getResourceModel());
        estimateRequest.setQuantity(request.getQuantity());

        BillingDTO.EstimateResponse estimateResponse = estimateFee(estimateRequest);

        ConsumptionRecord consumptionRecord = new ConsumptionRecord();
        consumptionRecord.setTenantId(request.getTenantId());
        consumptionRecord.setUserId(request.getUserId());
        consumptionRecord.setResourceId(request.getResourceId());
        consumptionRecord.setResourceType(request.getResourceType());
        consumptionRecord.setResourceModel(request.getResourceModel());
        consumptionRecord.setQuantity(request.getQuantity());
        consumptionRecord.setUnitPrice(estimateResponse.getEstimatedFee().divide(request.getQuantity(), 4, BigDecimal.ROUND_HALF_UP));
        consumptionRecord.setAmount(estimateResponse.getEstimatedFee());
        consumptionRecord.setCurrency(estimateResponse.getCurrency());
        consumptionRecord.setActualAmount(estimateResponse.getEstimatedFee());
        consumptionRecord.setStatus("SETTLED");
        consumptionRecord.setConsumptionTime(LocalDateTime.now());

        consumptionRecordRepository.save(consumptionRecord);

        BigDecimal actualAmount = estimateResponse.getEstimatedFee();
        BigDecimal preDeductedAmount = preDeductRecord.getPreDeductedAmount();
        BigDecimal refundAmount = preDeductedAmount.subtract(actualAmount);

        if (refundAmount.compareTo(BigDecimal.ZERO) > 0) {
            accountService.refund(request.getUserId(), refundAmount, request.getResourceId()).getBalance();
            preDeductRecord.setRefundAmount(refundAmount);
        }

        preDeductRecord.setActualAmount(actualAmount);
        preDeductRecord.setStatus("SETTLED");
        preDeductRecord.setSettleTime(LocalDateTime.now());
        preDeductionRecordRepository.save(preDeductRecord);

        BillingDTO.SettleResponse response = new BillingDTO.SettleResponse();
        response.setConsumptionRecordId(consumptionRecord.getId());
        response.setPreDeductionId(preDeductRecord.getId());
        response.setActualAmount(actualAmount);
        response.setRefundAmount(refundAmount);
        response.setSuccess(true);

        return response;
    }

    private void validateEstimateRequest(BillingDTO.EstimateRequest request) {
        if (request == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "请求参数不能为空");
        }

        if (request.getTenantId() == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "租户ID不能为空");
        }

        if (request.getUserId() == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户ID不能为空");
        }

        if (request.getResourceType() == null || request.getResourceType().trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "资源类型不能为空");
        }

        if (request.getQuantity() == null || request.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "消费数量必须大于0");
        }
    }

    private void validatePreDeductRequest(BillingDTO.PreDeductRequest request) {
        if (request == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "请求参数不能为空");
        }

        if (request.getResourceId() == null || request.getResourceId().trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "资源ID不能为空");
        }

        validateEstimateRequest(request);
    }

    private void validateSettleRequest(BillingDTO.SettleRequest request) {
        if (request == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "请求参数不能为空");
        }

        if (request.getResourceId() == null || request.getResourceId().trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "资源ID不能为空");
        }

        validateEstimateRequest(request);
    }
}
