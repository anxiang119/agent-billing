package com.billing.mq;

import com.billing.service.bill.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillMessageListener {

    private final BillService billService;

    public void handleBillMessage(String message) {
        // 处理账单生成消息
    }
}
