package com.billing.mq;

import com.billing.service.billing.BillingEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillingMessageListener {

    private final BillingEngineService billingEngineService;

    public void handleBillingMessage(String message) {
        // 处理计费消息
    }
}
