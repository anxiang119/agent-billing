package com.billing.common;

import lombok.AllArgsConstructor;
import lombok.Data;
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
     * 创建成功响应（无数据）
     *
     * @param message 成功消息
     * @return 响应对象
     */
    public static Response<Void> success(String message) {
        return new Response<>(ResponseCode.SUCCESS, message, null);
    }

    /**
     * 创建成功响应（带数据）
     *
     * @param data 响应数据
     * @return 响应对象
     */
    public static <T> Response<T> success(T data) {
        return new Response<>(ResponseCode.SUCCESS, ResponseCode.SUCCESS_MESSAGE, data);
    }

    /**
     * 创建成功响应（带消息和数据）
     *
     * @param message 成功消息
     * @param data    响应数据
     * @return 响应对象
     */
    public static <T> Response<T> success(String message, T data) {
        return new Response<>(ResponseCode.SUCCESS, message, data);
    }

    /**
     * 创建失败响应
     *
     * @param message 失败消息
     * @return 响应对象
     */
    public static <T> Response<T> failure(String message) {
        return new Response<>(ResponseCode.FAILURE, message, null);
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
        return new Response<>(ResponseCode.SUCCESS, ResponseCode.SUCCESS_MESSAGE, page);
    }

    /**
     * 响应码枚举
     */
    public interface ResponseCode {
        int SUCCESS = 0;
        int SUCCESS_MESSAGE_CODE = 0;
        String SUCCESS_MESSAGE = "操作成功";

        int FAILURE = 1;
        String FAILURE_MESSAGE = "操作失败";

        int BUSINESS_ERROR = 1001;
        int PARAM_ERROR = 1002;
        int SYSTEM_ERROR = 5000;

        int UNAUTHORIZED = 401;
        String UNAUTHORIZED_MESSAGE = "未授权，请登录";

        int FORBIDDEN = 403;
        String FORBIDDEN_MESSAGE = "无权限访问";

        int NOT_FOUND = 404;
        String NOT_FOUND_MESSAGE = "资源不存在";

        int INSUFFICIENT_BALANCE = 2001;
        String INSUFFICIENT_BALANCE_MESSAGE = "账户余额不足";

        int BUDGET_EXCEEDED = 2002;
        String BUDGET_EXCEEDED_MESSAGE = "消费已超出预算上限";

        int PRICING_CONFIG_NOT_FOUND = 3001;
        String PRICING_CONFIG_NOT_FOUND_MESSAGE = "定价配置不存在";

        int USER_NOT_FOUND = 4001;
        String USER_NOT_FOUND_MESSAGE = "用户不存在";

        int TENANT_NOT_FOUND = 4002;
        String TENANT_NOT_FOUND_MESSAGE = "租户不存在";
    }
}
