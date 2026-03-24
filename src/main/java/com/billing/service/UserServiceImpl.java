package com.billing.service;

import com.billing.common.Response;
import com.billing.entity.User;
import com.billing.exception.BusinessException;
import com.billing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户Service实现
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        if (user == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户信息不能为空");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户名不能为空");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BusinessException(Response.ResponseCode.BUSINESS_ERROR.getCode(), "用户名已存在");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "密码不能为空");
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户ID不能为空");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.USER_NOT_FOUND.getCode(), "用户不存在"));
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户名不能为空");
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.USER_NOT_FOUND.getCode(), "用户不存在"));
    }

    @Override
    public List<User> getUsersByTenantId(Long tenantId) {
        if (tenantId == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "租户ID不能为空");
        }

        return userRepository.findByTenantId(tenantId);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户ID不能为空");
        }

        if (user == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户信息不能为空");
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.USER_NOT_FOUND.getCode(), "用户不存在"));

        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }

        if (user.getPhone() != null) {
            existingUser.setPhone(user.getPhone());
        }

        if (user.getRealName() != null) {
            existingUser.setRealName(user.getRealName());
        }

        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }

        if (user.getStatus() != null) {
            existingUser.setStatus(user.getStatus());
        }

        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户ID不能为空");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.USER_NOT_FOUND.getCode(), "用户不存在"));

        userRepository.delete(user);
    }

    @Override
    public boolean validateUser(Long id) {
        if (id == null) {
            return false;
        }

        return userRepository.existsByIdAndActive(id);
    }

    @Override
    @Transactional
    public void updateLastLoginTime(Long id) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户ID不能为空");
        }

        if (!userRepository.existsById(id)) {
            throw new BusinessException(Response.ResponseCode.USER_NOT_FOUND.getCode(), "用户不存在");
        }

        userRepository.updateLastLoginTime(id, LocalDateTime.now());
    }
}
