package com.billing.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * 统一响应类
 * 定义标准的API响应格式
 *
 * @param <T> 响应数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    /**
     * 响应码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 创建成功响应（带消息和数据）
     *
     * @param message 成功消息
     * @param data    响应数据
     * @return 响应对象
     */
    public static <T> Response<T> success(String message, T data) {
        return new Response<>(ResponseCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 创建成功响应（带数据）
     *
     * @param data 响应数据
     * @return 响应对象
     */
    public static <T> Response<T> success(T data) {
        return new Response<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    /**
     * 创建失败响应
     *
     * @param message 失败消息
     * @return 响应对象
     */
    public static <T> Response<T> failure(String message) {
        return new Response<>(ResponseCode.FAILURE.getCode(), message, null);
    }

    /**
     * 创建错误响应
     *
     * @param code    错误码
     * @param message 错误消息
     * @return 响应对象
     */
    public static <T> Response<T> error(int code, String message) {
        return new Response<>(code, message, null);
    }

    /**
     * 创建错误响应响应
     *
     * @param responseCode 响应码枚举
     * @param message      错误消息
     * @return 响应对象
     */
    public static <T> Response<T> error(ResponseCode responseCode, String message) {
        return new Response<>(responseCode.getCode(), message, null);
    }

    /**
     * 创建分页响应
     *
     * @param page 分页数据
     * @return 响应对象
     */
    public static <T> Response<Page<T>> page(Page<T> page) {
        return new Response<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), page);
    }

    /**
     * 响应码枚举
     */
    @Getter
    @AllArgsConstructor
    public enum ResponseCode {
        SUCCESS(0, "操作成功"),
        FAILURE(1, "操作失败"),
        BUSINESS_ERROR(1001, "业务错误"),
        PARAM_ERROR(1002, "参数错误"),
        SYSTEM_ERROR(5000, "系统错误"),
        UNAUTHORIZED(401, "未授权，请登录"),
        FORBIDDEN(403, "无权限访问"),
        NOT_FOUND(404, "资源不存在"),
        INSUFFICIENT_BALANCE(2001, "账户余额不足"),
        BUDGET_EXCEEDED(2002, "消费已超出预算上限"),
        PRICING_CONFIG_NOT_FOUND(3001, "定价配置不存在"),
        USER_NOT_FOUND(4001, "用户不存在"),
        TENANT_NOT_FOUND(4002, "租户不存在");

        private final int code;
        private final String message;
    }
}
