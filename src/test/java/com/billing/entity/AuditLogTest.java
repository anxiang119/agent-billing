package com.billing.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuditLogTest {

    @Test
    void testEntityCreation() {
        AuditLog auditLog = new AuditLog();
        auditLog.setId(1L);
        auditLog.setTenantId(10L);
        auditLog.setUserId(100L);
        auditLog.setOperation("CREATE");
        auditLog.setModule("USER");
        auditLog.setCreatedTime(LocalDateTime.now());

        assertNotNull(auditLog);
        assertEquals(1L, auditLog.getId());
        assertEquals("CREATE", auditLog.getOperation());
    }
}
