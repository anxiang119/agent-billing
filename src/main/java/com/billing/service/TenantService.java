package com.billing.service;

import com.billing.entity.Tenant;

import java.util.List;

/**
 * 租户Service接口
 */
public interface TenantService {

    /**
     * 创建租户
     *
     * @param tenant 租户对象
     * @return 创建的租户
     */
    Tenant createTenant(Tenant tenant);

    /**
     * 根据ID获取租户
     *
     * @param id 租户ID
     * @return 租户对象
     */
    Tenant getTenantById(Long id);

    /**
     * 根据代码获取租户
     *
     * @param code 租户代码
     * @return 租户对象
     */
    Tenant getTenantByCode(String code);

    /**
     * 获取所有租户
     *
     * @return 租户列表
     */
    List<Tenant> getAllTenants();

    /**
     * 更新租户
     *
     * @param id 租户ID
     * @param tenant 租户对象
     * @return 更新后的租户
     */
    Tenant updateTenant(Long id, Tenant tenant);

    /**
     * 删除租户
     *
     * @param id 租户ID
     */
    void deleteTenant(Long id);

    /**
     * 验证租户是否存在且启用
     *
     * @param id 租户ID
     * @return 是否存在且启用
     */
    boolean validateTenant(Long id);
}
