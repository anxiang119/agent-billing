package com.billing.service;

import com.billing.common.Response;
import com.billing.entity.Tenant;
import com.billing.exception.BusinessException;
import com.billing.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    @Transactional
    public Tenant createTenant(Tenant tenant) {
        if (tenantRepository.existsByCode(tenant.getCode())) {
            throw new BusinessException(Response.ResponseCode.BUSINESS_ERROR.getCode(), "租户代码已存在：" + tenant.getCode());
        }
        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant getTenantById(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.TENANT_NOT_FOUND.getCode(), "租户不存在：" + id));
    }

    @Override
    public Tenant getTenantByCode(String code) {
        return tenantRepository.findByCode(code)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.TENANT_NOT_FOUND.getCode(), "租户不存在：" + code));
    }

    @Override
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    @Override
    @Transactional
    public Tenant updateTenant(Long id, Tenant tenant) {
        Tenant existingTenant = getTenantById(id);

        if (tenant.getName() != null) {
            existingTenant.setName(tenant.getName());
        }
        if (tenant.getContact() != null) {
            existingTenant.setContact(tenant.getContact());
        }
        if (tenant.getPhone() != null) {
            existingTenant.setPhone(tenant.getPhone());
        }
        if (tenant.getAddress() != null) {
            existingTenant.setAddress(tenant.getAddress());
        }

        return tenantRepository.save(existingTenant);
    }

    @Override
    @Transactional
    public void deleteTenant(Long id) {
        Tenant tenant = getTenantById(id);
        tenantRepository.delete(tenant);
    }

    @Override
    public boolean validateTenant(Long id) {
        return tenantRepository.findByIdAndStatus(id, "ACTIVE").isPresent();
    }
}

