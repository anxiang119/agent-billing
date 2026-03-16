package com.billing.exception;

import com.billing.common.Response;
import lombok.Getter;

/**
 * 业务异常类
 * 定义系统业务异常及错误码
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    /**
     * 构造函数
     *
     * @param code    错误码
     * @param message 错误消息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造函数（带原始异常）
     *
     * @param code    错误码
     * @param message 错误消息
     * @param cause   原始异常
     */
    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * 创建余额不足异常
     *
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException insufficientBalance(String message) {
        return new BusinessException(Response.ResponseCode.INSUFFICIENT_BALANCE, message);
    }

    /**
     * 创建预算超限异常
     *
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException budgetExceeded(String message) {
        return new BusinessException(Response.ResponseCode.BUDGET_EXCEEDED, message);
    }

    /**
     * 创建用户不存在异常
     *
     * @param userId 用户ID
     * @return 业务异常
     */
    public static BusinessException userNotFound(Long userId) {
        return new BusinessException(Response.ResponseCode.USER_NOT_FOUND,
                "用户不存在：" + userId);
    }

    /**
     * 创建租户不存在异常
     *
     * @param tenantId 租户ID
     * @return 业务异常
     */
    public static BusinessException tenantNotFound(Long tenantId) {
        return new BusinessException(Response.ResponseCode.TENANT_NOT_FOUND,
                "租户不存在：" + tenantId);
    }

    /**
     * 创建定价配置不存在异常
     *
     * @param resourceType 资源类型
     * @return 业务异常
     */
    public static BusinessException pricingConfigNotFound(String resourceType) {
        return new BusinessException(Response.ResponseCode.PRICING_CONFIG_NOT_FOUND,
                "定价配置不存在：" + resourceType);
    }

    /**
     * 创建参数错误异常
     *
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException paramError(String message) {
        return new BusinessException(Response.ResponseCode.PARAM_ERROR, message);
    }

    /**
     * 创建系统错误异常
     *
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException systemError(String message) {
        return new BusinessException(Response.ResponseCode.SYSTEM_ERROR, message);
    }

    /**
     * 创建系统错误异常（带原始异常）
     *
     * @param message 错误消息
     * @param cause   原始异常
     * @return 业务异常
     */
    public static BusinessException systemError(String message, Throwable cause) {
        return new BusinessException(Response.ResponseCode.SYSTEM_ERROR, message, cause);
    }
}
