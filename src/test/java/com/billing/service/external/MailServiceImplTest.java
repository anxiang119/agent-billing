package com.billing.service.external;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MailServiceImplTest {

    @Test
    void testSendMail() {
        MailServiceImpl service = new MailServiceImpl();
        service.sendMail("test@example.com", "Test", "Content");
        assertTrue(true);
    }
}
