package com.billing.exception;

import com.billing.common.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 业务异常类单元测试
 * 测试异常码、异常消息等
 */
class BusinessExceptionTest {

    @Test
    void testExceptionWithCodeAndMessage() {
        BusinessException exception = new BusinessException(1001, "业务错误");
        assertEquals(1001, exception.getCode());
        assertEquals("业务错误", exception.getMessage());
    }

    @Test
    void testExceptionWithResponseCode() {
        BusinessException exception = new BusinessException(
                Response.ResponseCode.INSUFFICIENT_BALANCE.getCode(),
                Response.ResponseCode.INSUFFICIENT_BALANCE.getMessage()
        );
        assertEquals(Response.ResponseCode.INSUFFICIENT_BALANCE.getCode(), exception.getCode());
        assertEquals(Response.ResponseCode.INSUFFICIENT_BALANCE.getMessage(), exception.getMessage());
    }

    @Test
    void testExceptionWithCause() {
        Throwable cause = new RuntimeException("原始异常");
        BusinessException exception = new BusinessException(1001, "业务错误", cause);
        assertEquals(1001, exception.getCode());
        assertEquals("业务错误", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testInsufficientBalanceException() {
        BusinessException exception = BusinessException.insufficientBalance("余额不足");
        assertEquals(Response.ResponseCode.INSUFFICIENT_BALANCE.getCode(), exception.getCode());
        assertEquals("余额不足", exception.getMessage());
    }

    @Test
    void testBudgetExceededException() {
        BusinessException exception = BusinessException.budgetExceeded("预算超限");
        assertEquals(Response.ResponseCode.BUDGET_EXCEEDED.getCode(), exception.getCode());
        assertEquals("预算超限", exception.getMessage());
    }

    @Test
    void testUserNotFoundException() {
        BusinessException exception = BusinessException.userNotFound(12345L);
        assertEquals(Response.ResponseCode.USER_NOT_FOUND.getCode(), exception.getCode());
        assertTrue(exception.getMessage().contains("12345"));
    }

    @Test
    void testTenantNotFoundException() {
        BusinessException exception = BusinessException.tenantNotFound(67890L);
        assertEquals(Response.ResponseCode.TENANT_NOT_FOUND.getCode(), exception.getCode());
        assertTrue(exception.getMessage().contains("67890"));
    }

    @Test
    void testPricingConfigNotFoundException() {
        BusinessException exception = BusinessException.pricingConfigNotFound("TOKEN");
        assertEquals(Response.ResponseCode.PRICING_CONFIG_NOT_FOUND.getCode(), exception.getCode());
        assertTrue(exception.getMessage().contains("TOKEN"));
    }

    @Test
    void testParamErrorException() {
        BusinessException exception = BusinessException.paramError("参数错误");
        assertEquals(Response.ResponseCode.PARAM_ERROR.getCode(), exception.getCode());
        assertEquals("参数错误", exception.getMessage());
    }

    @Test
    void testSystemErrorException() {
        BusinessException exception = BusinessException.systemError("系统错误");
        assertEquals(Response.ResponseCode.SYSTEM_ERROR.getCode(), exception.getCode());
        assertEquals("系统错误", exception.getMessage());
    }
}
