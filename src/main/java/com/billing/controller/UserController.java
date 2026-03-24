package com.billing.controller;

import com.billing.common.Response;
import com.billing.entity.User;
import com.billing.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户Controller
 * 提供用户查询接口
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {

    private final UserService userService;

    @Operation(summary = "根据ID获取用户", description = "根据用户ID查询用户详细信息")
    @GetMapping("/{id}")
    public Response<User> getUserById(
            @Parameter(description = "用户ID", required = true)
            @PathVariable Long id) {
        User user = userService.getUserById(id);
        return Response.success(user);
    }

    @Operation(summary = "根据用户名获取用户", description = "根据用户名查询用户详细信息")
    @GetMapping("/username/{username}")
    public Response<User> getUserByUsername(
            @Parameter(description = "用户名", required = true)
            @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return Response.success(user);
    }

    @Operation(summary = "根据租户ID获取用户列表", description = "查询指定租户下的所有用户")
    @GetMapping("/tenant/{tenantId}")
    public Response<List<User>> getUsersByTenantId(
            @Parameter(description = "租户ID", required = true)
            @PathVariable Long tenantId) {
        List<User> users = userService.getUsersByTenantId(tenantId);
        return Response.success(users);
    }

    @Operation(summary = "更新用户信息", description = "更新用户的邮箱、手机号、真实姓名等信息")
    @PutMapping("/{id}")
    public Response<User> updateUser(
            @Parameter(description = "用户ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return Response.success(updatedUser);
    }

    @Operation(summary = "删除用户", description = "根据ID删除用户，实际为逻辑删除")
    @DeleteMapping("/{id}")
    public Response<Void> deleteUser(
            @Parameter(description = "用户ID", required = true)
            @PathVariable Long id) {
        userService.deleteUser(id);
        return Response.success("删除成功", null);
    }

    @Operation(summary = "验证用户有效性", description = "检查用户是否存在且状态为启用")
    @GetMapping("/{id}/validate")
    public Response<Boolean> validateUser(
            @Parameter(description = "用户ID", required = true)
            @PathVariable Long id) {
        boolean isValid = userService.validateUser(id);
        return Response.success(isValid);
    }

    @Operation(summary = "更新最后登录时间", description = "更新用户的最后登录时间")
    @PutMapping("/{id}/login-time")
    public Response<Void> updateLastLoginTime(
            @Parameter(description = "用户ID", required = true)
            @PathVariable Long id) {
        userService.updateLastLoginTime(id);
        return Response.success("更新成功", null);
    }
}
