package com.billing.repository;

import com.billing.entity.AuditLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuditLogRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuditLogRepository repository;

    @Test
    void testSaveAndFind() {
        AuditLog auditLog = new AuditLog();
        auditLog.setTenantId(10L);
        auditLog.setUserId(100L);
        auditLog.setOperation("CREATE");
        auditLog.setModule("USER");

        entityManager.persist(auditLog);
        entityManager.flush();

        AuditLog found = repository.findById(auditLog.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("CREATE", found.getOperation());
    }
}
