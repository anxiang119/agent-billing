package com.billing.service.account;

import com.billing.dto.RechargeDTO;
import com.billing.entity.recharge.RechargeRecord;

/**
 * 充值Service接口
 * 提供充值业务逻辑
 */
public interface RechargeService {

    /**
     * 创建充值订单
     *
     * @param request 充值请求
     * @return 充值记录
     */
    RechargeRecord createRecharge(RechargeDTO.CreateRequest request);

    /**
     * 处理支付回调
     *
     * @param request 回调请求
     */
    void handleCallback(RechargeDTO.CallbackRequest request);

    /**
     * 根据ID获取充值记录
     *
     * @param id 充值记录ID
     * @return 充值记录
     */
    RechargeRecord getRechargeById(Long id);
}
