package com.billing.exception;

import com.billing.common.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 全局异常处理器单元测试
 * 测试各种异常的统一处理
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleBusinessException() {
        BusinessException exception = BusinessException.insufficientBalance("余额不足");
        ResponseEntity<Response<Void>> response = handler.handleBusinessException(exception);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Response.ResponseCode.INSUFFICIENT_BALANCE, response.getBody().getCode());
        assertEquals("余额不足", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("参数不合法");
        ResponseEntity<Response<Void>> response = handler.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(Response.ResponseCode.PARAM_ERROR, response.getBody().getCode());
        assertTrue(response.getBody().getMessage().contains("参数不合法"));
    }

    @Test
    void testHandleMethodArgumentNotValidException() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "defaultMessage");

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        ResponseEntity<Response<Void>> response = handler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(Response.ResponseCode.PARAM_ERROR, response.getBody().getCode());
        assertTrue(response.getBody().getMessage().contains("参数校验失败"));
    }

    @Test
    void testHandleMethodArgumentTypeMismatchException() {
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn("testParam");
        when(exception.getRequiredType()).thenReturn(Long.class);

        ResponseEntity<Response<Void>> response = handler.handleTypeMismatchException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(Response.ResponseCode.PARAM_ERROR, response.getBody().getCode());
        assertTrue(response.getBody().getMessage().contains("参数类型错误"));
    }

    @Test
    void testHandleNullPointerException() {
        NullPointerException exception = new NullPointerException("空指针异常");
        ResponseEntity<Response<Void>> response = handler.handleNullPointerException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Response.ResponseCode.SYSTEM_ERROR, response.getBody().getCode());
        assertTrue(response.getBody().getMessage().contains("系统错误"));
    }

    @Test
    void testHandleRuntimeException() {
        RuntimeException exception = new RuntimeException("运行时异常");
        ResponseEntity<Response<Void>> response = handler.handleRuntimeException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Response.ResponseCode.SYSTEM_ERROR, response.getBody().getCode());
        assertTrue(response.getBody().getMessage().contains("系统错误"));
    }

    @Test
    void testHandleException() {
        Exception exception = new Exception("未知异常");
        ResponseEntity<Response<Void>> response = handler.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Response.ResponseCode.SYSTEM_ERROR, response.getBody().getCode());
        assertTrue(response.getBody().getMessage().contains("系统错误"));
    }
}
