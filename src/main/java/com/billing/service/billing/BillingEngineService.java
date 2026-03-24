package com.billing.service.billing;

import com.billing.common.Response;
import com.billing.dto.BillingDTO;

import java.math.BigDecimal;

/**
 * 计费引擎Service接口
 * 提供预估、预扣、结算等核心计费功能
 */
public interface BillingEngineService {

    /**
     * 预估费用
     *
     * @param request 预估请求
     * @return 预估响应
     */
    BillingDTO.EstimateResponse estimateFee(BillingDTO.EstimateRequest request);

    /**
     * 预扣费用
     *
     * @param request 预扣请求
     * @return 预扣响应
     */
    BillingDTO.PreDeductResponse preDeduct(BillingDTO.PreDeductRequest request);

    /**
     * 结算费用
     *
     * @param request 结算请求
     * @return 结算响应
     */
    BillingDTO.SettleResponse settle(BillingDTO.SettleRequest request);
}
