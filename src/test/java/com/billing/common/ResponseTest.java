package com.billing.common;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 统一响应类单元测试
 * 测试成功响应、失败响应、分页响应等
 */
class ResponseTest {

    @Test
    void testSuccessResponse() {
        Response<String> response = Response.success("test data");
        assertEquals(Response.ResponseCode.SUCCESS.getCode(), response.getCode());
        assertEquals("操作成功", response.getMessage());
        assertEquals("test data", response.getData());
    }

    @Test
    void testSuccessResponseWithMessage() {
        Response<String> response = Response.success("自定义消息", "test data");
        assertEquals(Response.ResponseCode.SUCCESS.getCode(), response.getCode());
        assertEquals("自定义消息", response.getMessage());
        assertEquals("test data", response.getData());
    }

    @Test
    void testSuccessResponseWithoutData() {
        Response<Void> response = Response.success("操作成功", null);
        assertEquals(Response.ResponseCode.SUCCESS.getCode(), response.getCode());
        assertEquals("操作成功", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testFailureResponse() {
        Response<String> response = Response.failure("操作失败");
        assertEquals(Response.ResponseCode.FAILURE.getCode(), response.getCode());
        assertEquals("操作失败", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testErrorResponse() {
        Response<String> response = Response.error(Response.ResponseCode.BUSINESS_ERROR, "业务错误");
        assertEquals(Response.ResponseCode.BUSINESS_ERROR.getCode(), response.getCode());
        assertEquals("业务错误", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testErrorResponseWithCode() {
        Response<String> response = Response.error(1001, "自定义错误");
        assertEquals(1001, response.getCode());
        assertEquals("自定义错误", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testPageResponse() {
        List<String> data = Collections.singletonList("item");
        Page<String> page = new PageImpl<>(data, PageRequest.of(0, 10), 1);

        Response<Page<String>> response = Response.page(page);
        assertEquals(Response.ResponseCode.SUCCESS.getCode(), response.getCode());
        assertEquals("操作成功", response.getMessage());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().getTotalElements());
    }

    @Test
    void testNoArgsConstructor() {
        Response<String> response = new Response<>();
        assertEquals(0, response.getCode());
        assertNull(response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testAllArgsConstructor() {
        Response<String> response = new Response<>(200, "测试消息", "data");
        assertEquals(200, response.getCode());
        assertEquals("测试消息", response.getMessage());
        assertEquals("data", response.getData());
    }

    @Test
    void testSettersAndGetters() {
        Response<String> response = new Response<>();
        response.setCode(300);
        response.setMessage("设置的消息");
        response.setData("设置的数据");

        assertEquals(300, response.getCode());
        assertEquals("设置的消息", response.getMessage());
        assertEquals("设置的数据", response.getData());
    }
}
