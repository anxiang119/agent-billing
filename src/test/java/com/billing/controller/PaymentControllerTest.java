package com.billing.controller;

import com.billing.common.Response;
import com.billing.dto.PaymentDTO;
import com.billing.service.payment.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreatePaymentOrder_Success() throws Exception {
        PaymentDTO.CreateOrderRequest request = new PaymentDTO.CreateOrderRequest();
        request.setTenantId(10L);
        request.setUserId(100L);
        request.setAmount(new BigDecimal("100.00"));
        request.setPaymentMethod("ALIPAY");

        PaymentDTO.CreateOrderResponse response = new PaymentDTO.CreateOrderResponse();
        response.setOrderNo("ORDER001");
        response.setPaymentUrl("https://payment.example.com/pay");

        when(paymentService.createPaymentOrder(any())).thenReturn(response);

        mockMvc.perform(post("/api/payments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.orderNo").value("ORDER001"));
    }
}
