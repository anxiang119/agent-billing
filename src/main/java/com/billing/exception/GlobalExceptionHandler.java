package com.billing.exception;

import com.billing.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 统一处理系统异常并返回标准响应
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param exception 业务异常
     * @return 响应对象
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response<Void>> handleBusinessException(BusinessException exception) {
        log.warn("业务异常: code={}, message={}", exception.getCode(), exception.getMessage());
        return ResponseEntity
                .ok()
                .body(Response.error(exception.getCode(), exception.getMessage()));
    }

    /**
     * 处理参数非法异常
     *
     * @param exception 参数非法异常
     * @return 响应对象
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<Void>> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.warn("参数非法异常: {}", exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(Response.error(Response.ResponseCode.PARAM_ERROR,
                        "参数不合法: " + exception.getMessage()));
    }

    /**
     * 处理参数校验异常（@Valid）
     *
     * @param exception 参数校验异常
     * @return 响应对象
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Void>> handleValidationException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        String message = "参数校验失败: " + String.join(", ", errors);
        log.warn("参数校验异常: {}", message);

        return ResponseEntity
                .badRequest()
                .body(Response.error(Response.ResponseCode.PARAM_ERROR, message));
    }

    /**
     * 处理绑定异常
     *
     * @param exception 绑定异常
     * @return 响应对象
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Response<Void>> handleBindException(BindException exception) {
        List<String> errors = exception.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        String message = "参数绑定失败: " + String.join(", ", errors);
        log.warn("参数绑定异常: {}", message);

        return ResponseEntity
                .badRequest()
                .body(Response.error(Response.ResponseCode.PARAM_ERROR, message));
    }

    /**
     * 处理参数类型不匹配异常
     *
     * @param exception 参数类型不匹配异常
     * @return 响应对象
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response<Void>> handleTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        String message = String.format("参数类型错误: %s 期望类型为 %s",
                exception.getName(),
                exception.getRequiredType().getSimpleName());
        log.warn("参数类型不匹配异常: {}", message);

        return ResponseEntity
                .badRequest()
                .body(Response.error(Response.ResponseCode.PARAM_ERROR, message));
    }

    /**
     * 处理空指针异常
     *
     * @param exception 空指针异常
     * @return 响应对象
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Response<Void>> handleNullPointerException(NullPointerException exception) {
        log.error("空指针异常", exception);
        return ResponseEntity
                .internalServerError()
                .body(Response.error(Response.ResponseCode.SYSTEM_ERROR, "系统错误: 空指针异常"));
    }

    /**
     * 处理运行时异常
     *
     * @param exception 运行时异常
     * @return 响应对象
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response<Void>> handleRuntimeException(RuntimeException exception) {
        log.error("运行时异常", exception);
        return ResponseEntity
                .internalServerError()
                .body(Response.error(Response.ResponseCode.SYSTEM_ERROR,
                        "系统错误: " + exception.getMessage()));
    }

    /**
     * 处理所有其他异常
     *
     * @param exception 异常
     * @return 响应对象
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleException(Exception exception) {
        log.error("未知异常", exception);
        return ResponseEntity
                .internalServerError()
                .body(Response.error(Response.ResponseCode.SYSTEM_ERROR, "系统错误，请联系管理员"));
    }
}
