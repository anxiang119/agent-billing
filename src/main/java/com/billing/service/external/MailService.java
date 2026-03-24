package com.billing.service.external;

public interface MailService {
    void sendMail(String to, String subject, String content);
    void sendHtmlMail(String to, String subject, String htmlContent);
}
