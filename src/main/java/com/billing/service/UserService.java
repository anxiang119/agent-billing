package com.billing.service;

import com.billing.entity.User;

import java.util.List;

/**
 * 用户Service接口
 * 提供用户业务逻辑
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param user 用户信息
     * @return 创建的用户
     */
    User createUser(User user);

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    User getUserById(Long id);

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getUserByUsername(String username);

    /**
     * 根据租户ID获取用户列表
     *
     * @param tenantId 租户ID
     * @return 用户列表
     */
    List<User> getUsersByTenantId(Long tenantId);

    /**
     * 更新用户信息
     *
     * @param id 用户ID
     * @param user 更新的用户信息
     * @return 更新后的用户信息
     */
    User updateUser(Long id, User user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 验证用户是否有效
     *
     * @param id 用户ID
     * @return 是否有效
     */
    boolean validateUser(Long id);

    /**
     * 更新用户最后登录时间
     *
     * @param id 用户ID
     */
    void updateLastLoginTime(Long id);
}
