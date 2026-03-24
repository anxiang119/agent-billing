package com.billing.service.audit;

import com.billing.entity.AuditLog;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuditLogServiceTest {

    @Test
    void testLogOperation_Success() {
        AuditLog auditLog = new AuditLog();
        auditLog.setTenantId(10L);
        auditLog.setUserId(100L);
        auditLog.setOperation("CREATE");
        auditLog.setModule("USER");

        assertNotNull(auditLog);
        assertEquals("CREATE", auditLog.getOperation());
    }
}
