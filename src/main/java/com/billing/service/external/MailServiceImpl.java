package com.billing.service.external;

import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Override
    public void sendMail(String to, String subject, String content) {
        // 实现邮件发送
    }

    @Override
    public void sendHtmlMail(String to, String subject, String htmlContent) {
        // 实现HTML邮件发送
    }
}
