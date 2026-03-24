package com.billing.service.audit;

import com.billing.entity.AuditLog;
import com.billing.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void logOperation(Long tenantId, Long userId, String operation, String module,
                           String requestData, String responseData, String ipAddress) {
        AuditLog auditLog = new AuditLog();
        auditLog.setTenantId(tenantId);
        auditLog.setUserId(userId);
        auditLog.setOperation(operation);
        auditLog.setModule(module);
        auditLog.setRequestData(requestData);
        auditLog.setResponseData(responseData);
        auditLog.setIpAddress(ipAddress);
        auditLog.setCreatedTime(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }

    public List<AuditLog> getLogsByUserId(Long userId) {
        return auditLogRepository.findByUserId(userId);
    }

    public List<AuditLog> getLogsByTenantId(Long tenantId) {
        return auditLogRepository.findByTenantId(tenantId);
    }
}
