package com.billing.service.account;

import com.billing.common.Response;
import com.billing.dto.RechargeDTO;
import com.billing.entity.recharge.RechargeRecord;
import com.billing.exception.BusinessException;
import com.billing.repository.RechargeRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 充值Service实现
 */
@Service
@RequiredArgsConstructor
public class RechargeServiceImpl implements RechargeService {

    private final RechargeRecordRepository rechargeRecordRepository;
    private final AccountService accountService;

    @Override
    @Transactional
    public RechargeRecord createRecharge(RechargeDTO.CreateRequest request) {
        if (request == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "请求参数不能为空");
        }

        if (request.getUserId() == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户ID不能为空");
        }

        if (request.getAmount() == null || request.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "充值金额必须大于0");
        }

        if (request.getPaymentMethod() == null || request.getPaymentMethod().trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "支付方式不能为空");
        }

        RechargeRecord record = new RechargeRecord();
        record.setTenantId(request.getTenantId());
        record.setUserId(request.getUserId());
        record.setAmount(request.getAmount());
        record.setCurrency(request.getCurrency());
        record.setPaymentMethod(request.getPaymentMethod());
        record.setRemark(request.getRemark());
        record.setStatus("PENDING");

        return rechargeRecordRepository.save(record);
    }

    @Override
    @Transactional
    public void handleCallback(RechargeDTO.CallbackRequest request) {
        if (request == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "回调请求不能为空");
        }

        if (request.getPaymentOrderNo() == null || request.getPaymentOrderNo().trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "支付订单号不能为空");
        }

        RechargeRecord record = rechargeRecordRepository.findByPaymentOrderNo(request.getPaymentOrderNo());

        if (record == null) {
            throw new BusinessException(Response.ResponseCode.NOT_FOUND.getCode(), "找不到充值记录");
        }

        if ("SUCCESS".equals(request.getStatus())) {
            if ("PENDING".equals(record.getStatus())) {
                accountService.recharge(record.getUserId(), record.getAmount());
                record.setStatus("SUCCESS");
                record.setPaymentTime(LocalDateTime.now());
            }
        } else if ("FAILED".equals(request.getStatus())) {
            record.setStatus("FAILED");
        }

        rechargeRecordRepository.save(record);
    }

    @Override
    public RechargeRecord getRechargeById(Long id) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "充值记录ID不能为空");
        }

        return rechargeRecordRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.NOT_FOUND.getCode(), "充值记录不存在"));
    }
}
