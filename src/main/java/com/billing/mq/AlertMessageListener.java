package com.billing.mq;

import com.billing.service.external.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlertMessageListener {

    private final MailService mailService;

    public void handleAlertMessage(String message) {
        // 处理预警通知消息
    }
}
